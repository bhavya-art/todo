package com.example.demo.common.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler(value = ToDoException.class)
    protected ResponseEntity<Object> handleToDoException(ToDoException toDoException) {
        return ResponseEntity
                .status(toDoException.statusCode)
                .body(ErrorResponse.builder().errorCode(toDoException.getErrorCode())
                        .message(toDoException.getMessage())
                        .build());
    }
}
