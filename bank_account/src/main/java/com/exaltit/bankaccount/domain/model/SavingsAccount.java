package com.exaltit.bankaccount.domain.model;

import java.math.BigDecimal;

public class SavingsAccount extends BankAccount {
  private DepositLimit depositLimit;

  public SavingsAccount(Long accountNumber, BigDecimal balance, String accountType,
                        DepositLimit depositLimit) {
    super(accountNumber, balance, accountType);
    this.depositLimit = depositLimit;
  }

  public DepositLimit getDepositLimit() {
    return depositLimit;
  }

  public void setDepositLimit(DepositLimit depositLimit) {
    this.depositLimit = depositLimit;
  }
}