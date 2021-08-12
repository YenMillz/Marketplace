package com.example.marketplace.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class ResourceConflictException extends IllegalStateException{
    public ResourceConflictException(String message) {
        super(message);
    }
}
