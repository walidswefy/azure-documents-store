package com.example.api.storage.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author walid.sewaify
 * @since 12-Dec-20
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class NamingException extends RuntimeException {
    public NamingException(String message) {
        super(message);
    }
}
