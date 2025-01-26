package com.exaltit.bankaccount.domain.model;

import java.math.BigDecimal;

public class CurrentAccount extends BankAccount {
  private Discovered overdraftLimit;

  public CurrentAccount(Long accountNumber, BigDecimal balance, String accountType,
                        Discovered overdraftLimit) {
    super(accountNumber, balance, accountType);
    this.overdraftLimit = overdraftLimit;
  }

  public Discovered getOverdraftLimit() {
    return overdraftLimit;
  }

  public void setOverdraftLimit(Discovered overdraftLimit) {
    this.overdraftLimit = overdraftLimit;
  }
}