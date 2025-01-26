package com.exaltit.bankaccount.domain.enums;

import java.math.BigDecimal;

public interface TransactionTypeFeatures {
  BigDecimal rollbackAmount(BigDecimal balance, BigDecimal amount);
}
