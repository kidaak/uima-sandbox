/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.apache.uima.textmarker.ide.ui.preferences;

import org.apache.uima.textmarker.ide.TextMarkerIdePlugin;
import org.eclipse.dltk.ui.preferences.AbstractConfigurationBlockPreferencePage;
import org.eclipse.dltk.ui.preferences.IPreferenceConfigurationBlock;
import org.eclipse.dltk.ui.preferences.OverlayPreferenceStore;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

public class TextMarkerSmartTypingPreferencePage extends AbstractConfigurationBlockPreferencePage {

  /*
   * @see
   * org.eclipse.ui.internal.editors.text.AbstractConfigurationBlockPreferencePage#setDescription()
   */
  @Override
  protected void setDescription() {
    String description = TextMarkerPreferencesMessages.TextMarkerSmartTypingConfigurationBlock_typing_tabTitle;
    setDescription(description);
  }

  /*
   * @seeorg.org.eclipse.ui.internal.editors.text.AbstractConfigurationBlockPreferencePage#
   * setPreferenceStore()
   */
  @Override
  protected void setPreferenceStore() {
    setPreferenceStore(TextMarkerIdePlugin.getDefault().getPreferenceStore());
  }

  @Override
  protected Label createDescriptionLabel(Composite parent) {
    return null; // no description for new look.
  }

  /*
   * @seeorg.eclipse.ui.internal.editors.text.AbstractConfigureationBlockPreferencePage#
   * createConfigurationBlock(org.eclipse.ui.internal.editors.text.OverlayPreferenceStore)
   */
  @Override
  protected IPreferenceConfigurationBlock createConfigurationBlock(
          OverlayPreferenceStore overlayPreferenceStore) {
    return new TextMarkerSmartTypingConfigurationBlock(overlayPreferenceStore);
  }

  @Override
  protected String getHelpId() {
    return "";
  }

}
