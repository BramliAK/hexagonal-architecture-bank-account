package com.exaltit.bankaccount.infrastructure.adapter.output.repository;

import com.exaltit.bankaccount.domain.model.SavingsAccount;
import com.exaltit.bankaccount.domain.port.output.AccountPort;
import com.exaltit.bankaccount.infrastructure.adapter.exceptions.KataTechnicalException;
import com.exaltit.bankaccount.infrastructure.adapter.output.mapper.SavingsAccountMapper;
import org.springframework.stereotype.Component;

@Component("savingsAccountRepositoryAdapter")
public class SavingsAccountAdapter implements AccountPort<SavingsAccount> {
  private SavingsAccountRepository savingsAccountRepository;
  private SavingsAccountMapper savingsAccountMapper;

  public SavingsAccountAdapter(SavingsAccountRepository savingsAccountRepository,
                               SavingsAccountMapper savingsAccountMapper) {
    this.savingsAccountRepository = savingsAccountRepository;
    this.savingsAccountMapper = savingsAccountMapper;
  }

  @Override
  public SavingsAccount findByAccountNumber(Long accountNumber) {
    return savingsAccountMapper.toDomain(
        savingsAccountRepository.findByAccountNumber(accountNumber)
            .orElseThrow(() -> new KataTechnicalException("404", "Savings Account not found")));
  }

  @Override
  public void save(SavingsAccount savingsAccount) {
    savingsAccountRepository.save(savingsAccountMapper.toEntity(savingsAccount));
  }
}