package MFESTA;

import java.util.*;
import org.overture.codegen.runtime.*;

@SuppressWarnings("all")
public class Printer {
  private String printerName;
  private PrinterCapability printerCapabilities;
  private PrinterPricing printerPricing;
  private PrinterCapacity printerCapacities;
  private PrinterStatus printerStatus;
  private PrinterReport printerReport = new PrinterReport();

  public void cg_init_Printer_1(
      final String name,
      final PrinterCapability capabilities,
      final PrinterPricing pricing,
      final PrinterCapacity capacities,
      final PrinterStatus status) {

    printerName = name;
    printerCapabilities = capabilities;
    printerPricing = pricing;
    printerCapacities = capacities;
    printerStatus = status;
    return;
  }

  public Printer(
      final String name,
      final PrinterCapability capabilities,
      final PrinterPricing pricing,
      final PrinterCapacity capacities,
      final PrinterStatus status) {

    cg_init_Printer_1(name, capabilities, pricing, capacities, status);
  }

  public void print(final Document document, final User user) {

    Number pagesToPrint = numPagesToPrint(document);
    user.withdraw(calcPrintingCost(document));
    updatePrinterToner(document, pagesToPrint);
    updatePrinterPaper(document, pagesToPrint);
    document.setPagesLeft(document.getPagesLeft().longValue() - pagesToPrint.longValue());
    if (Utils.equals(document.getPagesLeft(), 0L)) {
      user.removeDocument(document);
    }

    updatePrinterStatus();
    updatePrinterReport(document, pagesToPrint);
  }

  public Number numPagesToPrint(final Document document) {

    Number intPages = 0L;
    Boolean andResult_11 = false;

    if (Utils.equals(document.getPageFormat(), '4')) {
      if (Utils.equals(document.getPageToner(), 'B')) {
        andResult_11 = true;
      }
    }

    if (andResult_11) {
      intPages =
          minNat(printerCapacities.getNumOfSheetsA4(), printerCapacities.getBlackPrintsLeft());
    } else {
      Boolean andResult_12 = false;

      if (Utils.equals(document.getPageFormat(), '4')) {
        if (Utils.equals(document.getPageToner(), 'C')) {
          andResult_12 = true;
        }
      }

      if (andResult_12) {
        intPages =
            minNat(printerCapacities.getNumOfSheetsA4(), printerCapacities.getColorPrintsLeft());
      } else {
        Boolean andResult_13 = false;

        if (Utils.equals(document.getPageFormat(), '3')) {
          if (Utils.equals(document.getPageToner(), 'B')) {
            andResult_13 = true;
          }
        }

        if (andResult_13) {
          intPages =
              minNat(printerCapacities.getNumOfSheetsA3(), printerCapacities.getBlackPrintsLeft());
        } else {
          Boolean andResult_14 = false;

          if (Utils.equals(document.getPageFormat(), '3')) {
            if (Utils.equals(document.getPageToner(), 'C')) {
              andResult_14 = true;
            }
          }

          if (andResult_14) {
            intPages =
                minNat(
                    printerCapacities.getNumOfSheetsA3(), printerCapacities.getColorPrintsLeft());
          }
        }
      }
    }

    if (document.getPagesLeft().longValue() > intPages.longValue()) {
      return intPages;

    } else {
      return document.getPagesLeft();
    }
  }

  private Number minNat(final Number a, final Number b) {

    if (a.longValue() > b.longValue()) {
      return b;

    } else {
      return a;
    }
  }

