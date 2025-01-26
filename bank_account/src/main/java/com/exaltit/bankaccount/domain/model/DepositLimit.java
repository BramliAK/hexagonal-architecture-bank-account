package com.exaltit.bankaccount.domain.model;

import java.math.BigDecimal;

public record DepositLimit(Long id, BigDecimal amount) {
}
