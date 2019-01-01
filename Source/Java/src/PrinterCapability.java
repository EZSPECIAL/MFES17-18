package MFESTA;

import java.util.*;
import org.overture.codegen.runtime.*;

@SuppressWarnings("all")
public class PrinterCapability {
  private Boolean canPrintA4;
  private Boolean canPrintA3;
  private Boolean canPrintBlack;
  private Boolean canPrintColor;

  public void cg_init_PrinterCapability_1(
      final Boolean printA4,
      final Boolean printA3,
      final Boolean printBlack,
      final Boolean printColor) {

    canPrintA4 = printA4;
    canPrintA3 = printA3;
    canPrintBlack = printBlack;
    canPrintColor = printColor;
    return;
  }

  public PrinterCapability(
      final Boolean printA4,
      final Boolean printA3,
      final Boolean printBlack,
      final Boolean printColor) {

    cg_init_PrinterCapability_1(printA4, printA3, printBlack, printColor);
  }

  public Boolean getCanPrintA4() {

    return this.canPrintA4;
  }

  public Boolean getCanPrintA3() {

    return this.canPrintA3;
  }

  public Boolean getCanPrintBlack() {

    return this.canPrintBlack;
  }

  public Boolean getCanPrintColor() {

    return this.canPrintColor;
  }

  public PrinterCapability() {}

  public String toString() {

    return "PrinterCapability{"
        + "canPrintA4 := "
        + Utils.toString(canPrintA4)
        + ", canPrintA3 := "
        + Utils.toString(canPrintA3)
        + ", canPrintBlack := "
        + Utils.toString(canPrintBlack)
        + ", canPrintColor := "
        + Utils.toString(canPrintColor)
        + "}";
  }
}
