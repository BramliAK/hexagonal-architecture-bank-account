package com.exaltit.bankaccount.domain.port.output.stubs;

import static com.exaltit.bankaccount.domain.enums.AccountType.CURRENT;

import com.exaltit.bankaccount.domain.Stub;
import com.exaltit.bankaccount.domain.model.CurrentAccount;
import com.exaltit.bankaccount.domain.model.Discovered;
import com.exaltit.bankaccount.domain.port.output.AccountPort;
import com.exaltit.bankaccount.infrastructure.adapter.exceptions.KataTechnicalException;
import java.math.BigDecimal;
import java.util.stream.Stream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Stub
public class CurrentAccountPortStub implements AccountPort<CurrentAccount> {

  private static final Logger log = LoggerFactory.getLogger(CurrentAccountPortStub.class);

  @Override
  public CurrentAccount findByAccountNumber(Long accountNumber) {
    return Stream.of(new CurrentAccount(1L, BigDecimal.valueOf(1000.0), CURRENT.getValue(),
                new Discovered(1L, BigDecimal.valueOf(100.0))),
            new CurrentAccount(2L, BigDecimal.valueOf(1000.0), CURRENT.getValue(),
                new Discovered(1L, BigDecimal.valueOf(100.0))))
        .filter(currentAccount -> accountNumber.equals(currentAccount.getAccountNumber()))
        .findAny()
        .orElseThrow(() -> new KataTechnicalException("404", "Savings Account not found"));
  }

  @Override
  public void save(CurrentAccount currentAccount) {
    log.info("Current account saved");
  }
}
