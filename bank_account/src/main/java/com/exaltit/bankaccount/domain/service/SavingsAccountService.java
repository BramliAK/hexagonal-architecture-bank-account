package com.exaltit.bankaccount.domain.service;

import static java.util.Objects.isNull;

import com.exaltit.bankaccount.domain.DomainService;
import com.exaltit.bankaccount.domain.exceptions.KataFunctionalException;
import com.exaltit.bankaccount.domain.model.SavingsAccount;
import com.exaltit.bankaccount.domain.port.input.AccountService;
import com.exaltit.bankaccount.domain.port.output.AccountPort;
import java.math.BigDecimal;

@DomainService
public class SavingsAccountService implements AccountService<SavingsAccount> {
  private final AccountPort<SavingsAccount> accountPort;

  public SavingsAccountService(AccountPort<SavingsAccount> accountPort) {
    this.accountPort = accountPort;
  }

  @Override
  public SavingsAccount deposit(Long accountNumber, BigDecimal amount) {
    verifyInputParams(accountNumber, amount);
    SavingsAccount savingsAccount = accountPort.findByAccountNumber(accountNumber);
    if (savingsAccount.getDepositLimit().amount().compareTo(amount) < 0) {
      throw new KataFunctionalException("The amount exceeds the deposit limit");
    }
    savingsAccount.setBalance(savingsAccount.getBalance().add(amount));
    accountPort.save(savingsAccount);
    return savingsAccount;
  }

  private void verifyInputParams(Long accountNumber, BigDecimal amount) {
    if (isNull(amount)) {
      throw new KataFunctionalException("The amount must be informed");
    }
    if (amount.compareTo(BigDecimal.ZERO) <= 0) {
      throw new KataFunctionalException("The amount must be greater than zero");
    }
    if (isNull(accountNumber)) {
      throw new KataFunctionalException("The account number must be informed");
    }
  }

  @Override
  public SavingsAccount withdraw(Long accountNumber, BigDecimal amount) {
    verifyInputParams(accountNumber, amount);
    SavingsAccount savingsAccount = accountPort.findByAccountNumber(accountNumber);
    if (savingsAccount.getBalance().compareTo(amount) < 0) {
      throw new KataFunctionalException("Insufficient balance");
    }
    savingsAccount.setBalance(savingsAccount.getBalance().subtract(amount));
    accountPort.save(savingsAccount);
    return savingsAccount;
  }

  @Override
  public SavingsAccount findBankAccount(Long accountNumber) {
    return accountPort.findByAccountNumber(accountNumber);
  }
}