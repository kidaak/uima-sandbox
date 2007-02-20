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

package org.apache.uima.caseditor.ui.model;


import org.apache.uima.caseditor.CasEditorPlugin;
import org.apache.uima.caseditor.Images;
import org.apache.uima.caseditor.core.model.CorpusElement;
import org.apache.uima.caseditor.core.model.DocumentElement;
import org.eclipse.jface.resource.ImageDescriptor;

/**
 * This is the IWorkbenchAdapter for the CorpusElement.
 */
class CorpusAdapter extends
        AbstractElementAdapter
{
    /**
     * Retrives all documents (children) of the current 
     * CorpusElement instance.
     */
    public Object[] getChildren(Object o)
    {
        CorpusElement corpus = (CorpusElement) o;
        
        DocumentElement[] documentElements = new DocumentElement[corpus
                .getDocuments().size()];
        
        return corpus.getDocuments().toArray(documentElements);
    }
    
    /**
     * Retrives the imag {@link ImageDescriptor} for the CorpusElement.
     */
    public ImageDescriptor getImageDescriptor(Object object)
    {
        return CasEditorPlugin.getTaeImageDescriptor(Images.MODEL_CORPUS);
    }
}