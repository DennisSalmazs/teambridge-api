package com.teambridge.exception;

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

    @ExceptionHandler(UserAlreadyExistException.class)
    public ResponseEntity<ExceptionWrapper> userAlreadyExistException(UserAlreadyExistException exception) {
        exception.printStackTrace();
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(new ExceptionWrapper(exception.getMessage(), HttpStatus.CONFLICT.value()));
    }

    @ExceptionHandler({Exception.class, RuntimeException.class, Throwable.class})
    public ResponseEntity<ExceptionWrapper> genericException(Throwable exception) {
        exception.printStackTrace();
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ExceptionWrapper("An error occurred. Try again!", HttpStatus.INTERNAL_SERVER_ERROR.value()));
    }
}
