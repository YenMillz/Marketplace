package com.example.marketplace.payload.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class TokenRefreshRequest {
    @NotBlank
    @JsonProperty(value = "refresh_token")
    private String refreshToken;
}
