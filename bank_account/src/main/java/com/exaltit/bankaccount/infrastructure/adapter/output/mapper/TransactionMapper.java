package com.exaltit.bankaccount.infrastructure.adapter.output.mapper;

import com.exaltit.bankaccount.domain.model.BankAccount;
import com.exaltit.bankaccount.domain.model.Transaction;
import com.exaltit.bankaccount.infrastructure.adapter.output.entities.BankAccountEntity;
import com.exaltit.bankaccount.infrastructure.adapter.output.entities.TransactionEntity;
import org.springframework.stereotype.Component;

@Component
public class TransactionMapper {

  public TransactionEntity toEntity(Transaction transaction) {
    BankAccount bankAccount = transaction.bankAccount();
    return new TransactionEntity(transaction.id(),
        new BankAccountEntity(bankAccount.getAccountNumber(), bankAccount.getBalance(),
            bankAccount.getAccountType()),
        transaction.amount(),
        transaction.transactionType(),
        transaction.transactionDate());
  }

  public Transaction toDomain(TransactionEntity transactionEntity) {
    BankAccountEntity bankAccountEntity = transactionEntity.getBankAccount();
    return new Transaction(transactionEntity.getId(),
        new BankAccount(bankAccountEntity.getAccountNumber(), bankAccountEntity.getBalance(),
            bankAccountEntity.getAccountType()),
        transactionEntity.getAmount(),
        transactionEntity.getTransactionType(),
        transactionEntity.getTransactionDate());
  }

}