  public Number calcPrintingCost(final Document document) {

    Boolean andResult_15 = false;

    if (Utils.equals(document.getPageFormat(), '4')) {
      if (Utils.equals(document.getPageToner(), 'B')) {
        andResult_15 = true;
      }
    }

    if (andResult_15) {
      return printerPricing.getPriceA4Black().doubleValue() * numPagesToPrint(document).longValue();

    } else {
      Boolean andResult_16 = false;

      if (Utils.equals(document.getPageFormat(), '4')) {
        if (Utils.equals(document.getPageToner(), 'C')) {
          andResult_16 = true;
        }
      }

      if (andResult_16) {
        return printerPricing.getPriceA4Color().doubleValue()
            * numPagesToPrint(document).longValue();

      } else {
        Boolean andResult_17 = false;

        if (Utils.equals(document.getPageFormat(), '3')) {
          if (Utils.equals(document.getPageToner(), 'B')) {
            andResult_17 = true;
          }
        }

        if (andResult_17) {
          return printerPricing.getPriceA3Black().doubleValue()
              * numPagesToPrint(document).longValue();

        } else {
          Boolean andResult_18 = false;

          if (Utils.equals(document.getPageFormat(), '3')) {
            if (Utils.equals(document.getPageToner(), 'C')) {
              andResult_18 = true;
            }
          }

          if (andResult_18) {
            return printerPricing.getPriceA3Color().doubleValue()
                * numPagesToPrint(document).longValue();

          } else {
            return 0L;
          }
        }
      }
    }
  }

  private Boolean checkStatusCapacityCoupling() {

    if (printerCapabilities.getCanPrintA4()) {
      return !(SetUtil.inSet("needA4", printerStatus.getStatus())
          ^ (Utils.equals(printerCapacities.getNumOfSheetsA4(), 0L)));
    }

    if (printerCapabilities.getCanPrintA3()) {
      return !(SetUtil.inSet("needA3", printerStatus.getStatus())
          ^ (Utils.equals(printerCapacities.getNumOfSheetsA3(), 0L)));
    }

    if (printerCapabilities.getCanPrintBlack()) {
      return !(SetUtil.inSet("needBlackToner", printerStatus.getStatus())
          ^ (Utils.equals(printerCapacities.getBlackPrintsLeft(), 0L)));
    }

    if (printerCapabilities.getCanPrintColor()) {
      return !(SetUtil.inSet("needColorToner", printerStatus.getStatus())
          ^ (Utils.equals(printerCapacities.getColorPrintsLeft(), 0L)));
    }

    return true;
  }

  private Boolean checkPrinterTonerCapability(final Document document) {

    Boolean andResult_19 = false;

    if (Utils.equals(document.getPageToner(), 'B')) {
      if (printerCapabilities.getCanPrintBlack()) {
        andResult_19 = true;
      }
    }

    if (andResult_19) {
      return true;

    } else {
      Boolean andResult_20 = false;

      if (Utils.equals(document.getPageToner(), 'C')) {
        if (printerCapabilities.getCanPrintColor()) {
          andResult_20 = true;
        }
      }

      if (andResult_20) {
        return true;

      } else {
        return false;
      }
    }
  }

  private Boolean checkPrinterHasToner(final Document document) {

    Boolean andResult_21 = false;

    if (Utils.equals(document.getPageToner(), 'B')) {
      if (Utils.equals(printerCapacities.getBlackPrintsLeft(), 0L)) {
        andResult_21 = true;
      }
    }

    if (andResult_21) {
      return false;

    } else {
      Boolean andResult_22 = false;

      if (Utils.equals(document.getPageToner(), 'C')) {
        if (Utils.equals(printerCapacities.getColorPrintsLeft(), 0L)) {
          andResult_22 = true;
        }
      }

      if (andResult_22) {
        return false;

      } else {
        return true;
      }
    }
  }

  private void updatePrinterToner(final Document document, final Number pagesPrinted) {

    if (Utils.equals(document.getPageToner(), 'B')) {
      printerCapacities.setBlackPrintsLeft(
          printerCapacities.getBlackPrintsLeft().longValue() - pagesPrinted.longValue());
    } else if (Utils.equals(document.getPageToner(), 'C')) {
      printerCapacities.setColorPrintsLeft(
          printerCapacities.getColorPrintsLeft().longValue() - pagesPrinted.longValue());
    }
  }

