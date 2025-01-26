package com.exaltit.bankaccount.infrastructure.adapter.output.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import java.math.BigDecimal;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@DiscriminatorValue("savings")
public class SavingsAccountEntity extends BankAccountEntity {
  @ManyToOne
  private DepositLimitEntity depositLimit;

  public SavingsAccountEntity(Long accountNumber, BigDecimal balance, String accountType,
                              DepositLimitEntity depositLimit) {
    super(accountNumber, balance, accountType);
    this.depositLimit = depositLimit;
  }
}