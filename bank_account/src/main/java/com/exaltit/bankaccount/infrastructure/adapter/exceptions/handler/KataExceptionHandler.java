package com.exaltit.bankaccount.infrastructure.adapter.exceptions.handler;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

import com.exaltit.bankaccount.domain.exceptions.KataFunctionalException;
import com.exaltit.bankaccount.infrastructure.adapter.exceptions.KataError;
import com.exaltit.bankaccount.infrastructure.adapter.exceptions.KataTechnicalException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class KataExceptionHandler extends ResponseEntityExceptionHandler {

  private static final Logger log = LoggerFactory.getLogger(KataExceptionHandler.class);

  @ExceptionHandler(KataTechnicalException.class)
  public final ResponseEntity<Object> handleKataTechnicalException(
      KataTechnicalException exception, WebRequest request) {
    log.error("KataTechnicalException handler: {0}", exception);

    var error = new KataError(exception.getCode(), exception.getMessage());

    return handleExceptionInternal(exception, error, new HttpHeaders(),
        INTERNAL_SERVER_ERROR, request);
  }

  @ExceptionHandler(KataFunctionalException.class)
  public final ResponseEntity<Object> handleKataFunctionalException(
      KataFunctionalException exception, WebRequest request) {
    log.error("KataFunctionalException handler: {0}", exception);

    var error = new KataError(exception.getCode(), exception.getMessage());

    return handleExceptionInternal(exception, error, new HttpHeaders(),
        BAD_REQUEST, request);
  }

  @ExceptionHandler(RuntimeException.class)
  public final ResponseEntity<Object> handleRuntimeException(
      RuntimeException exception, WebRequest request) {
    log.error("RuntimeException handler: {0}", exception);

    var error = new KataError("500", exception.getLocalizedMessage());

    return handleExceptionInternal(exception, error, new HttpHeaders(),
        INTERNAL_SERVER_ERROR, request);
  }

  @ExceptionHandler(Exception.class)
  public final ResponseEntity<Object> handleGenericException(
      Exception exception, WebRequest request) {
    log.error("GenericException handler: {0}", exception);

    var error = new KataError("500", exception.getLocalizedMessage());

    return handleExceptionInternal(exception, error, new HttpHeaders(),
        INTERNAL_SERVER_ERROR, request);
  }

}
