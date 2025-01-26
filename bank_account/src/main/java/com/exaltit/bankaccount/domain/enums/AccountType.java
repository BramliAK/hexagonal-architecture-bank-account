package com.exaltit.bankaccount.domain.enums;

public enum AccountType {
  SAVINGS("Savings"), CURRENT("Current");

  private final String value;

  AccountType(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }
}
