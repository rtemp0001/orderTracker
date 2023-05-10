package com.robertdennett.orderTracker.exception;

import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

public record ApiResponseRecord(String message, HttpStatus status, ZonedDateTime timestamp) {}
