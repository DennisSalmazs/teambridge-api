package com.teambridge.exception;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ExceptionWrapper {

    private LocalDateTime timestamp;
    private Integer status;
    private String message;

    public ExceptionWrapper(String message, Integer status) {
        this.message = message;
        this.status = status;
        this.timestamp = LocalDateTime.now();
    }
}
