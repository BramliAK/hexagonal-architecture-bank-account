package com.exaltit.bankaccount.infrastructure.adapter.output.repository;

import static com.exaltit.bankaccount.domain.enums.AccountType.CURRENT;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.exaltit.bankaccount.domain.model.DepositLimit;
import com.exaltit.bankaccount.domain.model.SavingsAccount;
import com.exaltit.bankaccount.infrastructure.adapter.exceptions.KataTechnicalException;
import com.exaltit.bankaccount.infrastructure.adapter.output.entities.DepositLimitEntity;
import com.exaltit.bankaccount.infrastructure.adapter.output.entities.SavingsAccountEntity;
import com.exaltit.bankaccount.infrastructure.adapter.output.mapper.DepositLimitMapper;
import com.exaltit.bankaccount.infrastructure.adapter.output.mapper.SavingsAccountMapper;
import java.math.BigDecimal;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class SavingsAccountAdapterTest {
  @Mock
  private SavingsAccountRepository savingsAccountRepository;
  private final DepositLimitMapper depositLimitMapper = new DepositLimitMapper();
  private final SavingsAccountMapper savingsAccountMapper =
      new SavingsAccountMapper(depositLimitMapper);
  @InjectMocks
  private SavingsAccountAdapter savingsAccountAdapter =
      new SavingsAccountAdapter(savingsAccountRepository, savingsAccountMapper);
  @Captor
  private ArgumentCaptor<SavingsAccountEntity> accountArgumentCaptor;

  private DepositLimitEntity initDepositLimitEntity() {
    return new DepositLimitEntity(1L, BigDecimal.valueOf(1000));
  }

  @Test
  void should_findByAccountNumber_returnMappedSavingsAccount() {
    // Given
    SavingsAccountEntity savingsAccountEntity = new SavingsAccountEntity();
    savingsAccountEntity.setAccountNumber(1L);
    savingsAccountEntity.setBalance(BigDecimal.valueOf(1000));
    savingsAccountEntity.setAccountType("Savings");
    savingsAccountEntity.setDepositLimit(initDepositLimitEntity());
    given(savingsAccountRepository.findByAccountNumber(1L))
        .willReturn(Optional.of(savingsAccountEntity));
    // When
    SavingsAccount foundSavingsAccount = savingsAccountAdapter.findByAccountNumber(1L);
    // Then
    assertEquals(1L, foundSavingsAccount.getAccountNumber());
    assertEquals(BigDecimal.valueOf(1000), foundSavingsAccount.getBalance());
    assertEquals("Savings", foundSavingsAccount.getAccountType());
    assertEquals(1L, foundSavingsAccount.getDepositLimit().id());
    assertEquals(BigDecimal.valueOf(1000),
        foundSavingsAccount.getDepositLimit().amount());
  }

  @Test
  void should_findByAccountNumber_ThrowException_WhenAccountNotFound() {
    given(savingsAccountRepository.findByAccountNumber(1L))
        .willThrow(new KataTechnicalException("404", "Savings Account not found"));
    Throwable exception = assertThrows(KataTechnicalException.class,
        () -> savingsAccountAdapter.findByAccountNumber(1L));
    assertEquals("Savings Account not found", exception.getMessage());
  }

  @Test
  void should_save_MappedSavingsAccount() {
    SavingsAccount savingsAccount =
        new SavingsAccount(1L, BigDecimal.valueOf(1000.0), CURRENT.getValue(),
            new DepositLimit(1L, BigDecimal.valueOf(100.0)));
    savingsAccountAdapter.save(savingsAccount);
    verify(savingsAccountRepository, times(1)).save(accountArgumentCaptor.capture());
    assertEquals(1L, accountArgumentCaptor.getValue().getAccountNumber());
    assertEquals(BigDecimal.valueOf(1000.0), accountArgumentCaptor.getValue().getBalance());
    assertEquals("savings", accountArgumentCaptor.getValue().getAccountType());
    assertEquals(1L, accountArgumentCaptor.getValue().getDepositLimit().getId());
    assertEquals(BigDecimal.valueOf(100.0),
        accountArgumentCaptor.getValue().getDepositLimit().getAmount());
  }
}