  private Boolean checkPrinterPageFormatCapability(final Document document) {

    Boolean andResult_23 = false;

    if (Utils.equals(document.getPageFormat(), '4')) {
      if (printerCapabilities.getCanPrintA4()) {
        andResult_23 = true;
      }
    }

    if (andResult_23) {
      return true;

    } else {
      Boolean andResult_24 = false;

      if (Utils.equals(document.getPageFormat(), '3')) {
        if (printerCapabilities.getCanPrintA3()) {
          andResult_24 = true;
        }
      }

      if (andResult_24) {
        return true;

      } else {
        return false;
      }
    }
  }

  private Boolean checkPrinterHasPaper(final Document document) {

    Boolean andResult_25 = false;

    if (Utils.equals(document.getPageFormat(), '4')) {
      if (Utils.equals(printerCapacities.getNumOfSheetsA4(), 0L)) {
        andResult_25 = true;
      }
    }

    if (andResult_25) {
      return false;

    } else {
      Boolean andResult_26 = false;

      if (Utils.equals(document.getPageFormat(), '3')) {
        if (Utils.equals(printerCapacities.getNumOfSheetsA3(), 0L)) {
          andResult_26 = true;
        }
      }

      if (andResult_26) {
        return false;

      } else {
        return true;
      }
    }
  }

  private void updatePrinterPaper(final Document document, final Number pagesPrinted) {

    if (Utils.equals(document.getPageFormat(), '4')) {
      printerCapacities.setNumOfSheetsA4(
          printerCapacities.getNumOfSheetsA4().longValue() - pagesPrinted.longValue());
    } else if (Utils.equals(document.getPageFormat(), '3')) {
      printerCapacities.setNumOfSheetsA3(
          printerCapacities.getNumOfSheetsA3().longValue() - pagesPrinted.longValue());
    }
  }

  private void updatePrinterStatus() {

    Boolean andResult_27 = false;

    if (Utils.equals(printerCapacities.getNumOfSheetsA4(), 0L)) {
      if (printerCapabilities.getCanPrintA4()) {
        andResult_27 = true;
      }
    }

    if (andResult_27) {
      printerStatus.addStatus("needA4");
    }

    Boolean andResult_28 = false;

    if (Utils.equals(printerCapacities.getNumOfSheetsA3(), 0L)) {
      if (printerCapabilities.getCanPrintA3()) {
        andResult_28 = true;
      }
    }

    if (andResult_28) {
      printerStatus.addStatus("needA3");
    }

    Boolean andResult_29 = false;

    if (Utils.equals(printerCapacities.getBlackPrintsLeft(), 0L)) {
      if (printerCapabilities.getCanPrintBlack()) {
        andResult_29 = true;
      }
    }

    if (andResult_29) {
      printerStatus.addStatus("needBlackToner");
    }

    Boolean andResult_30 = false;

    if (Utils.equals(printerCapacities.getColorPrintsLeft(), 0L)) {
      if (printerCapabilities.getCanPrintColor()) {
        andResult_30 = true;
      }
    }

    if (andResult_30) {
      printerStatus.addStatus("needColorToner");
    }
  }

