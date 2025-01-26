package com.exaltit.bankaccount.infrastructure.adapter.output.repository;

import static com.exaltit.bankaccount.domain.enums.AccountType.CURRENT;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.exaltit.bankaccount.domain.model.CurrentAccount;
import com.exaltit.bankaccount.domain.model.Discovered;
import com.exaltit.bankaccount.infrastructure.adapter.exceptions.KataTechnicalException;
import com.exaltit.bankaccount.infrastructure.adapter.output.entities.CurrentAccountEntity;
import com.exaltit.bankaccount.infrastructure.adapter.output.entities.DiscoveredEntity;
import com.exaltit.bankaccount.infrastructure.adapter.output.mapper.CurrentAccountMapper;
import com.exaltit.bankaccount.infrastructure.adapter.output.mapper.DiscoveredMapper;
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
class CurrentAccountAdapterTest {

  @Mock
  private CurrentAccountRepository currentAccountRepository;
  private final DiscoveredMapper discoveredMapper = new DiscoveredMapper();
  private final CurrentAccountMapper currentAccountMapper =
      new CurrentAccountMapper(discoveredMapper);
  @InjectMocks
  private CurrentAccountAdapter currentAccountAdapter =
      new CurrentAccountAdapter(currentAccountRepository, currentAccountMapper);
  @Captor
  private ArgumentCaptor<CurrentAccountEntity> accountArgumentCaptor;

  private DiscoveredEntity initDiscoveredEntity() {
    return new DiscoveredEntity(1L, BigDecimal.valueOf(1000));
  }

  @Test
  void should_findByAccountNumber_returnMappedCurrentAccount() {
    // Given
    CurrentAccountEntity currentAccountEntity = new CurrentAccountEntity();
    currentAccountEntity.setAccountNumber(1L);
    currentAccountEntity.setBalance(BigDecimal.valueOf(1000));
    currentAccountEntity.setAccountType("Current");
    currentAccountEntity.setOverdraftLimit(initDiscoveredEntity());
    given(currentAccountRepository.findByAccountNumber(1L))
        .willReturn(Optional.of(currentAccountEntity));
    // When
    CurrentAccount foundCurrentAccount = currentAccountAdapter.findByAccountNumber(1L);
    // Then
    assertEquals(1L, foundCurrentAccount.getAccountNumber());
    assertEquals(BigDecimal.valueOf(1000), foundCurrentAccount.getBalance());
    assertEquals("Current", foundCurrentAccount.getAccountType());
    assertEquals(1L, foundCurrentAccount.getOverdraftLimit().id());
    assertEquals(BigDecimal.valueOf(1000),
        foundCurrentAccount.getOverdraftLimit().authorizedDiscoveryAmount());
  }

  @Test
  void should_findByAccountNumber_ThrowException_WhenAccountNotFound() {
    given(currentAccountRepository.findByAccountNumber(1L))
        .willThrow(new KataTechnicalException("404", "Current Account not found"));
    Throwable exception = assertThrows(KataTechnicalException.class,
        () -> currentAccountAdapter.findByAccountNumber(1L));
    assertEquals("Current Account not found", exception.getMessage());
  }

  @Test
  void should_save_MappedCurrentAccount() {
    CurrentAccount currentAccount =
        new CurrentAccount(1L, BigDecimal.valueOf(1000.0), CURRENT.getValue(),
            new Discovered(1L, BigDecimal.valueOf(100.0)));
    currentAccountAdapter.save(currentAccount);
    verify(currentAccountRepository, times(1)).save(accountArgumentCaptor.capture());
    assertEquals(1L, accountArgumentCaptor.getValue().getAccountNumber());
    assertEquals(BigDecimal.valueOf(1000.0), accountArgumentCaptor.getValue().getBalance());
    assertEquals("Current", accountArgumentCaptor.getValue().getAccountType());
    assertEquals(1L, accountArgumentCaptor.getValue().getOverdraftLimit().getId());
    assertEquals(BigDecimal.valueOf(100.0),
        accountArgumentCaptor.getValue().getOverdraftLimit().getAuthorizedDiscoveryAmount());
  }

}