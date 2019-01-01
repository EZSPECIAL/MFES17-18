package MFESTA;

import java.util.*;
import org.overture.codegen.runtime.*;

@SuppressWarnings("all")
public class User {
  private VDMSet documentList = SetUtil.set();
  private Number balance;

  public void cg_init_User_1(final Number initBal) {

    balance = initBal;
    return;
  }

  public User(final Number initBal) {

    cg_init_User_1(initBal);
  }

  public void addDocument(final Document doc) {

    documentList = SetUtil.union(Utils.copy(documentList), SetUtil.set(doc));
  }

  public void removeDocument(final Document doc) {

    documentList = SetUtil.diff(Utils.copy(documentList), SetUtil.set(doc));
  }

  public void deposit(final Number amount) {

    balance = balance.doubleValue() + amount.doubleValue();
  }

  public void withdraw(final Number amount) {

    balance = balance.doubleValue() - amount.doubleValue();
  }

  public Number getBalance() {

    return this.balance;
  }

  public VDMSet getDocumentList() {

    return this.documentList;
  }

  public User() {}

  public String toString() {

    return "User{"
        + "documentList := "
        + Utils.toString(documentList)
        + ", balance := "
        + Utils.toString(balance)
        + "}";
  }
}
