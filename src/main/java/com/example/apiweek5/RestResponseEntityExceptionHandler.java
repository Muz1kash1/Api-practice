package com.example.apiweek5;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.zalando.problem.Problem;

import javax.ws.rs.NotFoundException;
import java.util.NoSuchElementException;

import static org.zalando.problem.Status.*;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(value = {HttpClientErrorException.BadRequest.class, IllegalArgumentException.class})
  protected ResponseEntity<Object> handleIllegalArgument() {
    Problem problem =
        Problem.builder()
            .withTitle("Illegal Argument Exception")
            .withDetail("Unknown Arguments")
            .withStatus(BAD_REQUEST)
            .build();
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .contentType(MediaType.APPLICATION_PROBLEM_JSON)
        .body(problem);
  }

  @ExceptionHandler(value = {NoSuchElementException.class, NotFoundException.class, HttpClientErrorException.NotFound.class})
  protected ResponseEntity<Object> handleNotFound() {
    Problem problem =
        Problem.builder()
            .withTitle("Not found or not such element exception")
            .withDetail("Not found")
            .withStatus(NOT_FOUND)
            .build();
    return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .contentType(MediaType.APPLICATION_PROBLEM_JSON)
        .body(problem);
  }

  @ExceptionHandler(value = IllegalStateException.class)
  protected ResponseEntity<Object> handleIllegalState() {
    Problem problem =
        Problem.builder()
            .withTitle("Illegal State Exception")
            .withDetail("Unknown Arguments")
            .withStatus(CONFLICT)
            .build();
    return ResponseEntity.status(HttpStatus.CONFLICT)
        .contentType(MediaType.APPLICATION_PROBLEM_JSON)
        .body(problem);
  }

  @ExceptionHandler(RuntimeException.class)
  protected ResponseEntity<Object> handleAnyElse() {
    Problem problem =
        Problem.builder()
            .withTitle("Internal server error")
            .withDetail("Error")
            .withStatus(INTERNAL_SERVER_ERROR)
            .build();
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .contentType(MediaType.APPLICATION_PROBLEM_JSON)
        .body(problem);
  }
}
