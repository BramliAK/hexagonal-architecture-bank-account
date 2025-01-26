package com.exaltit.bankaccount.domain.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.exaltit.bankaccount.domain.exceptions.KataFunctionalException;
import com.exaltit.bankaccount.domain.model.CurrentAccount;
import com.exaltit.bankaccount.domain.port.output.AccountPort;
import com.exaltit.bankaccount.domain.port.output.stubs.CurrentAccountPortStub;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

class CurrentAccountServiceTest {

  private final AccountPort<CurrentAccount> accountPort = new CurrentAccountPortStub();
  private final CurrentAccountService currentAccountService =
      new CurrentAccountService(accountPort);

  @Test
  void should_deposit_Throw_FunctionalException_When_AmountIsNull() {
    Throwable exception = assertThrows(KataFunctionalException.class,
        () -> currentAccountService.deposit(1L, null));

    assertEquals("The amount must be informed", exception.getMessage());
  }

  @Test
  void should_deposit_Throw_FunctionalException_When_AmountLessThanZero() {
    Throwable exception = assertThrows(KataFunctionalException.class,
        () -> currentAccountService.deposit(1L, BigDecimal.valueOf(-1)));

    assertEquals("The amount must be greater than zero", exception.getMessage());
  }

  @Test
  void should_deposit_Throw_FunctionalException_When_AccountNumberIsNull() {
    Throwable exception = assertThrows(KataFunctionalException.class,
        () -> currentAccountService.deposit(null, BigDecimal.valueOf(100)));

    assertEquals("The account number must be informed", exception.getMessage());
  }

  @Test
  void should_deposit_UpdateBalanceWithTheAmount() {
    CurrentAccount currentAccount = currentAccountService.deposit(1L, BigDecimal.valueOf(100));
    assertEquals(BigDecimal.valueOf(1100.0), currentAccount.getBalance());
  }

  @Test
  void should_withdraw_Throw_FunctionalException_When_AmountIsNull() {
    Throwable exception = assertThrows(KataFunctionalException.class,
        () -> currentAccountService.withdraw(1L, null));

    assertEquals("The amount must be informed", exception.getMessage());
  }

  @Test
  void should_withdraw_Throw_FunctionalException_When_AmountLessThanZero() {
    Throwable exception = assertThrows(KataFunctionalException.class,
        () -> currentAccountService.withdraw(1L, BigDecimal.valueOf(-1)));

    assertEquals("The amount must be greater than zero", exception.getMessage());
  }

  @Test
  void should_withdraw_Throw_FunctionalException_When_AccountNumberIsNull() {
    Throwable exception = assertThrows(KataFunctionalException.class,
        () -> currentAccountService.withdraw(null, BigDecimal.valueOf(100)));

    assertEquals("The account number must be informed", exception.getMessage());
  }

  @Test
  void should_withdraw_ThrowFunctionalException_WhenHasExceededTheOverdraft() {
    Throwable exception = assertThrows(KataFunctionalException.class,
        () -> currentAccountService.withdraw(1L, BigDecimal.valueOf(2000)));

    assertEquals("Insufficient balance", exception.getMessage());
  }

  @Test
  void should_withdraw_SubtractBalanceWithTheAmount() {
    CurrentAccount currentAccount = currentAccountService.withdraw(1L, BigDecimal.valueOf(900));
    assertEquals(BigDecimal.valueOf(100.0), currentAccount.getBalance());
  }

  @Test
  void should_withdraw_SubtractBalanceWithAllDiscoveryAmount() {
    CurrentAccount currentAccount = currentAccountService.withdraw(1L, BigDecimal.valueOf(1100.0));
    assertEquals(BigDecimal.valueOf(-100.0), currentAccount.getBalance());
  }

  @Test
  void should_findBankAccount_Return_CurrentAccount() {
    CurrentAccount currentAccount = currentAccountService.findBankAccount(1L);
    assertNotNull(currentAccount);
    assertEquals(1L, currentAccount.getAccountNumber());
  }
}