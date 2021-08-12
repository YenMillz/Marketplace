package com.example.marketplace.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RegistrationResponse {
    private Long id;
    private String username;
    private String email;
}
