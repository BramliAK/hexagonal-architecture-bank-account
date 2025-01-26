package com.exaltit.bankaccount.infrastructure.adapter.output.entities;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "accountType",
    discriminatorType = DiscriminatorType.STRING)
@Table(name = "bank_account")
public class BankAccountEntity {

  @Id
  private Long accountNumber;
  private BigDecimal balance;
  @Column(insertable = false, updatable = false)
  private String accountType;

}