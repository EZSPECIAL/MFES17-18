package MFESTA;

import java.util.*;
import org.overture.codegen.runtime.*;

@SuppressWarnings("all")
public class PrinterStatus {
  private VDMSet statusT =
      SetUtil.set(
          "needA4", "needA3", "needBlackToner", "needColorToner", "needFixing", "operational");
  private VDMSet status = SetUtil.set("operational");

  public void cg_init_PrinterStatus_1() {

    return;
  }

  public PrinterStatus() {

    cg_init_PrinterStatus_1();
  }

  public void addStatus(final String stat) {

    status = SetUtil.union(Utils.copy(status), SetUtil.set(stat));
  }

  public void removeStatus(final String stat) {

    status = SetUtil.diff(Utils.copy(status), SetUtil.set(stat));
  }

  public void flipOperationalStatus() {

    VDMSet newStatus = Utils.copy(status);
    if (SetUtil.inSet("operational", status)) {
      newStatus =
          SetUtil.union(
              SetUtil.diff(Utils.copy(newStatus), SetUtil.set("operational")),
              SetUtil.set("needFixing"));
    } else {
      newStatus =
          SetUtil.union(
              SetUtil.diff(Utils.copy(newStatus), SetUtil.set("needFixing")),
              SetUtil.set("operational"));
    }

    status = Utils.copy(newStatus);
  }

  public VDMSet getStatus() {

    return this.status;
  }

  public VDMSet getStatusT() {

    return this.statusT;
  }

  public String toString() {

    return "PrinterStatus{"
        + "statusT := "
        + Utils.toString(statusT)
        + ", status := "
        + Utils.toString(status)
        + "}";
  }
}
