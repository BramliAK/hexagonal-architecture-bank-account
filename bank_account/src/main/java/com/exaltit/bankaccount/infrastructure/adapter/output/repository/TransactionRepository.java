package com.exaltit.bankaccount.infrastructure.adapter.output.repository;

import com.exaltit.bankaccount.infrastructure.adapter.output.entities.TransactionEntity;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionEntity, Long> {

  List<TransactionEntity> findTransactionEntitiesByBankAccount_AccountNumberAndTransactionDateIsAfter(
      Long bankAccountAccountNumber, LocalDateTime transactionDate);
}
