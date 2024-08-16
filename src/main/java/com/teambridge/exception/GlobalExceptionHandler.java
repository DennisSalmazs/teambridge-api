package com.teambridge.exception;

import com.teambridge.dto.ResponseWrapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/*
  Advice -- the logic to be executed whenever certain exception is thrown,
  instead of default exception message
 */

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ExceptionWrapper> userNotFoundException(UserNotFoundException exception) {
        exception.printStackTrace(); // this will print exception details on the console
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ExceptionWrapper(exception.getMessage(), HttpStatus.NOT_FOUND.value()));
    }
}
