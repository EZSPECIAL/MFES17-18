package MFESTA;

import java.util.*;
import org.overture.codegen.runtime.*;

@SuppressWarnings("all")
public class Network {
  private VDMSet printerList = SetUtil.set();

  public void cg_init_Network_2(final VDMSet printers) {

    printerList = Utils.copy(printers);
    return;
  }

  public void cg_init_Network_1() {

    return;
  }

  public Network() {

    cg_init_Network_1();
  }

  public Network(final VDMSet printers) {

    cg_init_Network_2(Utils.copy(printers));
  }

  public void addPrinter(final Printer printer) {

    printerList = SetUtil.union(Utils.copy(printerList), SetUtil.set(printer));
  }

  public void removePrinter(final Printer printer) {

    printerList = SetUtil.diff(Utils.copy(printerList), SetUtil.set(printer));
  }

  public void printIndividualReport() {

    for (Iterator iterator_6 = printerList.iterator(); iterator_6.hasNext(); ) {
      Printer printer = (Printer) iterator_6.next();
      printer.printIndividualReport();
    }
  }

  public void printGlobalReport() {

    Number numA4BlackPrinters = 0L;
    Number numA4ColorPrinters = 0L;
    Number numA3BlackPrinters = 0L;
    Number numA3ColorPrinters = 0L;
    Number sumA4BlackPages = 0L;
    Number sumA4ColorPages = 0L;
    Number sumA3BlackPages = 0L;
    Number sumA3ColorPages = 0L;
    Number sumPages = 0L;
    Number sumA4BlackMoney = 0L;
    Number sumA4ColorMoney = 0L;
    Number sumA3BlackMoney = 0L;
    Number sumA3ColorMoney = 0L;
    Number sumMoney = 0L;
    for (Iterator iterator_7 = printerList.iterator(); iterator_7.hasNext(); ) {
      Printer printer = (Printer) iterator_7.next();
      Boolean andResult_1 = false;

      if (printer.getPrinterCapabilities().getCanPrintA4()) {
        if (printer.getPrinterCapabilities().getCanPrintBlack()) {
          andResult_1 = true;
        }
      }

      if (andResult_1) {
        numA4BlackPrinters = numA4BlackPrinters.longValue() + 1L;
      }

      Boolean andResult_2 = false;

      if (printer.getPrinterCapabilities().getCanPrintA4()) {
        if (printer.getPrinterCapabilities().getCanPrintColor()) {
          andResult_2 = true;
        }
      }

      if (andResult_2) {
        numA4ColorPrinters = numA4ColorPrinters.longValue() + 1L;
      }

      Boolean andResult_3 = false;

      if (printer.getPrinterCapabilities().getCanPrintA3()) {
        if (printer.getPrinterCapabilities().getCanPrintBlack()) {
          andResult_3 = true;
        }
      }

      if (andResult_3) {
        numA3BlackPrinters = numA3BlackPrinters.longValue() + 1L;
      }

      Boolean andResult_4 = false;

      if (printer.getPrinterCapabilities().getCanPrintA3()) {
        if (printer.getPrinterCapabilities().getCanPrintColor()) {
          andResult_4 = true;
        }
      }

      if (andResult_4) {
        numA3ColorPrinters = numA3ColorPrinters.longValue() + 1L;
      }

      sumA4BlackPages =
          sumA4BlackPages.longValue() + printer.getPrinterReport().getBlackA4Printed().longValue();
      sumA4ColorPages =
          sumA4ColorPages.longValue() + printer.getPrinterReport().getColorA4Printed().longValue();
      sumA3BlackPages =
          sumA3BlackPages.longValue() + printer.getPrinterReport().getBlackA3Printed().longValue();
      sumA3ColorPages =
          sumA3ColorPages.longValue() + printer.getPrinterReport().getColorA3Printed().longValue();
      sumPages = sumPages.longValue() + printer.sumTotalPagesPrinted().longValue();
      sumA4BlackMoney =
          sumA4BlackMoney.doubleValue()
              + printer.getPrinterPricing().getPriceA4Black().doubleValue()
                  * printer.getPrinterReport().getBlackA4Printed().longValue();
      sumA4ColorMoney =
          sumA4ColorMoney.doubleValue()
              + printer.getPrinterPricing().getPriceA4Color().doubleValue()
                  * printer.getPrinterReport().getColorA4Printed().longValue();
      sumA3BlackMoney =
          sumA3BlackMoney.doubleValue()
              + printer.getPrinterPricing().getPriceA3Black().doubleValue()
                  * printer.getPrinterReport().getBlackA3Printed().longValue();
      sumA3ColorMoney =
          sumA3ColorMoney.doubleValue()
              + printer.getPrinterPricing().getPriceA3Color().doubleValue()
                  * printer.getPrinterReport().getColorA3Printed().longValue();
      sumMoney = sumMoney.doubleValue() + printer.sumTotalMoneyReceived().doubleValue();
    }
    System.out.print("Printers in network: ");
    System.out.print(this.printerList.size());
    System.out.print("\n");
    System.out.print("A4 Black capable printers: ");
    System.out.print(numA4BlackPrinters);
    System.out.print("\n");
    System.out.print("A4 Color capable printers: ");
    System.out.print(numA4ColorPrinters);
    System.out.print("\n");
    System.out.print("A3 Black capable printers: ");
    System.out.print(numA3BlackPrinters);
    System.out.print("\n");
    System.out.print("A3 Color capable printers: ");
    System.out.print(numA3ColorPrinters);
    System.out.print("\n");
    System.out.print("Number of A4 Black pages printed: ");
    System.out.print(sumA4BlackPages);
    System.out.print("\n");
    System.out.print("Number of A4 Color pages printed: ");
    System.out.print(sumA4ColorPages);
    System.out.print("\n");
    System.out.print("Number of A3 Black pages printed: ");
    System.out.print(sumA3BlackPages);
    System.out.print("\n");
    System.out.print("Number of A3 Color pages printed: ");
    System.out.print(sumA3ColorPages);
    System.out.print("\n");
    System.out.print("Total pages printed: ");
    System.out.print(sumPages);
    System.out.print("\n");
    System.out.print("Money from printing A4 Black: ");
    System.out.print(sumA4BlackMoney);
    System.out.print("\n");
    System.out.print("Money from printing A4 Color: ");
    System.out.print(sumA4ColorMoney);
    System.out.print("\n");
    System.out.print("Money from printing A3 Black: ");
    System.out.print(sumA3BlackMoney);
    System.out.print("\n");
    System.out.print("Money from printing A3 Color: ");
    System.out.print(sumA3ColorMoney);
    System.out.print("\n");
    System.out.print("Total money: ");
    System.out.print(sumMoney);
    System.out.print("\n");
    System.out.print("\n");
  }

  public VDMSet getPrinterList() {

    return this.printerList;
  }

  public String toString() {

    return "Network{" + "printerList := " + Utils.toString(printerList) + "}";
  }
}
