package MFESTA;

import java.util.*;
import org.overture.codegen.runtime.*;

@SuppressWarnings("all")
public class PrinterPricing {
  private Number priceA4Black;
  private Number priceA4Color;
  private Number priceA3Black;
  private Number priceA3Color;

  public void cg_init_PrinterPricing_1(
      final Number priceA4B, final Number priceA4C, final Number priceA3B, final Number priceA3C) {

    priceA4Black = priceA4B;
    priceA4Color = priceA4C;
    priceA3Black = priceA3B;
    priceA3Color = priceA3C;
    return;
  }

  public PrinterPricing(
      final Number priceA4B, final Number priceA4C, final Number priceA3B, final Number priceA3C) {

    cg_init_PrinterPricing_1(priceA4B, priceA4C, priceA3B, priceA3C);
  }

  public Number getPriceA4Black() {

    return this.priceA4Black;
  }

  public Number getPriceA4Color() {

    return this.priceA4Color;
  }

  public Number getPriceA3Black() {

    return this.priceA3Black;
  }

  public Number getPriceA3Color() {

    return this.priceA3Color;
  }

  public PrinterPricing() {}

  public String toString() {

    return "PrinterPricing{"
        + "priceA4Black := "
        + Utils.toString(priceA4Black)
        + ", priceA4Color := "
        + Utils.toString(priceA4Color)
        + ", priceA3Black := "
        + Utils.toString(priceA3Black)
        + ", priceA3Color := "
        + Utils.toString(priceA3Color)
        + "}";
  }
}
