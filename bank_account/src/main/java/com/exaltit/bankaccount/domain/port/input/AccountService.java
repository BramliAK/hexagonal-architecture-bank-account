package com.exaltit.bankaccount.domain.port.input;

import com.exaltit.bankaccount.domain.model.BankAccount;
import java.math.BigDecimal;

public interface AccountService<T extends BankAccount> {

  T deposit(Long accountNumber, BigDecimal amount);

  T withdraw(Long accountNumber, BigDecimal amount);

  T findBankAccount(Long accountNumber);
}
