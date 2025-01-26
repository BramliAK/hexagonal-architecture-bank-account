package com.exaltit.bankaccount.domain.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.exaltit.bankaccount.domain.exceptions.KataFunctionalException;
import com.exaltit.bankaccount.domain.model.SavingsAccount;
import com.exaltit.bankaccount.domain.port.output.AccountPort;
import com.exaltit.bankaccount.domain.port.output.stubs.SavingsAccountPortStub;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

class SavingsAccountServiceTest {

  private final AccountPort<SavingsAccount> accountPort = new SavingsAccountPortStub();
  private final SavingsAccountService savingsAccountService =
      new SavingsAccountService(accountPort);

  @Test
  void should_deposit_Throw_FunctionalException_When_AmountIsNull() {
    Throwable exception = assertThrows(KataFunctionalException.class,
        () -> savingsAccountService.deposit(1L, null));

    assertEquals("The amount must be informed", exception.getMessage());
  }

  @Test
  void should_deposit_Throw_FunctionalException_When_AmountLessThanZero() {
    Throwable exception = assertThrows(KataFunctionalException.class,
        () -> savingsAccountService.deposit(1L, BigDecimal.valueOf(-1)));

    assertEquals("The amount must be greater than zero", exception.getMessage());
  }

  @Test
  void should_deposit_Throw_FunctionalException_When_AccountNumberIsNull() {
    Throwable exception = assertThrows(KataFunctionalException.class,
        () -> savingsAccountService.deposit(null, BigDecimal.valueOf(100)));
    assertEquals("The account number must be informed", exception.getMessage());
  }

  @Test
  void should_deposit_Throw_ThrowFunctionalException_WhenHasExceededDepositLimit() {
    Throwable exception = assertThrows(KataFunctionalException.class,
        () -> savingsAccountService.deposit(1L, BigDecimal.valueOf(150)));
    assertEquals("The amount exceeds the deposit limit", exception.getMessage());
  }

  @Test
  void should_deposit_UpdateBalanceWithTheAmount_WhenAmountLessThanDepositLimit() {
    SavingsAccount savingsAccount = savingsAccountService.deposit(1L, BigDecimal.valueOf(90));
    assertEquals(BigDecimal.valueOf(1090.0), savingsAccount.getBalance());
  }

  @Test
  void should_deposit_UpdateBalanceWithTheAmount_WhenAmountEqualThanDepositLimit() {
    SavingsAccount savingsAccount = savingsAccountService.deposit(1L, BigDecimal.valueOf(100));
    assertEquals(BigDecimal.valueOf(1100.0), savingsAccount.getBalance());
  }

  @Test
  void should_withdraw_Throw_FunctionalException_When_AmountIsNull() {
    Throwable exception = assertThrows(KataFunctionalException.class,
        () -> savingsAccountService.withdraw(1L, null));

    assertEquals("The amount must be informed", exception.getMessage());
  }

  @Test
  void should_withdraw_Throw_FunctionalException_When_AmountLessThanZero() {
    Throwable exception = assertThrows(KataFunctionalException.class,
        () -> savingsAccountService.withdraw(1L, BigDecimal.valueOf(-1)));

    assertEquals("The amount must be greater than zero", exception.getMessage());
  }

  @Test
  void should_withdraw_Throw_FunctionalException_When_AccountNumberIsNull() {
    Throwable exception = assertThrows(KataFunctionalException.class,
        () -> savingsAccountService.withdraw(null, BigDecimal.valueOf(100)));

    assertEquals("The account number must be informed", exception.getMessage());
  }

  @Test
  void should_withdraw_ThrowFunctionalException_WhenHasExceededTheBalance() {
    Throwable exception = assertThrows(KataFunctionalException.class,
        () -> savingsAccountService.withdraw(1L, BigDecimal.valueOf(1100)));

    assertEquals("Insufficient balance", exception.getMessage());
  }

  @Test
  void should_withdraw_SubtractBalanceWithTheAmount() {
    SavingsAccount savingsAccount = savingsAccountService.withdraw(1L, BigDecimal.valueOf(900));
    assertEquals(BigDecimal.valueOf(100.0), savingsAccount.getBalance());
  }

  @Test
  void should_withdraw_SubtractBalanceWithAllDiscoveryAmount() {
    SavingsAccount savingsAccount = savingsAccountService.withdraw(1L, BigDecimal.valueOf(1000.0));
    assertEquals(BigDecimal.valueOf(0.0), savingsAccount.getBalance());
  }

  @Test
  void should_findBankAccount_Return_CurrentAccount() {
    SavingsAccount savingsAccount = savingsAccountService.findBankAccount(1L);
    assertNotNull(savingsAccount);
    assertEquals(1L, savingsAccount.getAccountNumber());
  }
}