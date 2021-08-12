package com.example.marketplace.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class ResourceAlreadyExistsException extends IllegalStateException {
    public ResourceAlreadyExistsException(String message) {
        super(message);
    }
}