package com.robertdennett.orderTracker.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(value = {ResourceNotFoundException.class})
    public ResponseEntity<Object> handleResourceNotFoundException(ResourceNotFoundException e) {
        return getResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {BadRequestException.class})
    public ResponseEntity<Object> handleBadRequestException(BadRequestException e) {
        return getResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<Object> getResponseEntity(String message, HttpStatus status) {
        return new ResponseEntity<>(new ApiResponseRecord(
                message,
                status,
                ZonedDateTime.now(ZoneId.of("Z")) // UTC time
        ), status);
    }
}