  private void updatePrinterReport(final Document document, final Number pages) {

    Boolean andResult_31 = false;

    if (Utils.equals(document.getPageFormat(), '4')) {
      if (Utils.equals(document.getPageToner(), 'B')) {
        andResult_31 = true;
      }
    }

    if (andResult_31) {
      printerReport.addBlackA4(pages);
    } else {
      Boolean andResult_32 = false;

      if (Utils.equals(document.getPageFormat(), '4')) {
        if (Utils.equals(document.getPageToner(), 'C')) {
          andResult_32 = true;
        }
      }

      if (andResult_32) {
        printerReport.addColorA4(pages);
      } else {
        Boolean andResult_33 = false;

        if (Utils.equals(document.getPageFormat(), '3')) {
          if (Utils.equals(document.getPageToner(), 'B')) {
            andResult_33 = true;
          }
        }

        if (andResult_33) {
          printerReport.addBlackA3(pages);
        } else {
          Boolean andResult_34 = false;

          if (Utils.equals(document.getPageFormat(), '3')) {
            if (Utils.equals(document.getPageToner(), 'C')) {
              andResult_34 = true;
            }
          }

          if (andResult_34) {
            printerReport.addColorA3(pages);
          }
        }
      }
    }
  }

  public void break_(final VDMSet statusSet) {

    for (Iterator iterator_8 = statusSet.iterator(); iterator_8.hasNext(); ) {
      String status = (String) iterator_8.next();
      if (Utils.equals(status, "needA4")) {
        printerCapacities.setNumOfSheetsA4(0L);
        printerStatus.addStatus("needA4");

      } else if (Utils.equals(status, "needA3")) {
        printerCapacities.setNumOfSheetsA3(0L);
        printerStatus.addStatus("needA3");

      } else if (Utils.equals(status, "needBlackToner")) {
        printerCapacities.setBlackPrintsLeft(0L);
        printerStatus.addStatus("needBlackToner");

      } else if (Utils.equals(status, "needColorToner")) {
        printerCapacities.setColorPrintsLeft(0L);
        printerStatus.addStatus("needColorToner");
      }

      Boolean andResult_35 = false;

      if (Utils.equals(status, "needFixing")) {
        if (SetUtil.inSet("operational", printerStatus.getStatus())) {
          andResult_35 = true;
        }
      }

      if (andResult_35) {
        printerStatus.flipOperationalStatus();
      }
    }
  }

  public void breakAll() {

    if (printerCapabilities.getCanPrintA4()) {
      printerCapacities.setNumOfSheetsA4(0L);
      printerStatus.addStatus("needA4");
    }

    if (printerCapabilities.getCanPrintA3()) {
      printerCapacities.setNumOfSheetsA3(0L);
      printerStatus.addStatus("needA3");
    }

    if (printerCapabilities.getCanPrintBlack()) {
      printerCapacities.setBlackPrintsLeft(0L);
      printerStatus.addStatus("needBlackToner");
    }

    if (printerCapabilities.getCanPrintColor()) {
      printerCapacities.setColorPrintsLeft(0L);
      printerStatus.addStatus("needColorToner");
    }

    if (SetUtil.inSet("operational", printerStatus.getStatus())) {
      printerStatus.flipOperationalStatus();
    }
  }

  public void fix(final VDMSet statusSet) {

    for (Iterator iterator_9 = statusSet.iterator(); iterator_9.hasNext(); ) {
      String status = (String) iterator_9.next();
      if (Utils.equals(status, "needA4")) {
        printerCapacities.setNumOfSheetsA4(printerCapacities.getMaxCapacityA4());
        printerStatus.removeStatus("needA4");

      } else if (Utils.equals(status, "needA3")) {
        printerCapacities.setNumOfSheetsA3(printerCapacities.getMaxCapacityA3());
        printerStatus.removeStatus("needA3");

      } else if (Utils.equals(status, "needBlackToner")) {
        printerCapacities.setBlackPrintsLeft(printerCapacities.getMaxCapacityBlack());
        printerStatus.removeStatus("needBlackToner");

      } else if (Utils.equals(status, "needColorToner")) {
        printerCapacities.setColorPrintsLeft(printerCapacities.getMaxCapacityColor());
        printerStatus.removeStatus("needColorToner");
      }

      Boolean andResult_37 = false;

      if (Utils.equals(status, "needFixing")) {
        if (SetUtil.inSet("needFixing", printerStatus.getStatus())) {
          andResult_37 = true;
        }
      }

      if (andResult_37) {
        printerStatus.flipOperationalStatus();
      }
    }
  }

