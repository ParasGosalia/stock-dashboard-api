package com.sample.payconiq.stocks.exception;

import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
@Slf4j
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    String bodyOfResponse;

    @ExceptionHandler(value = {StockNotFoundException.class})
    public ResponseEntity<?> stockNotFoundException(StockNotFoundException ex, WebRequest request) {
        log.warn("handling StockNotFoundException... {}",ex.getMessage());
        bodyOfResponse = "Stock Data not found for the requested Stock id";
        return handleConflict(ex,request, HttpStatus.NOT_FOUND,bodyOfResponse);
    }

    @ExceptionHandler(value = {UsernameNotFoundException.class})
    public ResponseEntity<?> userNameNotFoundException(UsernameNotFoundException ex, WebRequest request) {
        log.warn("handling UsernameNotFoundException...{}", ex.getMessage());
        bodyOfResponse = "Requested User not found";
        return handleConflict(ex,request, HttpStatus.NOT_FOUND,bodyOfResponse);
    }

    @ExceptionHandler(value = {BadCredentialsException.class})
    public ResponseEntity<?> badCredentialsException(BadCredentialsException ex, WebRequest request) {
        log.warn("handling BadCredentials... {}", ex.getMessage());
        bodyOfResponse = "Invalid Credentials provided by the user.";
        return  handleConflict(ex,request, HttpStatus.UNAUTHORIZED,bodyOfResponse);
    }

    @ExceptionHandler(value = {DisabledException.class})
    public ResponseEntity<?> disabledException(DisabledException ex, WebRequest request) {
        log.warn("handling BadCredentials... {}", ex.getMessage());
        bodyOfResponse = "user is disabled.";
        return  handleConflict(ex,request, HttpStatus.UNAUTHORIZED,bodyOfResponse);
    }


    @ExceptionHandler(value = {ExpiredJwtException.class})
    public ResponseEntity<?> jwtTokenExpiredException(ExpiredJwtException ex, WebRequest request) {
        log.warn("handling Token Expired exception... {}", ex.getMessage());
        bodyOfResponse = "JWT Token has expired. Please login";
        return  handleConflict(ex,request, HttpStatus.FORBIDDEN,bodyOfResponse);
    }


    @ExceptionHandler(value = {RuntimeException.class, Exception.class})
    public ResponseEntity<?> internalError(BadCredentialsException ex, WebRequest request) {
        log.warn("handling Internal Error... {}", ex.getMessage());
        bodyOfResponse = "We cannot process your request at the moment.";
        return handleConflict(ex,request, HttpStatus.INTERNAL_SERVER_ERROR, bodyOfResponse);
    }

    protected ResponseEntity<Object> handleConflict(
            Exception ex, WebRequest request, HttpStatus status, String Message ) {
        return handleExceptionInternal(ex, Message,
                new HttpHeaders(), status, request);
    }
}
