package com.exaltit.bankaccount.domain.port.output.stubs;

import static com.exaltit.bankaccount.domain.enums.AccountType.SAVINGS;

import com.exaltit.bankaccount.domain.Stub;
import com.exaltit.bankaccount.domain.model.DepositLimit;
import com.exaltit.bankaccount.domain.model.SavingsAccount;
import com.exaltit.bankaccount.domain.port.output.AccountPort;
import com.exaltit.bankaccount.infrastructure.adapter.exceptions.KataTechnicalException;
import java.math.BigDecimal;
import java.util.stream.Stream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Stub
public class SavingsAccountPortStub implements AccountPort<SavingsAccount> {

  private static final Logger log = LoggerFactory.getLogger(SavingsAccountPortStub.class);

  @Override
  public SavingsAccount findByAccountNumber(Long accountNumber) {
    return Stream.of(new SavingsAccount(1L, BigDecimal.valueOf(1000.0), SAVINGS.getValue(),
                new DepositLimit(1L, BigDecimal.valueOf(100.0))),
            new SavingsAccount(2L, BigDecimal.valueOf(1000.0), SAVINGS.getValue(),
                new DepositLimit(1L, BigDecimal.valueOf(100.0))))
        .filter(currentAccount -> accountNumber.equals(currentAccount.getAccountNumber()))
        .findAny()
        .orElseThrow(() -> new KataTechnicalException("404", "Savings Account not found"));
  }

  @Override
  public void save(SavingsAccount savingsAccount) {
    log.info("Savings Account saved");
  }
}
