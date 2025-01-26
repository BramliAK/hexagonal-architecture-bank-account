package com.exaltit.bankaccount.domain.model;

import java.math.BigDecimal;

public record Discovered(Long id, BigDecimal authorizedDiscoveryAmount) {
}
