package com.exaltit.bankaccount.domain.enums;

import java.math.BigDecimal;

public enum TransactionType implements TransactionTypeFeatures {

  DEPOSIT {
    @Override
    public BigDecimal rollbackAmount(BigDecimal balance, BigDecimal amount) {
      return balance.subtract(amount);
    }
  }, WITHDRAWAL {
    @Override
    public BigDecimal rollbackAmount(BigDecimal balance, BigDecimal amount) {
      return balance.add(amount);
    }
  };

}
