package com.exaltit.bankaccount.domain.exceptions;

public class KataFunctionalException extends RuntimeException {

  private final String code;
  private final String message;

  public KataFunctionalException(String message) {
    this.code = "500";
    this.message = message;
  }

  public KataFunctionalException(String code, String message) {
    this.code = code;
    this.message = message;
  }

  public String getCode() {
    return code;
  }

  @Override
  public String getMessage() {
    return message;
  }

  @Override
  public String toString() {
    return """
        KataFunctionalException{
        code = %s
        message= %s}
        """.formatted(code, message);
  }
}
