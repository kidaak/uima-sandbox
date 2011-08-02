package org.apache.uima.tm.textmarker.cev.statistics;

import org.apache.uima.tm.cev.data.CEVDocument;
import org.apache.uima.tm.cev.editor.CEVViewer;
import org.apache.uima.tm.cev.extension.ICEVView;
import org.apache.uima.tm.cev.extension.ICEVViewFactory;

public class StatisticsViewFactory implements ICEVViewFactory {

  public StatisticsViewFactory() {
  }

  public ICEVView createView(CEVViewer viewer, CEVDocument cevDocument, int index) {
    return new StatisticsViewPage(viewer, cevDocument, index);
  }

  public Class<?> getAdapterInterface() {
    return IStatisticsViewPage.class;
  }

}