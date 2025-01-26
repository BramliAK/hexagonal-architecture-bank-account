package com.exaltit.bankaccount.infrastructure.adapter.output.repository;

import com.exaltit.bankaccount.domain.model.Transaction;
import com.exaltit.bankaccount.domain.port.output.AccountTransactionPort;
import com.exaltit.bankaccount.infrastructure.adapter.output.entities.TransactionEntity;
import com.exaltit.bankaccount.infrastructure.adapter.output.mapper.TransactionMapper;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class TransactionAdapter implements AccountTransactionPort {

  private final TransactionRepository transactionRepository;
  private final TransactionMapper transactionMapper;

  public TransactionAdapter(TransactionRepository transactionRepository,
                            TransactionMapper transactionMapper) {
    this.transactionRepository = transactionRepository;
    this.transactionMapper = transactionMapper;
  }

  @Override
  public Transaction save(Transaction transaction) {
    TransactionEntity transactionEntity =
        transactionRepository.save(transactionMapper.toEntity(transaction));
    return transactionMapper.toDomain(transactionEntity);
  }

  @Override
  public List<Transaction> findTransactionsByAccountNumberAndEmissionDate(
      Long accountNumber, LocalDateTime emissionDate) {
    List<TransactionEntity> transactionEntities =
        transactionRepository
            .findTransactionEntitiesByBankAccount_AccountNumberAndTransactionDateIsAfter(
                accountNumber, emissionDate);
    return transactionEntities.stream().map(transactionMapper::toDomain).toList();
  }
}
