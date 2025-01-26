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
@DiscriminatorValue("current")
public class CurrentAccountEntity extends BankAccountEntity {
  @ManyToOne
  private DiscoveredEntity overdraftLimit;

  public CurrentAccountEntity(Long accountNumber, BigDecimal balance, String accountType,
                              DiscoveredEntity overdraftLimit) {
    super(accountNumber, balance, accountType);
    this.overdraftLimit = overdraftLimit;
  }
}