package com.exaltit.bankaccount.domain.port.output.stubs;

import com.exaltit.bankaccount.domain.Stub;
import com.exaltit.bankaccount.domain.enums.TransactionType;
import com.exaltit.bankaccount.domain.model.BankAccount;
import com.exaltit.bankaccount.domain.model.Transaction;
import com.exaltit.bankaccount.domain.port.output.AccountTransactionPort;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;

@Stub
public class AccountTransactionPortStub implements AccountTransactionPort {
  @Override
  public Transaction save(Transaction transaction) {
    return transaction;
  }

  @Override
  public List<Transaction> findTransactionsByAccountNumberAndEmissionDate(Long accountNumber,
                                                                          LocalDateTime emissionDate) {
    BankAccount bankAccount = new BankAccount(1L, BigDecimal.valueOf(1000.0), "current");
    return Stream.of(
            new Transaction(bankAccount, BigDecimal.valueOf(100.0), TransactionType.DEPOSIT,
                LocalDateTime.of(2025, 12, 1, 0, 0)),
            new Transaction(bankAccount, BigDecimal.valueOf(100.0), TransactionType.DEPOSIT,
                LocalDateTime.of(2025, 1, 24, 0, 0)),
            new Transaction(bankAccount, BigDecimal.valueOf(150.0), TransactionType.WITHDRAWAL,
                LocalDateTime.of(2024, 12, 25, 0, 0))

        ).filter(op -> op.transactionDate().isAfter(emissionDate))
        .toList();

  }
}
