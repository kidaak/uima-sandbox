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

package org.apache.uima.caseditor.editor;

import org.eclipse.jface.action.IStatusLineManager;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.editors.text.TextEditorActionContributor;
import org.eclipse.ui.texteditor.StatusLineContributionItem;

/**
 * The <code>AnnotationEditor</code> action contributor.
 * 
 * Contributes the "annotation mode" status item to the status bar.
 * 
 * @author <a href="mailto:kottmann@gmail.com">Joern Kottmann</a>
 * @version $Revision: 1.3.2.2 $, $Date: 2007/01/04 15:00:54 $
 */
public class AnnotationEditorActionContributor extends TextEditorActionContributor {
  /**
   * ID of the status item.
   */
  public static final String ID = "net.sf.tae.editor.mode.status";

  private AnnotationEditor mActiveEditorPart;

  private StatusLineContributionItem mStatusLineModeItem;

  /**
   * Sets the active editor.
   * 
   * @param part
   */
  @Override
  public void setActiveEditor(IEditorPart part) {
    super.setActiveEditor(part);

    if (mActiveEditorPart != part && part instanceof AnnotationEditor) {
      mActiveEditorPart = (AnnotationEditor) part;

      mActiveEditorPart.setStatusField(mStatusLineModeItem, ID);

      // TODO: how todo this right ???
      mStatusLineModeItem.setText(mActiveEditorPart.getAnnotationMode().getShortName());
    }
  }

  /**
   * Contributes the status item to the status line.
   * 
   * @param statusLineManager
   */
  @Override
  public void contributeToStatusLine(IStatusLineManager statusLineManager) {
    super.contributeToStatusLine(statusLineManager);

    mStatusLineModeItem = new StatusLineContributionItem(ID);

    mStatusLineModeItem.setVisible(true);

    statusLineManager.add(mStatusLineModeItem);
  }
}