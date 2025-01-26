package com.exaltit.bankaccount.domain.service;

import static java.util.Objects.isNull;

import com.exaltit.bankaccount.domain.DomainService;
import com.exaltit.bankaccount.domain.exceptions.KataFunctionalException;
import com.exaltit.bankaccount.domain.model.CurrentAccount;
import com.exaltit.bankaccount.domain.port.input.AccountService;
import com.exaltit.bankaccount.domain.port.output.AccountPort;
import java.math.BigDecimal;

@DomainService
public class CurrentAccountService implements AccountService<CurrentAccount> {
  private final AccountPort<CurrentAccount> accountPort;

  public CurrentAccountService(AccountPort<CurrentAccount> accountPort) {
    this.accountPort = accountPort;
  }

  @Override
  public CurrentAccount deposit(Long accountNumber, BigDecimal amount) {
    verifyInputParams(accountNumber, amount);
    CurrentAccount currentAccount = accountPort.findByAccountNumber(accountNumber);
    currentAccount.setBalance(currentAccount.getBalance().add(amount));
    accountPort.save(currentAccount);
    return currentAccount;
  }

  @Override
  public CurrentAccount withdraw(Long accountNumber, BigDecimal amount) {
    verifyInputParams(accountNumber, amount);
    CurrentAccount currentAccount = accountPort.findByAccountNumber(accountNumber);
    BigDecimal eligibleAmount = currentAccount.getBalance()
        .add(currentAccount.getOverdraftLimit().authorizedDiscoveryAmount());
    if (eligibleAmount.compareTo(amount) < 0) {
      throw new KataFunctionalException("Insufficient balance");
    }
    currentAccount.setBalance(currentAccount.getBalance().subtract(amount));
    accountPort.save(currentAccount);
    return currentAccount;
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
  public CurrentAccount findBankAccount(Long accountNumber) {
    return accountPort.findByAccountNumber(accountNumber);
  }

}