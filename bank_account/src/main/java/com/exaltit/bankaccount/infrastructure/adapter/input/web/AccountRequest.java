package com.exaltit.bankaccount.infrastructure.adapter.input.web;

import java.math.BigDecimal;

public record AccountRequest(Long accountNumber, BigDecimal amount) {
}
