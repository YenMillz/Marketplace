package com.example.marketplace.advice;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ApiResponseMessage {
    private String status;
    private String detail;
}
