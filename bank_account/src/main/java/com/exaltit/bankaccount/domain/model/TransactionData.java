package com.exaltit.bankaccount.domain.model;

import com.exaltit.bankaccount.domain.enums.TransactionType;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransactionData(BigDecimal amount, TransactionType transactionType,
                              LocalDateTime transactionDate) {
}