  public void fixAll() {

    for (Iterator iterator_10 = printerStatus.getStatus().iterator(); iterator_10.hasNext(); ) {
      String status = (String) iterator_10.next();
      Boolean andResult_38 = false;

      if (printerCapabilities.getCanPrintA4()) {
        if (Utils.equals(status, "needA4")) {
          andResult_38 = true;
        }
      }

      if (andResult_38) {
        printerCapacities.setNumOfSheetsA4(printerCapacities.getMaxCapacityA4());
        printerStatus.removeStatus("needA4");
      }

      Boolean andResult_39 = false;

      if (printerCapabilities.getCanPrintA3()) {
        if (Utils.equals(status, "needA3")) {
          andResult_39 = true;
        }
      }

      if (andResult_39) {
        printerCapacities.setNumOfSheetsA3(printerCapacities.getMaxCapacityA3());
        printerStatus.removeStatus("needA3");
      }

      Boolean andResult_40 = false;

      if (printerCapabilities.getCanPrintBlack()) {
        if (Utils.equals(status, "needBlackToner")) {
          andResult_40 = true;
        }
      }

      if (andResult_40) {
        printerCapacities.setBlackPrintsLeft(printerCapacities.getMaxCapacityBlack());
        printerStatus.removeStatus("needBlackToner");
      }

      Boolean andResult_41 = false;

      if (printerCapabilities.getCanPrintColor()) {
        if (Utils.equals(status, "needColorToner")) {
          andResult_41 = true;
        }
      }

      if (andResult_41) {
        printerCapacities.setColorPrintsLeft(printerCapacities.getMaxCapacityColor());
        printerStatus.removeStatus("needColorToner");
      }

      if (SetUtil.inSet("needFixing", printerStatus.getStatus())) {
        printerStatus.flipOperationalStatus();
      }
    }
  }

