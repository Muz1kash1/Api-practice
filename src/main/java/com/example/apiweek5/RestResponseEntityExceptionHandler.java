package com.example.apiweek5;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.ws.rs.NotFoundException;
import java.util.NoSuchElementException;


@ControllerAdvice

public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
  @ExceptionHandler(value = IllegalArgumentException.class)
  protected ResponseEntity<Object> handleIllegalArgument(
      RuntimeException exception, WebRequest request) {
    return handleExceptionInternal(
        exception, exception.getCause(), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
  }

  @ExceptionHandler(value = {NoSuchElementException.class, NotFoundException.class})
  protected ResponseEntity<Object> handleNotFound(RuntimeException exception, WebRequest request) {
    return handleExceptionInternal(
        exception, exception.getCause(), new HttpHeaders(), HttpStatus.NOT_FOUND, request);
  }

  @ExceptionHandler(value = IllegalStateException.class)
  protected ResponseEntity<Object> handleIllegalState(
      RuntimeException exception, WebRequest request) {
    return handleExceptionInternal(
        exception, exception.getCause(), new HttpHeaders(), HttpStatus.CONFLICT, request);
  }

  @ExceptionHandler
  protected ResponseEntity<Object> handleAnyElse(RuntimeException exception, WebRequest request) {

    return handleExceptionInternal(
        exception, exception.getCause(), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
  }
}
