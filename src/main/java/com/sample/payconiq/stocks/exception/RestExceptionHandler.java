package com.sample.payconiq.stocks.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
@Slf4j
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    String bodyOfResponse;

    @ExceptionHandler(value = {StockNotFoundException.class})
    public ResponseEntity<?> stockNotFoundException(StockNotFoundException ex, WebRequest request) {
        log.warn("handling StockNotFoundException... {}",ex.getMessage());
        return handleConflict(request, HttpStatus.NOT_FOUND,ex.getMessage());
    }

    @ExceptionHandler(value = {UsernameNotFoundException.class})
    public ResponseEntity<?> userNameNotFoundException(UsernameNotFoundException ex, WebRequest request) {
        log.warn("handling UsernameNotFoundException...{}", ex.getMessage());
        bodyOfResponse = "Requested User not found";
        return handleConflict(request, HttpStatus.NOT_FOUND,bodyOfResponse);
    }

    @ExceptionHandler(value = {RuntimeException.class})
    public ResponseEntity<?> internalError(RuntimeException ex, WebRequest request) {
        log.warn("handling Internal Error... {}", ex.getMessage());
        bodyOfResponse = "An Internal error has occurred.We cannot process your request at the moment.";
        return handleConflict(request, HttpStatus.INTERNAL_SERVER_ERROR, bodyOfResponse);
    }

    protected ResponseEntity<Object> handleConflict(
            WebRequest request, HttpStatus status, String message ) {

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("status", status.value());
        body.put("message", message);
        return new ResponseEntity<>(body, status);
    }
}
