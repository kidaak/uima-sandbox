package org.apache.uima.tm.cev.views;

import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.internal.views.ViewsPlugin;
import org.eclipse.ui.part.IPage;
import org.eclipse.ui.part.IPageBookViewPage;
import org.eclipse.ui.part.MessagePage;
import org.eclipse.ui.part.PageBook;
import org.eclipse.ui.part.PageBookView;

/**
 * Abstrakter PageBookView fuer den AnnotationBrowser und den SelectionView.
 * 
 * @author Marco Nehmeier
 */
public abstract class CEVPageBookView extends PageBookView {

  /**
   * Text wenn kein View vom Editor zur Verfuegung gestellt wird.
   */
  private static final String VIEW_IS_NOT_AVAILABLE = "View is not available.";

  // private PageRec last = null;

  /**
   * Defaultkonstruktor.
   */
  public CEVPageBookView() {
    super();
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.ui.part.PageBookView#createDefaultPage(org.eclipse.ui.part.PageBook)
   */
  @Override
  protected IPage createDefaultPage(PageBook book) {
    // MessagePage wenn der View nicht zur Verfuegung steht
    MessagePage page = new MessagePage();
    initPage(page);
    page.createControl(book);
    page.setMessage(VIEW_IS_NOT_AVAILABLE);
    return page;
  }

  /**
   * Erzeugt die jeweilige Page
   * 
   * @param part
   *          zugrundeliegender WorkbenchPart
   * @param clazz
   *          Klasse der Page
   * @return PageRec
   */
  protected PageRec doCreatePage(IWorkbenchPart part, final Class<?> clazz) {
    Object obj = ViewsPlugin.getAdapter(part, clazz, false);
    if (obj instanceof ICEVViewPage) {
      ICEVViewPage page = (ICEVViewPage) obj;
      if (page instanceof IPageBookViewPage) {
        initPage(page);
      }
      page.createControl(getPageBook());
      PageRec pageRec = new PageRec(part, page);
      // last = pageRec;
      return pageRec;
    } else {
      return null;
    }
  }

  //
  // public void dispose() {
  // last.dispose();
  // super.dispose();
  // }

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.ui.part.PageBookView#doDestroyPage(org.eclipse.ui.IWorkbenchPart,
   * org.eclipse.ui.part.PageBookView.PageRec)
   */
  @Override
  protected void doDestroyPage(IWorkbenchPart part, PageRec pageRecord) {
    pageRecord.page.dispose();
    pageRecord.dispose();
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.ui.part.PageBookView#getBootstrapPart()
   */
  @Override
  protected IWorkbenchPart getBootstrapPart() {
    IWorkbenchPage page = getSite().getPage();
    if (page != null) {
      return page.getActiveEditor();
    }

    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.ui.part.PageBookView#isImportant(org.eclipse.ui.IWorkbenchPart)
   */
  @Override
  protected boolean isImportant(IWorkbenchPart part) {
    return (part instanceof IEditorPart);
  }
}