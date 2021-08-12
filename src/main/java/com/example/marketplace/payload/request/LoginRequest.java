package com.example.marketplace.payload.request;


import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class LoginRequest {

    @NotBlank(message = "Is required")
    private String username;

    @NotBlank(message = "Is required")
    private String password;

}
