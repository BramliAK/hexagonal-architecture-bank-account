package com.exaltit.bankaccount.domain.model;

import java.math.BigDecimal;

public class BankAccount {
  private Long accountNumber;
  private BigDecimal balance;
  private String accountType;

  public BankAccount(Long accountNumber, BigDecimal balance, String accountType) {
    this.accountNumber = accountNumber;
    this.balance = balance;
    this.accountType = accountType;
  }

  public Long getAccountNumber() {
    return accountNumber;
  }

  public void setAccountNumber(Long accountNumber) {
    this.accountNumber = accountNumber;
  }

  public BigDecimal getBalance() {
    return balance;
  }

  public void setBalance(BigDecimal balance) {
    this.balance = balance;
  }

  public String getAccountType() {
    return accountType;
  }

  public void setAccountType(String accountType) {
    this.accountType = accountType;
  }
}
