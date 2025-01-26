package com.exaltit.bankaccount.infrastructure.adapter.exceptions;

public class KataTechnicalException extends RuntimeException {

  private final String code;
  private final String message;

  public KataTechnicalException(String code, String message) {
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
        KataTechnicalException{
        code = %s
        message= %s}
        """.formatted(code, message);
  }
}
