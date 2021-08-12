package com.example.marketplace.payload.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;

@Getter
@Setter
public class RegistrationRequest {

    @NotNull(message = "Is required")
    @Pattern(
            regexp = "^([a-zA-Z])+([\\w]{2,})+$",
            message = "Invalid username"
    )
    private String username;

    @NotNull(message = "Email is required")
    @Pattern(
            regexp = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$",
            message = "Invalid email"
    )
    private String email;

    @NotNull(message = "password is required")
    @Pattern(
            regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&+=])(?=\\S+$).{8,}$",
            message = "Invalid password"
    )
    private String password;

}
