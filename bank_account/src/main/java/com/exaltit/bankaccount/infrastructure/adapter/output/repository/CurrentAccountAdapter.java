package com.exaltit.bankaccount.infrastructure.adapter.output.repository;

import com.exaltit.bankaccount.domain.model.CurrentAccount;
import com.exaltit.bankaccount.domain.port.output.AccountPort;
import com.exaltit.bankaccount.infrastructure.adapter.exceptions.KataTechnicalException;
import com.exaltit.bankaccount.infrastructure.adapter.output.mapper.CurrentAccountMapper;
import org.springframework.stereotype.Component;

@Component("currentAccountRepositoryAdapter")
public class CurrentAccountAdapter implements AccountPort<CurrentAccount> {
  private CurrentAccountRepository currentAccountRepository;
  private CurrentAccountMapper currentAccountMapper;

  public CurrentAccountAdapter(CurrentAccountRepository currentAccountRepository,
                               CurrentAccountMapper currentAccountMapper) {
    this.currentAccountRepository = currentAccountRepository;
    this.currentAccountMapper = currentAccountMapper;
  }

  @Override
  public CurrentAccount findByAccountNumber(Long accountNumber) {
    return currentAccountMapper.toDomain(
        currentAccountRepository.findByAccountNumber(accountNumber)
            .orElseThrow(() -> new KataTechnicalException("404", "Current Account not found")));
  }

  @Override
  public void save(CurrentAccount currentAccount) {
    currentAccountRepository.save(currentAccountMapper.toEntity(currentAccount));
  }
}