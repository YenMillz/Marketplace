package com.example.marketplace.advice;

import com.example.marketplace.exception.ResourceAlreadyExistsException;
import com.example.marketplace.exception.ResourceNotFoundException;
import com.example.marketplace.exception.TokenRefreshException;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.Map;

@Hidden
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(TokenRefreshException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ApiResponseMessage handleTokenRefreshException(TokenRefreshException e, WebRequest request) {
        return new ApiResponseMessage(
                HttpStatus.FORBIDDEN.toString(),
                e.getMessage()
        );
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiResponseMessage resourceNotFoundHandling(ResourceNotFoundException e, WebRequest request) {
        return new ApiResponseMessage(
                HttpStatus.NOT_FOUND.toString(),
                e.getMessage()
        );
    }

    @ExceptionHandler(ResourceAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiResponseMessage resourceAlreadyExistsHandling(ResourceNotFoundException e, WebRequest request) {
        return new ApiResponseMessage(
                HttpStatus.CONFLICT.toString(),
                e.getMessage()
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseEntity<?> processValidationError(MethodArgumentNotValidException e, WebRequest request) {

        Map<String, String> errors = new HashMap<>();
        /*errors.put("status", HttpStatus.BAD_REQUEST.toString());*/
        for (FieldError fieldError : e.getBindingResult().getFieldErrors()) {
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);

    }

}
