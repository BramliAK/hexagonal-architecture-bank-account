package com.exaltit.bankaccount.domain.port.output;

import com.exaltit.bankaccount.domain.model.Transaction;
import java.time.LocalDateTime;
import java.util.List;

public interface AccountTransactionPort {
  Transaction save(Transaction transaction);

  List<Transaction> findTransactionsByAccountNumberAndEmissionDate(Long accountNumber,
                                                                   LocalDateTime emissionDate);
}
