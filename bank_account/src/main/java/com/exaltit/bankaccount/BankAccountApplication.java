package com.exaltit.bankaccount;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(
    info = @Info(
        title = "Bank Account Hexagonal Architecture",
        version = "1.0",
        description = "Bank Account API for managing resources"
    )
)
@SpringBootApplication
public class BankAccountApplication {

  public static void main(String[] args) {
    SpringApplication.run(BankAccountApplication.class, args);
  }

}
