package com.sample.payconiq.stocks.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
@Slf4j
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    String bodyOfResponse;

    @ExceptionHandler(value = {StockNotFoundException.class})
    public ResponseEntity<?> stockNotFoundException(StockNotFoundException ex, WebRequest request) {
        log.warn("handling StockNotFoundException... {}", ex.getMessage());
        return handleConflict(request, HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(value = {UsernameNotFoundException.class})
    public ResponseEntity<?> userNameNotFoundException(UsernameNotFoundException ex, WebRequest request) {
        log.warn("handling UsernameNotFoundException...{}", ex.getMessage());
        bodyOfResponse = "Requested User not found";
        return handleConflict(request, HttpStatus.NOT_FOUND, bodyOfResponse);
    }

    @ExceptionHandler(value = {BadCredentialsException.class})
    public ResponseEntity<?> badCredentialsException(BadCredentialsException ex, WebRequest request) {
        log.warn("handling Bad Credentials exception.. {}", ex.getMessage());
        bodyOfResponse = "Your username/password is incorrect";
        return handleConflict(request, HttpStatus.UNAUTHORIZED, bodyOfResponse);
    }

    @ExceptionHandler(value = {ConstraintViolationException.class})
    public ResponseEntity<?> constraintViolationException(ConstraintViolationException ex, WebRequest request) {
        log.warn("handling Bad Inputs.. {}", ex.getMessage());
        return handleConflict(request, HttpStatus.BAD_REQUEST, ex.getMessage().substring(ex.getMessage().lastIndexOf(":") + 1));
    }

    @ExceptionHandler(value = {RuntimeException.class})
    public ResponseEntity<?> internalError(RuntimeException ex, WebRequest request) {
        if (ex instanceof InternalAuthenticationServiceException) {
            log.warn("handling Disabled User exception.. {}", ex.getMessage());
            return handleConflict(request, HttpStatus.UNAUTHORIZED, ex.getMessage());
        } else {
            log.warn("handling Internal Error... {}", ex.getMessage());
            bodyOfResponse = "An Internal error has occurred.We cannot process your request at the moment.";
            return handleConflict(request, HttpStatus.INTERNAL_SERVER_ERROR, bodyOfResponse);
        }

    }


    protected ResponseEntity<Object> handleConflict(
            WebRequest request, HttpStatus status, String message) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("status", status.value());
        body.put("message", message);
        return new ResponseEntity<>(body, status);
    }

    @Override
    protected ResponseEntity<Object>
    handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                 HttpHeaders headers, HttpStatus status, WebRequest request) {
        BindingResult result = ex.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors();
        String errorMessage = fieldErrors.get(0).getDefaultMessage();
        return ResponseEntity.badRequest().body(errorMessage);
    }

}
