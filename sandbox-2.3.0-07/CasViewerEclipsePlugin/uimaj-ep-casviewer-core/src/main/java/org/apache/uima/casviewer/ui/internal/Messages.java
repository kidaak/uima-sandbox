package org.apache.uima.casviewer.ui.internal;

import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 */
public class Messages {
    private static final String BUNDLE_NAME = "messages";//$NON-NLS-1$
    
    private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);
    
    private Messages() {
    }
    
    public static String getString(String key) {
        try {
            return RESOURCE_BUNDLE.getString(key);
        } catch (MissingResourceException e) {
            return '!' + key + '!';
        }
    }
    
    public static String getFormattedString (String key, String[] args) { 
        return MessageFormat.format(RESOURCE_BUNDLE.getString(key), args); 
    }
    
    public static ResourceBundle getBundle() {
      return RESOURCE_BUNDLE; 
  }
}