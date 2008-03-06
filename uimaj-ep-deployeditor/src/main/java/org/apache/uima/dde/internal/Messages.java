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

package org.apache.uima.dde.internal;

import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.eclipse.osgi.util.NLS;

/**
 */
public class Messages extends NLS {
  private static final String BUNDLE_NAME = "org.apache.uima.dde.internal.messages";//$NON-NLS-1$

  private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);
  static {
    NLS.initializeMessages(BUNDLE_NAME, Messages.class);
  }

  // Overview Page
  static public String DDE_OverviewPage_General_Section_Title;

  static public String DDE_OverviewPage_General_Section_Description;

  static public String DDE_OverviewPage_Service_Section_Title;

  static public String DDE_OverviewPage_Service_Section_Description;

  // AE Configurations Page
  static public String DDE_AEConfigPage_AEConfigTree_Section_Title;

  static public String DDE_AEConfigPage_AEConfigTree_Section_Description;

  static public String DDE_AEConfigPage_AEConfig_Section_Title;

  static public String DDE_AEConfigPage_ErrorConfig_Section_Title;

  /** ************************************************************************ */

  private Messages() {
  }

  public static String getString(String key) {
    try {
      return RESOURCE_BUNDLE.getString(key);
    } catch (MissingResourceException e) {
      return '!' + key + '!';
    }
  }

  public static String getFormattedString(String key, String[] args) {
    return MessageFormat.format(RESOURCE_BUNDLE.getString(key), args);
  }

}