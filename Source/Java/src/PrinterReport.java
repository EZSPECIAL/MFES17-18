package MFESTA;

import java.util.*;
import org.overture.codegen.runtime.*;

@SuppressWarnings("all")
public class PrinterReport {
  private Number blackA4Printed = 0L;
  private Number colorA4Printed = 0L;
  private Number blackA3Printed = 0L;
  private Number colorA3Printed = 0L;

  public void cg_init_PrinterReport_1() {

    return;
  }

  public PrinterReport() {

    cg_init_PrinterReport_1();
  }

  public void addBlackA4(final Number pages) {

    blackA4Printed = blackA4Printed.longValue() + pages.longValue();
  }

  public void addColorA4(final Number pages) {

    colorA4Printed = colorA4Printed.longValue() + pages.longValue();
  }

  public void addBlackA3(final Number pages) {

    blackA3Printed = blackA3Printed.longValue() + pages.longValue();
  }

  public void addColorA3(final Number pages) {

    colorA3Printed = colorA3Printed.longValue() + pages.longValue();
  }

  public Number getBlackA4Printed() {

    return this.blackA4Printed;
  }

  public Number getColorA4Printed() {

    return this.colorA4Printed;
  }

  public Number getBlackA3Printed() {

    return this.blackA3Printed;
  }

  public Number getColorA3Printed() {

    return this.colorA3Printed;
  }

  public String toString() {

    return "PrinterReport{"
        + "blackA4Printed := "
        + Utils.toString(blackA4Printed)
        + ", colorA4Printed := "
        + Utils.toString(colorA4Printed)
        + ", blackA3Printed := "
        + Utils.toString(blackA3Printed)
        + ", colorA3Printed := "
        + Utils.toString(colorA3Printed)
        + "}";
  }
}
