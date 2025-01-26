package com.exaltit.bankaccount.domain.model;

import com.exaltit.bankaccount.domain.enums.TransactionType;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public record Transaction(Long id, BankAccount bankAccount, BigDecimal amount,
                          TransactionType transactionType, LocalDateTime transactionDate) {
  public Transaction(BankAccount bankAccount, BigDecimal amount,
                     TransactionType transactionType, LocalDateTime transactionDate) {
    this(null, bankAccount, amount, transactionType, transactionDate);
  }

  public Transaction(BankAccount bankAccount, BigDecimal amount,
                     TransactionType transactionType) {
    this(null, bankAccount, amount, transactionType, LocalDateTime.now());
  }
}
