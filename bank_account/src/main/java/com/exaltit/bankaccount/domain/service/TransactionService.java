package com.exaltit.bankaccount.domain.service;

import com.exaltit.bankaccount.domain.DomainService;
import com.exaltit.bankaccount.domain.model.AccountStatement;
import com.exaltit.bankaccount.domain.model.BankAccount;
import com.exaltit.bankaccount.domain.model.Transaction;
import com.exaltit.bankaccount.domain.model.TransactionData;
import com.exaltit.bankaccount.domain.port.input.TransactionUseCase;
import com.exaltit.bankaccount.domain.port.output.AccountTransactionPort;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@DomainService
public class TransactionService implements TransactionUseCase {

  private final AccountTransactionPort transactionPort;

  public TransactionService(AccountTransactionPort transactionPort) {
    this.transactionPort = transactionPort;
  }

  @Override
  public Transaction save(Transaction transaction) {
    return transactionPort.save(transaction);
  }

  @Override
  public AccountStatement findAccountStatement(BankAccount bankAccount,
                                               LocalDateTime emissionDate) {
    LocalDateTime dateDebut = emissionDate.minusMonths(1);

    List<Transaction> transactions =
        transactionPort.findTransactionsByAccountNumberAndEmissionDate(
            bankAccount.getAccountNumber(), dateDebut);

    List<TransactionData> transactionsList =
        findEmissionDateTransactions(emissionDate, transactions);

    BigDecimal balance =
        findBalanceOnEmissionDate(bankAccount.getBalance(), emissionDate, transactions);
    return new AccountStatement(bankAccount.getAccountNumber(), bankAccount.getAccountType(),
        balance, transactionsList);
  }

  private List<TransactionData> findEmissionDateTransactions(LocalDateTime emissionDate,
                                                             List<Transaction> transactions) {
    return transactions.stream()
        .filter(op -> op.transactionDate().isBefore(emissionDate)
            || op.transactionDate().isEqual(emissionDate))
        .map(this::mapTransactionData)
        .sorted(Comparator.comparing(TransactionData::transactionDate).reversed())
        .toList();
  }

  private static BigDecimal findBalanceOnEmissionDate(BigDecimal soldeEmission,
                                                      LocalDateTime emissionDate,
                                                      List<Transaction> transactions) {
    for (Transaction operation : transactions) {
      if (operation.transactionDate().isAfter(emissionDate)) {
        soldeEmission =
            operation.transactionType().rollbackAmount(soldeEmission, operation.amount());
      }
    }
    return soldeEmission;
  }

  private TransactionData mapTransactionData(Transaction transaction) {
    return new TransactionData(transaction.amount(), transaction.transactionType(),
        transaction.transactionDate());
  }
}
