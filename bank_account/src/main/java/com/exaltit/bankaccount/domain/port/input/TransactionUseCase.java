package com.exaltit.bankaccount.domain.port.input;

import com.exaltit.bankaccount.domain.model.AccountStatement;
import com.exaltit.bankaccount.domain.model.BankAccount;
import com.exaltit.bankaccount.domain.model.Transaction;
import java.time.LocalDateTime;

public interface TransactionUseCase {

  Transaction save(Transaction transaction);

  AccountStatement findAccountStatement(BankAccount bankAccount, LocalDateTime dateEmission);

}
