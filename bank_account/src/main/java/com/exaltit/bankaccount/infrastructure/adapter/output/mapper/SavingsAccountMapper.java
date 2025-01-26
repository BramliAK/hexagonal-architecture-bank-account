package com.exaltit.bankaccount.infrastructure.adapter.output.mapper;

import static com.exaltit.bankaccount.domain.enums.AccountType.SAVINGS;

import com.exaltit.bankaccount.domain.model.SavingsAccount;
import com.exaltit.bankaccount.infrastructure.adapter.output.entities.SavingsAccountEntity;
import org.springframework.stereotype.Component;

@Component
public class SavingsAccountMapper {
  private DepositLimitMapper depositLimitMapper;

  public SavingsAccountMapper(DepositLimitMapper depositLimitMapper) {
    this.depositLimitMapper = depositLimitMapper;
  }

  public SavingsAccountEntity toEntity(SavingsAccount savingsAccount) {
    return new SavingsAccountEntity(savingsAccount.getAccountNumber(), savingsAccount.getBalance(),
        "savings",
        depositLimitMapper.toEntity(savingsAccount.getDepositLimit()));
  }

  public SavingsAccount toDomain(SavingsAccountEntity savingsAccountEntity) {
    return new SavingsAccount(savingsAccountEntity.getAccountNumber(),
        savingsAccountEntity.getBalance(), SAVINGS.getValue(),
        depositLimitMapper.toDomain(savingsAccountEntity.getDepositLimit()));
  }
}