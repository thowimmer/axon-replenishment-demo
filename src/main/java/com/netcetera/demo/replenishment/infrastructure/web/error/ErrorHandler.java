package com.netcetera.demo.replenishment.infrastructure.web.error;

import org.axonframework.modelling.command.AggregateNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.NoSuchElementException;

import static org.springframework.http.HttpStatus.*;

@ControllerAdvice
public class ErrorHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(AggregateNotFoundException.class)
    public ResponseEntity<Object> handleAggregateNotFoundException(AggregateNotFoundException ex, WebRequest request) {
        String errorMessage = String.format("Aggregate with 'id' %s not found.", ex.getAggregateIdentifier());
        ErrorResponse responseBody = new ErrorResponse(errorMessage);
        return handleExceptionInternal(ex, responseBody, new HttpHeaders(), NOT_FOUND, request);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<Object> handleNoSuchElementException(NoSuchElementException ex, WebRequest request) {
        ErrorResponse responseBody = new ErrorResponse(ex.getMessage());
        return handleExceptionInternal(ex, responseBody, new HttpHeaders(), NOT_FOUND, request);
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<Object> handleIllegalStateException(IllegalStateException ex, WebRequest request) {
        ErrorResponse responseBody = new ErrorResponse(ex.getMessage());
        return handleExceptionInternal(ex, responseBody, new HttpHeaders(), UNPROCESSABLE_ENTITY, request);
    }
}
