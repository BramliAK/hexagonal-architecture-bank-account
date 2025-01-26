package com.exaltit.bankaccount.infrastructure.adapter.output.entities;

import static jakarta.persistence.GenerationType.SEQUENCE;

import com.exaltit.bankaccount.domain.enums.TransactionType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "transaction")
public class TransactionEntity {
  @Id
  @GeneratedValue(strategy = SEQUENCE, generator = "SEQ")
  private Long id;
  @ManyToOne
  @JoinColumn(name = "bank_account_id", nullable = false)
  private BankAccountEntity bankAccount;
  private BigDecimal amount;
  private TransactionType transactionType;
  @Temporal(TemporalType.TIMESTAMP)
  @Column(nullable = false, updatable = false)
  @CreatedDate
  protected LocalDateTime transactionDate;
}