/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.apache.uima.solrcas;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.CommonsHttpSolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.apache.uima.UimaContext;
import org.apache.uima.analysis_component.CasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.*;
import org.apache.uima.cas.text.AnnotationFS;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.util.Level;

import java.net.URI;
import java.net.URL;
import java.util.Map;

/**
 * CAS Consumer to write on a Solr instance
 */
public class SolrCASConsumer extends CasAnnotator_ImplBase {

  protected SolrServer solrServer;

  private SolrMappingConfiguration mappingConfig;

  private boolean autoCommit;

  @Override
  public void initialize(UimaContext context) throws ResourceInitializationException {
    super.initialize(context);
    try {
      /* create the SolrServer*/
      this.solrServer = createServer();

      /* read configuration */
      FieldMappingReader fieldMappingReader = new FieldMappingReader();
      String mappingFileParam = String.valueOf(context.getConfigParameterValue("mappingFile"));
      this.mappingConfig = fieldMappingReader.getConf(mappingFileParam);

      /* set Solr autoCommit parameter */
      Object autoCommitParam = context.getConfigParameterValue("autoCommit");
      if (autoCommitParam != null && autoCommitParam.toString().length() > 0)
        this.autoCommit = Boolean.valueOf(autoCommitParam.toString());
      else
        this.autoCommit = false; // default to false

    } catch (Exception e) {
      context.getLogger().log(Level.SEVERE, e.toString());
      throw new ResourceInitializationException(e);
    }
  }

  protected SolrServer createServer() throws Exception {
    /* get Solr type*/
    String solrInstanceTypeParam = String.valueOf(getContext().
            getConfigParameterValue("solrInstanceType"));

    /* get Solr Path */
    String solrPathParam = String.valueOf(getContext().
            getConfigParameterValue("solrPath"));

    SolrServer solrServer = null;
    if (solrInstanceTypeParam.equalsIgnoreCase("http")) {
      URL solrURL = URI.create(solrPathParam).toURL();
      solrServer = new CommonsHttpSolrServer(solrURL);
    }

    return solrServer;
  }

  public void process(CAS cas) throws AnalysisEngineProcessException {

    SolrInputDocument document = new SolrInputDocument();
    if (mappingConfig.getCasMapping() != null && mappingConfig.getCasMapping().length() > 0)
      document.addField(mappingConfig.getCasMapping(), cas.toString());
    if (mappingConfig.getDocumentTextMapping() != null && mappingConfig.getDocumentTextMapping().length() > 0)
      document.addField(mappingConfig.getDocumentTextMapping(), cas.getDocumentText());
    if (mappingConfig.getDocumentLanguageMapping() != null && mappingConfig.getDocumentLanguageMapping().length() > 0)
      document.addField(mappingConfig.getDocumentLanguageMapping(), cas.getDocumentLanguage());
    for (String key : mappingConfig.getFeatureStructuresMapping().keySet()) {
      Type type = cas.getTypeSystem().getType(key);

      for (FSIterator<FeatureStructure> iterator = cas.getIndexRepository().getAllIndexedFS(type); iterator
              .hasNext();) {
        FeatureStructure fs = iterator.next();
        Map<String, String> stringStringMap = mappingConfig.getFeatureStructuresMapping().get(key);

        for (String featureName : stringStringMap.keySet()) {

          String fieldName = stringStringMap.get(featureName);

          String featureValue;

          if (fs instanceof AnnotationFS && "coveredText".equals(featureName)) {
            featureValue = ((AnnotationFS) fs).getCoveredText();
          } else {
            Feature feature = type.getFeatureByBaseName(featureName);
            featureValue = fs.getFeatureValueAsString(feature);
          }

          document.addField(fieldName, featureValue);
        }
      }
    }

    try {
      solrServer.add(document);
      if (!autoCommit)
        solrServer.commit();
    } catch (Exception e) {
      throw new AnalysisEngineProcessException(e);
    }
  }
}
