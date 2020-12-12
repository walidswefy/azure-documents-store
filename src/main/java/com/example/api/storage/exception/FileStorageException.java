package com.example.api.storage.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author walid.sewaify
 * @since 12-Dec-20
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class FileStorageException extends RuntimeException {
    public FileStorageException(String message) {
        super(message);
    }
}
