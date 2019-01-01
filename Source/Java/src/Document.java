package MFESTA;

import java.util.*;
import org.overture.codegen.runtime.*;

@SuppressWarnings("all")
public class Document {
  private String docName;
  private Number pagesLeft;
  private Number numPages;
  private Character pageFormat;
  private Character pageToner;

  public void cg_init_Document_1(
      final String name, final Number pages, final Character format, final Character toner) {

    docName = name;
    pagesLeft = pages;
    numPages = pages;
    pageFormat = format;
    pageToner = toner;
    return;
  }

  public Document(
      final String name, final Number pages, final Character format, final Character toner) {

    cg_init_Document_1(name, pages, format, toner);
  }

  public String getDocName() {

    return this.docName;
  }

  public Number getPagesLeft() {

    return this.pagesLeft;
  }

  public Number getNumPages() {

    return this.numPages;
  }

  public Character getPageFormat() {

    return this.pageFormat;
  }

  public Character getPageToner() {

    return this.pageToner;
  }

  public void setPagesLeft(final Number pages) {

    pagesLeft = pages;
  }

  public Document() {}

  public String toString() {

    return "Document{"
        + "docName := "
        + Utils.toString(docName)
        + ", pagesLeft := "
        + Utils.toString(pagesLeft)
        + ", numPages := "
        + Utils.toString(numPages)
        + ", pageFormat := "
        + Utils.toString(pageFormat)
        + ", pageToner := "
        + Utils.toString(pageToner)
        + "}";
  }
}
