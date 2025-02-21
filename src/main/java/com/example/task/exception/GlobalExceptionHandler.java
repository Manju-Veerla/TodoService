package com.example.task.exception;

import com.example.task.model.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
    ErrorResponse customError = ErrorResponse.builder()
      .httpStatus(HttpStatus.BAD_REQUEST)
      .message(ex.getMessage())
      .build();

    return new ResponseEntity<>(customError, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(TodoNotFoundException.class)
  public ResponseEntity<Object> handleTodoNotFoundException(TodoNotFoundException ex) {

    ErrorResponse customError = ErrorResponse.builder()
      .httpStatus(HttpStatus.NOT_FOUND)
      .message(ex.getMessage())
      .build();

    return new ResponseEntity<>(customError, HttpStatus.NOT_FOUND);
  }

}
