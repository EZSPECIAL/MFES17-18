package MFESTA;

import java.util.*;
import org.overture.codegen.runtime.*;

@SuppressWarnings("all")
public class PrinterCapacity {
  private Number numOfSheetsA4;
  private Number numOfSheetsA3;
  private Number blackPrintsLeft;
  private Number colorPrintsLeft;
  private Number maxCapacityA4;
  private Number maxCapacityA3;
  private Number maxCapacityBlack;
  private Number maxCapacityColor;

  public void cg_init_PrinterCapacity_1(
      final Number numA4, final Number numA3, final Number maxBlack, final Number maxColor) {

    maxCapacityA4 = numA4;
    maxCapacityA3 = numA3;
    maxCapacityBlack = maxBlack;
    maxCapacityColor = maxColor;
    numOfSheetsA4 = numA4;
    numOfSheetsA3 = numA3;
    blackPrintsLeft = maxBlack;
    colorPrintsLeft = maxColor;
    return;
  }

  public PrinterCapacity(
      final Number numA4, final Number numA3, final Number maxBlack, final Number maxColor) {

    cg_init_PrinterCapacity_1(numA4, numA3, maxBlack, maxColor);
  }

  public Number getMaxCapacityA4() {

    return this.maxCapacityA4;
  }

  public Number getMaxCapacityA3() {

    return this.maxCapacityA3;
  }

  public Number getMaxCapacityBlack() {

    return this.maxCapacityBlack;
  }

  public Number getMaxCapacityColor() {

    return this.maxCapacityColor;
  }

  public Number getNumOfSheetsA4() {

    return this.numOfSheetsA4;
  }

  public Number getNumOfSheetsA3() {

    return this.numOfSheetsA3;
  }

  public Number getBlackPrintsLeft() {

    return this.blackPrintsLeft;
  }

  public Number getColorPrintsLeft() {

    return this.colorPrintsLeft;
  }

  public void setNumOfSheetsA4(final Number numA4) {

    numOfSheetsA4 = numA4;
  }

  public void setNumOfSheetsA3(final Number numA3) {

    numOfSheetsA3 = numA3;
  }

  public void setBlackPrintsLeft(final Number numBlacks) {

    blackPrintsLeft = numBlacks;
  }

  public void setColorPrintsLeft(final Number numColor) {

    colorPrintsLeft = numColor;
  }

  public PrinterCapacity() {}

  public String toString() {

    return "PrinterCapacity{"
        + "numOfSheetsA4 := "
        + Utils.toString(numOfSheetsA4)
        + ", numOfSheetsA3 := "
        + Utils.toString(numOfSheetsA3)
        + ", blackPrintsLeft := "
        + Utils.toString(blackPrintsLeft)
        + ", colorPrintsLeft := "
        + Utils.toString(colorPrintsLeft)
        + ", maxCapacityA4 := "
        + Utils.toString(maxCapacityA4)
        + ", maxCapacityA3 := "
        + Utils.toString(maxCapacityA3)
        + ", maxCapacityBlack := "
        + Utils.toString(maxCapacityBlack)
        + ", maxCapacityColor := "
        + Utils.toString(maxCapacityColor)
        + "}";
  }
}
