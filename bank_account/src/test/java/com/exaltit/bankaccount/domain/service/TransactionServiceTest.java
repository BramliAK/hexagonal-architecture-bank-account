package com.exaltit.bankaccount.domain.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.exaltit.bankaccount.domain.enums.TransactionType;
import com.exaltit.bankaccount.domain.model.AccountStatement;
import com.exaltit.bankaccount.domain.model.BankAccount;
import com.exaltit.bankaccount.domain.model.Transaction;
import com.exaltit.bankaccount.domain.port.output.AccountTransactionPort;
import com.exaltit.bankaccount.domain.port.output.stubs.AccountTransactionPortStub;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

class TransactionServiceTest {
  private final AccountTransactionPort transactionPort = new AccountTransactionPortStub();
  private final TransactionService transactionService = new TransactionService(transactionPort);

  @Test
  void should_createTransaction() {
    BankAccount bankAccount = new BankAccount(1L, BigDecimal.valueOf(1000.0), "current");
    Transaction transaction =
        new Transaction(bankAccount, BigDecimal.valueOf(100.0), TransactionType.DEPOSIT,
            LocalDateTime.now());
    Transaction savedTransaction = transactionService.save(transaction);
    assertEquals(BigDecimal.valueOf(100.0), savedTransaction.amount());
  }

  @Test
  void should_findAccountStatement_Return_EmptyTransactionWhenNotMatchWithRollingMonth() {
    BankAccount bankAccount = new BankAccount(1L, BigDecimal.valueOf(1000.0), "current");

    AccountStatement accountStatement = transactionService.findAccountStatement(bankAccount,
        LocalDateTime.of(2025, 3, 30, 0, 0));
    assertEquals(0, accountStatement.transactions().size());
  }

  @Test
  void should_findAccountStatement_Return_TransactionWhenMatchWithRollingMonth() {
    BankAccount bankAccount = new BankAccount(1L, BigDecimal.valueOf(1000.0), "current");
    LocalDateTime dateEmission = LocalDateTime.of(2025, 1, 24, 0, 0);
    AccountStatement accountStatement = transactionService.findAccountStatement(bankAccount,
        dateEmission);
    assertEquals(2, accountStatement.transactions().size());
    assertTrue(dateEmission.plusDays(1).isAfter(accountStatement.transactions().getFirst().transactionDate()));
    assertTrue(dateEmission.isAfter(accountStatement.transactions().getLast().transactionDate()));
  }

  @Test
  void should_findAccountStatement_Return_TransactionAnteChronologicalSorting() {
    BankAccount bankAccount = new BankAccount(1L, BigDecimal.valueOf(1000.0), "current");
    LocalDateTime dateEmission = LocalDateTime.of(2025, 1, 24, 0, 0);
    AccountStatement accountStatement = transactionService.findAccountStatement(bankAccount,
        dateEmission);
    assertEquals(2, accountStatement.transactions().size());
    assertTrue(accountStatement.transactions().getFirst().transactionDate()
        .isAfter(accountStatement.transactions().getLast().transactionDate()));
  }

  @Test
  void should_findAccountStatement_Return_BalanceRollingMonth() {
    BankAccount bankAccount = new BankAccount(1L, BigDecimal.valueOf(1000.0), "current");
    LocalDateTime dateEmission = LocalDateTime.of(2025, 2, 23, 0, 0);
    AccountStatement accountStatement = transactionService.findAccountStatement(bankAccount,
        dateEmission);
    assertEquals(BigDecimal.valueOf(900.0), accountStatement.balance());
    assertEquals(1, accountStatement.transactions().size());
  }

  @Test
  void should_findAccountStatement_Return_BalanceOnRollingMonth2() {
    BankAccount bankAccount = new BankAccount(1L, BigDecimal.valueOf(1000.0), "current");
    LocalDateTime dateEmission = LocalDateTime.of(2024, 12, 1, 0, 0);
    AccountStatement accountStatement = transactionService.findAccountStatement(bankAccount,
        dateEmission);
    assertEquals(BigDecimal.valueOf(950.0), accountStatement.balance());
    assertEquals(0, accountStatement.transactions().size());
  }


}