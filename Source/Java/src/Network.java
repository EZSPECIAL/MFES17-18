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
    IO.print("Printers in network: ");
    IO.print(this.printerList.size());
    IO.print("\n");
    IO.print("A4 Black capable printers: ");
    IO.print(numA4BlackPrinters);
    IO.print("\n");
    IO.print("A4 Color capable printers: ");
    IO.print(numA4ColorPrinters);
    IO.print("\n");
    IO.print("A3 Black capable printers: ");
    IO.print(numA3BlackPrinters);
    IO.print("\n");
    IO.print("A3 Color capable printers: ");
    IO.print(numA3ColorPrinters);
    IO.print("\n");
    IO.print("Number of A4 Black pages printed: ");
    IO.print(sumA4BlackPages);
    IO.print("\n");
    IO.print("Number of A4 Color pages printed: ");
    IO.print(sumA4ColorPages);
    IO.print("\n");
    IO.print("Number of A3 Black pages printed: ");
    IO.print(sumA3BlackPages);
    IO.print("\n");
    IO.print("Number of A3 Color pages printed: ");
    IO.print(sumA3ColorPages);
    IO.print("\n");
    IO.print("Total pages printed: ");
    IO.print(sumPages);
    IO.print("\n");
    IO.print("Money from printing A4 Black: ");
    IO.print(sumA4BlackMoney);
    IO.print("\n");
    IO.print("Money from printing A4 Color: ");
    IO.print(sumA4ColorMoney);
    IO.print("\n");
    IO.print("Money from printing A3 Black: ");
    IO.print(sumA3BlackMoney);
    IO.print("\n");
    IO.print("Money from printing A3 Color: ");
    IO.print(sumA3ColorMoney);
    IO.print("\n");
    IO.print("Total money: ");
    IO.print(sumMoney);
    IO.print("\n");
    IO.print("\n");
  }

  public String toString() {

    return "Network{" + "printerList := " + Utils.toString(printerList) + "}";
  }
}
