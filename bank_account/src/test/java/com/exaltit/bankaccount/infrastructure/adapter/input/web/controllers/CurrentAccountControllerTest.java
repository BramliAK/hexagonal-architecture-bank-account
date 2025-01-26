package com.exaltit.bankaccount.infrastructure.adapter.input.web.controllers;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.exaltit.bankaccount.domain.DomainService;
import com.exaltit.bankaccount.domain.Stub;
import com.exaltit.bankaccount.infrastructure.adapter.input.web.AccountRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(CurrentAccountController.class)
@ComponentScan(
    basePackages = "com.exaltit.bankaccount.domain",
    includeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = {
        DomainService.class, Stub.class}))
class CurrentAccountControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  protected ObjectMapper mapper;

  private final AccountRequest accountRequest = new AccountRequest(1L, BigDecimal.valueOf(1000));
  private static final Long ACCOUNT_NUMBER = 1L;
  private static final String ACCOUNT_TYPE = "Current";
  private static final BigDecimal DISCOVERY_AMOUNT = BigDecimal.valueOf(100.0);

  private static final int TRANACTION_SIZE = 1;

  @Test
  void deposit_should_return_200() throws Exception {
    mockMvc.perform(
            post("/accounts/current/deposit")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsBytes(accountRequest)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.accountNumber").value((ACCOUNT_NUMBER)))
        .andExpect(jsonPath("$.balance").value(BigDecimal.valueOf(2000.0)))
        .andExpect(jsonPath("$.accountType").value(ACCOUNT_TYPE))
        .andExpect(jsonPath("$.overdraftLimit.authorizedDiscoveryAmount").value(DISCOVERY_AMOUNT));
  }

  @Test
  void deposit_should_return_500() throws Exception {
    mockMvc.perform(
            post("/accounts/current/deposit")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsBytes(new AccountRequest(50L, BigDecimal.valueOf(100)))))
        .andExpect(status().is5xxServerError())
        .andExpect(jsonPath("$.code").value(("404")))
        .andExpect(jsonPath("$.message").value("Savings Account not found"));
  }

  @Test
  void should_assemble_a_rescue_fleet2() throws Exception {
    mockMvc.perform(
            post("/accounts/current/withdraw")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsBytes(accountRequest)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.accountNumber").value((ACCOUNT_NUMBER)))
        .andExpect(jsonPath("$.balance").value(BigDecimal.valueOf(0.0)))
        .andExpect(jsonPath("$.accountType").value(ACCOUNT_TYPE))
        .andExpect(jsonPath("$.overdraftLimit.authorizedDiscoveryAmount").value(DISCOVERY_AMOUNT));
  }

  @Test
  void withdraw_should_return_400_WithInsufficient_Balance() throws Exception {
    mockMvc.perform(
            post("/accounts/current/withdraw")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsBytes(new AccountRequest(1L, BigDecimal.valueOf(5000)))))
        .andExpect(status().is4xxClientError())
        .andExpect(jsonPath("$.code").value(("500")))
        .andExpect(jsonPath("$.message").value("Insufficient balance"));
  }

  @Test
  void transactions_should_return_200() throws Exception {
    mockMvc.perform(
            get("/accounts/current/transactions/1/2025-02-23")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsBytes(accountRequest)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.accountNumber").value((ACCOUNT_NUMBER)))
        .andExpect(jsonPath("$.accountType").value((ACCOUNT_TYPE)))
        .andExpect(jsonPath("$.balance").value(BigDecimal.valueOf(900.0)))
        .andExpect(jsonPath("$.transactions").value(hasSize(TRANACTION_SIZE)));
  }
}