  public void printIndividualReport() {

    System.out.print("Name: ");
    System.out.print(this.printerName);
    System.out.print("\n");
    System.out.print("Number of A4 Black pages printed: ");
    Boolean andResult_42 = false;

    if (printerCapabilities.getCanPrintA4()) {
      if (printerCapabilities.getCanPrintBlack()) {
        andResult_42 = true;
      }
    }

    if (andResult_42) {
      System.out.print(printerReport.getBlackA4Printed());
    } else {
      System.out.print("N/A");
    }

    System.out.print("\n");
    System.out.print("Number of A4 Color pages printed: ");
    Boolean andResult_43 = false;

    if (printerCapabilities.getCanPrintA4()) {
      if (printerCapabilities.getCanPrintColor()) {
        andResult_43 = true;
      }
    }

    if (andResult_43) {
      System.out.print(printerReport.getColorA4Printed());
    } else {
      System.out.print("N/A");
    }

    System.out.print("\n");
    System.out.print("Number of A3 Black pages printed: ");
    Boolean andResult_44 = false;

    if (printerCapabilities.getCanPrintA3()) {
      if (printerCapabilities.getCanPrintBlack()) {
        andResult_44 = true;
      }
    }

    if (andResult_44) {
      System.out.print(printerReport.getBlackA3Printed());
    } else {
      System.out.print("N/A");
    }

    System.out.print("\n");
    System.out.print("Number of A3 Color pages printed: ");
    Boolean andResult_45 = false;

    if (printerCapabilities.getCanPrintA3()) {
      if (printerCapabilities.getCanPrintColor()) {
        andResult_45 = true;
      }
    }

    if (andResult_45) {
      System.out.print(printerReport.getColorA3Printed());
    } else {
      System.out.print("N/A");
    }

    System.out.print("\n");
    System.out.print("Total pages printed: ");
    System.out.print(sumTotalPagesPrinted());
    System.out.print("\n");
    System.out.print("Money from printing A4 Black: ");
    Boolean andResult_46 = false;

    if (printerCapabilities.getCanPrintA4()) {
      if (printerCapabilities.getCanPrintBlack()) {
        andResult_46 = true;
      }
    }

    if (andResult_46) {
      System.out.print(
          printerPricing.getPriceA4Black().doubleValue()
              * printerReport.getBlackA4Printed().longValue());
    } else {
      System.out.print("N/A");
    }

    System.out.print("\n");
    System.out.print("Money from printing A4 Color: ");
    Boolean andResult_47 = false;

    if (printerCapabilities.getCanPrintA4()) {
      if (printerCapabilities.getCanPrintColor()) {
        andResult_47 = true;
      }
    }

    if (andResult_47) {
      System.out.print(
          printerPricing.getPriceA4Color().doubleValue()
              * printerReport.getColorA4Printed().longValue());
    } else {
      System.out.print("N/A");
    }

    System.out.print("\n");
    System.out.print("Money from printing A3 Black: ");
    Boolean andResult_48 = false;

    if (printerCapabilities.getCanPrintA3()) {
      if (printerCapabilities.getCanPrintBlack()) {
        andResult_48 = true;
      }
    }

    if (andResult_48) {
      System.out.print(
          printerPricing.getPriceA3Black().doubleValue()
              * printerReport.getBlackA3Printed().longValue());
    } else {
      System.out.print("N/A");
    }

    System.out.print("\n");
    System.out.print("Money from printing A3 Color: ");
    Boolean andResult_49 = false;

    if (printerCapabilities.getCanPrintA3()) {
      if (printerCapabilities.getCanPrintColor()) {
        andResult_49 = true;
      }
    }

    if (andResult_49) {
      System.out.print(
          printerPricing.getPriceA3Color().doubleValue()
              * printerReport.getColorA3Printed().longValue());
    } else {
      System.out.print("N/A");
    }

    System.out.print("\n");
    System.out.print("Total money: ");
    System.out.print(sumTotalMoneyReceived());
    System.out.print("\n");
    System.out.print("\n");
  }

  public Number sumTotalPagesPrinted() {

    return printerReport.getBlackA4Printed().longValue()
        + printerReport.getColorA4Printed().longValue()
        + printerReport.getBlackA3Printed().longValue()
        + printerReport.getColorA3Printed().longValue();
  }

  public Number sumTotalMoneyReceived() {

    return printerPricing.getPriceA4Black().doubleValue()
            * printerReport.getBlackA4Printed().longValue()
        + printerPricing.getPriceA4Color().doubleValue()
            * printerReport.getColorA4Printed().longValue()
        + printerPricing.getPriceA3Black().doubleValue()
            * printerReport.getBlackA3Printed().longValue()
        + printerPricing.getPriceA3Color().doubleValue()
            * printerReport.getColorA3Printed().longValue();
  }

  public String getPrinterName() {

    return this.printerName;
  }

  public PrinterReport getPrinterReport() {

    return this.printerReport;
  }

  public PrinterPricing getPrinterPricing() {

    return this.printerPricing;
  }

  public PrinterCapability getPrinterCapabilities() {

    return this.printerCapabilities;
  }

  public PrinterCapacity getPrinterCapacities() {

    return this.printerCapacities;
  }

  public PrinterStatus getPrinterStatus() {

    return this.printerStatus;
  }

  public Printer() {}

  public String toString() {

    return "Printer{"
        + "printerName := "
        + Utils.toString(printerName)
        + ", printerCapabilities := "
        + Utils.toString(printerCapabilities)
        + ", printerPricing := "
        + Utils.toString(printerPricing)
        + ", printerCapacities := "
        + Utils.toString(printerCapacities)
        + ", printerStatus := "
        + Utils.toString(printerStatus)
        + ", printerReport := "
        + Utils.toString(printerReport)
        + "}";
  }
}
