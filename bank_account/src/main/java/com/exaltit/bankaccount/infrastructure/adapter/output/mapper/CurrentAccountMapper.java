package com.exaltit.bankaccount.infrastructure.adapter.output.mapper;

import static com.exaltit.bankaccount.domain.enums.AccountType.CURRENT;

import com.exaltit.bankaccount.domain.model.CurrentAccount;
import com.exaltit.bankaccount.infrastructure.adapter.output.entities.CurrentAccountEntity;
import org.springframework.stereotype.Component;

@Component("currentAccountAdapter")
public class CurrentAccountMapper {
  private DiscoveredMapper discoveredMapper;

  public CurrentAccountMapper(DiscoveredMapper discoveredMapper) {
    this.discoveredMapper = discoveredMapper;
  }

  public CurrentAccountEntity toEntity(CurrentAccount currentAccount) {
    return new CurrentAccountEntity(currentAccount.getAccountNumber(), currentAccount.getBalance(),
        CURRENT.getValue(),
        discoveredMapper.toEntity(currentAccount.getOverdraftLimit()));
  }

  public CurrentAccount toDomain(CurrentAccountEntity currentAccountEntity) {
    return new CurrentAccount(currentAccountEntity.getAccountNumber(),
        currentAccountEntity.getBalance(),
        CURRENT.getValue(),
        discoveredMapper.toDomain(currentAccountEntity.getOverdraftLimit()));
  }
}