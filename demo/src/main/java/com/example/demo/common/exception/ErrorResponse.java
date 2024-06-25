package com.example.demo.common.exception;

import lombok.Builder;

@Builder
public record ErrorResponse(String errorCode, String message) {
}