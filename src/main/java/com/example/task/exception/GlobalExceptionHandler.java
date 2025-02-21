package com.example.task.exception;

import com.example.task.model.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

  private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
    LOGGER.error("Validation failed", ex);
    ErrorResponse customError = ErrorResponse.builder()
      .httpStatus(HttpStatus.BAD_REQUEST)
      .message(ex.getMessage())
      .build();
    return new ResponseEntity<>(customError, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(TodoNotFoundException.class)
  public ResponseEntity<Object> handleTodoNotFoundException(TodoNotFoundException ex) {
    LOGGER.error("TodoNotFoundException ", ex);
    ErrorResponse customError = ErrorResponse.builder()
      .httpStatus(HttpStatus.NOT_FOUND)
      .message(ex.getMessage())
      .build();
    return new ResponseEntity<>(customError, HttpStatus.NOT_FOUND);
  }

}
