package com.example.apiweek5;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.zalando.problem.Problem;

import javax.ws.rs.NotFoundException;
import java.util.NoSuchElementException;

import static org.zalando.problem.Status.BAD_REQUEST;
import static org.zalando.problem.Status.CONFLICT;
import static org.zalando.problem.Status.INTERNAL_SERVER_ERROR;
import static org.zalando.problem.Status.NOT_FOUND;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

//  @ExceptionHandler(value = IllegalArgumentException.class)
//  protected ResponseEntity<Object> handleIllegalArgument() {
//    Problem problem =
//        Problem.builder()
//            .withTitle("IllegalA Argument Exception")
//            .withDetail("Unknown Arguments")
//            .withStatus(BAD_REQUEST)
//            .build();
//    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//        .contentType(MediaType.valueOf("application/problem+json"))
//        .body(problem);
//  }
//
//  @ExceptionHandler(value = {NoSuchElementException.class, NotFoundException.class})
//  protected ResponseEntity<Object> handleNotFound() {
//    Problem problem =
//        Problem.builder()
//            .withTitle("Not found")
//            .withDetail("Not found")
//            .withStatus(NOT_FOUND)
//            .build();
//    return ResponseEntity.status(HttpStatus.NOT_FOUND)
//        .contentType(MediaType.valueOf("application/problem+json"))
//        .body(problem);
//  }
//
//  @ExceptionHandler(value = IllegalStateException.class)
//  protected ResponseEntity<Object> handleIllegalState() {
//    Problem problem =
//        Problem.builder()
//            .withTitle("IllegalA Argument Exception")
//            .withDetail("Unknown Arguments")
//            .withStatus(CONFLICT)
//            .build();
//    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//        .contentType(MediaType.valueOf("application/problem+json"))
//        .body(problem);
//  }
//
//  @ExceptionHandler(RuntimeException.class)
//  protected ResponseEntity<Object> handleAnyElse() {
//    Problem problem =
//        Problem.builder()
//            .withTitle("Server Error")
//            .withDetail("Error")
//            .withStatus(INTERNAL_SERVER_ERROR)
//            .build();
//    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//        .contentType(MediaType.valueOf("application/problem+json"))
//        .body(problem);
//  }
}
