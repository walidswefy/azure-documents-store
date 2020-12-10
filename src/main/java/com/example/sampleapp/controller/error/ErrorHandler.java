package com.example.sampleapp.controller.error;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author walid.sewaify
 * @since 2020-03-04
 */
@ControllerAdvice
public class ErrorHandler extends ResponseEntityExceptionHandler {
    /**
     * Handler for @Valid request
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(org.springframework.web.bind.MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status, WebRequest request) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", new Date());
        body.put("status", status.value());
        List<String> errors = ex.getBindingResult().getFieldErrors().stream()
            .map(fieldError -> fieldError.getField() + " : " + fieldError.getDefaultMessage())
            .collect(Collectors.toList());
        body.put("errors", errors);
        return new ResponseEntity<>(body, headers, status);
    }

    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<Object> handleDatabaseException(DataAccessException ex) {
        logger.error(ex.getMessage(), ex);
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", new Date());
        body.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        body.put("errors", "Internal server error, please reach out to us for support!");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
    }
}
