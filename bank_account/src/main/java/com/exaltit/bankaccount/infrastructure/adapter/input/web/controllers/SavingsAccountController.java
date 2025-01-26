package com.exaltit.bankaccount.infrastructure.adapter.input.web.controllers;

import com.exaltit.bankaccount.domain.enums.TransactionType;
import com.exaltit.bankaccount.domain.model.AccountStatement;
import com.exaltit.bankaccount.domain.model.BankAccount;
import com.exaltit.bankaccount.domain.model.SavingsAccount;
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
@RequestMapping("/accounts/savings")
public class SavingsAccountController {
  private AccountService<SavingsAccount> savingsAccountService;
  private TransactionUseCase transactionUseCase;

  public SavingsAccountController(AccountService<SavingsAccount> savingsAccountService,
                                  TransactionUseCase transactionUseCase) {
    this.savingsAccountService = savingsAccountService;
    this.transactionUseCase = transactionUseCase;
  }

  @PostMapping("/deposit")
  public ResponseEntity<SavingsAccount> deposit(@Valid @RequestBody AccountRequest accountRequest) {
    SavingsAccount savingsAccount =
        savingsAccountService.deposit(accountRequest.accountNumber(), accountRequest.amount());
    transactionUseCase.save(
        new Transaction(savingsAccount, accountRequest.amount(), TransactionType.WITHDRAWAL));
    return new ResponseEntity<>(savingsAccount, HttpStatus.OK);
  }

  @PostMapping("/withdraw")
  public ResponseEntity<SavingsAccount> withdraw(
      @Valid @RequestBody AccountRequest accountRequest) {
    SavingsAccount savingsAccount =
        savingsAccountService.withdraw(accountRequest.accountNumber(), accountRequest.amount());
    transactionUseCase.save(
        new Transaction(savingsAccount, accountRequest.amount(), TransactionType.WITHDRAWAL));
    return new ResponseEntity<>(savingsAccount, HttpStatus.OK);
  }

  @GetMapping("/transactions/{accountNumber}/{emissionDate}")
  public ResponseEntity<AccountStatement> accountStatement(
      @NotNull @Valid @PathVariable Long accountNumber,
      @PathVariable @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate emissionDate) {
    BankAccount bankAccount = savingsAccountService.findBankAccount(accountNumber);
    LocalDateTime emissionDateTime = emissionDate.atTime(LocalTime.now());
    return new ResponseEntity<>(
        transactionUseCase.findAccountStatement(bankAccount, emissionDateTime),
        HttpStatus.OK);
  }
}