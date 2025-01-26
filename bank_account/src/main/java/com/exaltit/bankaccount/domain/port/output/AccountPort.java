package com.exaltit.bankaccount.domain.port.output;

import com.exaltit.bankaccount.domain.model.BankAccount;

public interface AccountPort<T extends BankAccount> {

  T findByAccountNumber(Long accountNumber);

  void save(T bankAccount);
}