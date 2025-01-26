package com.exaltit.bankaccount.infrastructure.adapter.output.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.exaltit.bankaccount.domain.enums.TransactionType;
import com.exaltit.bankaccount.domain.model.BankAccount;
import com.exaltit.bankaccount.domain.model.Transaction;
import com.exaltit.bankaccount.infrastructure.adapter.output.entities.BankAccountEntity;
import com.exaltit.bankaccount.infrastructure.adapter.output.entities.TransactionEntity;
import com.exaltit.bankaccount.infrastructure.adapter.output.mapper.TransactionMapper;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TransactionAdapterTest {

  @Mock
  private TransactionRepository transactionRepository;
  private final TransactionMapper transactionMapper = new TransactionMapper();


  @Captor
  private ArgumentCaptor<TransactionEntity> transactionArgumentCaptor;

  private TransactionEntity transactionEntity() {
    return new TransactionEntity(1L,
        new BankAccountEntity(1L, BigDecimal.valueOf(1000.0),
            "current"),
        BigDecimal.valueOf(100.0),
        TransactionType.DEPOSIT,
        LocalDateTime.of(2025, 12, 1, 0, 0));
  }

  @Test
  void should_save_returnMappedSavingsAccount() {
    BankAccount bankAccount = new BankAccount(1L, BigDecimal.valueOf(1000.0), "current");
    Transaction transaction =
        new Transaction(1L, bankAccount, BigDecimal.valueOf(100.0), TransactionType.DEPOSIT,
            LocalDateTime.of(2025, 12, 1, 0, 0));
    given(transactionRepository.save(transactionEntity()))
        .willReturn(transactionEntity());
    TransactionAdapter transactionAdapter =
        new TransactionAdapter(transactionRepository, transactionMapper);
    transactionAdapter.save(transaction);

    verify(transactionRepository, times(1)).save(transactionArgumentCaptor.capture());

    assertEquals(1L, transactionArgumentCaptor.getValue().getId());
    assertEquals(BigDecimal.valueOf(100.0), transactionArgumentCaptor.getValue().getAmount());
    assertEquals(TransactionType.DEPOSIT,
        transactionArgumentCaptor.getValue().getTransactionType());
    assertEquals(LocalDateTime.of(2025, 12, 1, 0, 0),
        transactionArgumentCaptor.getValue().getTransactionDate());
  }

  @Test
  void should_findAll_returnMappedSavingsAccount() {

    given(transactionRepository
        .findTransactionEntitiesByBankAccount_AccountNumberAndTransactionDateIsAfter(1L,
            LocalDateTime.of(2025, 1, 24, 0, 0)))
        .willReturn(java.util.List.of(transactionEntity()));
    TransactionAdapter transactionAdapter =
        new TransactionAdapter(transactionRepository, transactionMapper);

    var transactions = transactionAdapter.findTransactionsByAccountNumberAndEmissionDate(1L,
        LocalDateTime.of(2025, 1, 24, 0, 0));
    assertEquals(1, transactions.size());
    assertEquals(1L, transactions.getFirst().id());
    assertEquals(BigDecimal.valueOf(100.0), transactions.getFirst().amount());
    assertEquals(TransactionType.DEPOSIT, transactions.getFirst().transactionType());
    assertEquals(LocalDateTime.of(2025, 12, 1, 0, 0),
        transactions.getFirst().transactionDate());
  }
}