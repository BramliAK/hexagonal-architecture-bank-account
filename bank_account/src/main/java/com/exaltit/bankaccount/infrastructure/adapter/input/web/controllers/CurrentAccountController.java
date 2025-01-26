package com.exaltit.bankaccount.infrastructure.adapter.input.web.controllers;

import static com.exaltit.bankaccount.domain.enums.TransactionType.*;

import com.exaltit.bankaccount.domain.model.AccountStatement;
import com.exaltit.bankaccount.domain.model.BankAccount;
import com.exaltit.bankaccount.domain.model.CurrentAccount;
import com.exaltit.bankaccount.domain.model.Transaction;
import com.exaltit.bankaccount.domain.port.input.AccountService;
import com.exaltit.bankaccount.domain.port.input.TransactionUseCase;
import com.exaltit.bankaccount.infrastructure.adapter.input.web.AccountRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/accounts/current")
public class CurrentAccountController {

  private AccountService<CurrentAccount> currentAccountService;
  private TransactionUseCase transactionUseCase;

  public CurrentAccountController(AccountService<CurrentAccount> currentAccountService,
                                  TransactionUseCase transactionUseCase) {
    this.currentAccountService = currentAccountService;
    this.transactionUseCase = transactionUseCase;
  }

  @PostMapping("/deposit")
  public ResponseEntity<CurrentAccount> deposit(@Valid @RequestBody AccountRequest accountRequest) {
    CurrentAccount currentAccount =
        currentAccountService.deposit(accountRequest.accountNumber(), accountRequest.amount());
    transactionUseCase.save(
        new Transaction(currentAccount, accountRequest.amount(), DEPOSIT));
    return new ResponseEntity<>(currentAccount, HttpStatus.OK);
  }

  @PostMapping("/withdraw")
  public ResponseEntity<CurrentAccount> withdraw(
      @Valid @RequestBody AccountRequest accountRequest) {
    CurrentAccount currentAccount =
        currentAccountService.withdraw(accountRequest.accountNumber(), accountRequest.amount());
    transactionUseCase.save(
        new Transaction(currentAccount, accountRequest.amount(), WITHDRAWAL));
    return new ResponseEntity<>(currentAccount, HttpStatus.OK);
  }

  @GetMapping("/transactions/{accountNumber}/{emissionDate}")
  public ResponseEntity<AccountStatement> accountStatement(
      @NotNull @Valid @PathVariable Long accountNumber,
      @NotNull @PathVariable @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate emissionDate) {
    BankAccount bankAccount = currentAccountService.findBankAccount(accountNumber);
    LocalDateTime emissionDateTime = emissionDate.atTime(LocalTime.now());
    return new ResponseEntity<>(
        transactionUseCase.findAccountStatement(bankAccount, emissionDateTime), HttpStatus.OK);
  }
}