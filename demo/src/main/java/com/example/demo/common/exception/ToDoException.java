package com.example.demo.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public class ToDoException extends RuntimeException {
    @Getter
    String errorCode;
    HttpStatus statusCode;

    public ToDoException(HttpStatus statusCode, String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
        this.statusCode = statusCode;
    }

    public ToDoException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public ToDoException(String errorCode, String message, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public ToDoException(HttpStatus statusCode, String errorCode, String message, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
        this.statusCode = statusCode;
    }

}