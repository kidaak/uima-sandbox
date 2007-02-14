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

package org.apache.uima.caseditor.editor.fsview;


import org.apache.uima.cas.FeatureStructure;
import org.apache.uima.cas.text.AnnotationFS;
import org.apache.uima.caseditor.core.util.Primitives;
import org.apache.uima.caseditor.editor.FeatureValue;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.swt.graphics.Image;

/**
 * Provide the labels for the given {@link FeatureStructure}s.
 * 
 * @author <a href="mailto:kottmann@gmail.com">Joern Kottmann</a>
 * @version $Revision: 1.3.2.2 $, $Date: 2007/01/04 15:00:57 $
 */
public final class FeatureStructureLabelProvider implements ILabelProvider {
  public String getText(Object element) {
    if (element instanceof IAdaptable
            && ((IAdaptable) element).getAdapter(AnnotationFS.class) != null) {
      FeatureStructure structure = (AnnotationFS) ((IAdaptable) element)
              .getAdapter(AnnotationFS.class);

      return structure.getType().getShortName();
    } else if (element instanceof FeatureValue) {
      FeatureValue featureValue = (FeatureValue) element;
      Object value = featureValue.getValue();

      if (value == null) {
        return featureValue.getFeature().getShortName() + ": null";
      }

      if (Primitives.isPrimitive(featureValue.getFeature())) {
        return featureValue.getFeature().getShortName() + " : " + value.toString();
      }

      return featureValue.getFeature().getShortName();
    } else {
      assert false : "Unexpected element!";

      return element.toString();
    }
  }

  public Image getImage(Object element) {
    return null;
  }

  public boolean isLabelProperty(Object element, String property) {
    return false;
  }

  public void addListener(ILabelProviderListener listener) {
  }

  public void removeListener(ILabelProviderListener listener) {
  }

  public void dispose() {
  }
}