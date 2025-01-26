package com.exaltit.bankaccount.domain.model;

import java.math.BigDecimal;
import java.util.List;

public record AccountStatement(Long accountNumber, String accountType, BigDecimal balance,
                               List<TransactionData> transactions) {

}
