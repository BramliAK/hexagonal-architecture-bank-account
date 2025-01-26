package com.exaltit.bankaccount.infrastructure.adapter.output.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "discovered")
public class DiscoveredEntity {
  @Id
  private Long id;
  private BigDecimal authorizedDiscoveryAmount;
}