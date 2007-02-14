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

package org.apache.uima.caseditor.editor.context;

import org.apache.uima.cas.Feature;
import org.apache.uima.cas.FeatureStructure;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

/**
 * TODO: add javadoch here
 * 
 * @author <a href="mailto:kottmann@gmail.com">Joern Kottmann</a>
 * @version $Revision: 1.3.2.2 $, $Date: 2007/01/04 15:00:58 $
 */
public class AnnotationEditingControl extends Composite {

  public AnnotationEditingControl(Composite parent) {
    super(parent, SWT.NONE);

    setLayout(new FillLayout());

    Label text = new Label(this, SWT.NONE);
    text.setText("annotation context editor");

    pack();
  }

  /**
   * Display this feature structure.
   */
  public void displayFeatureStructure(FeatureStructure structure) {
    Feature hack = structure.getType().getFeatureByBaseName("byte");
    structure.setFeatureValue(hack, structure.getCAS().createByteArrayFS(5));

  }
}
