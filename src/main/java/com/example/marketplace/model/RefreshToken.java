package com.example.marketplace.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RefreshToken {

    private Long id;
    private String token;
    @JsonProperty(value = "expiry_date")
    private Instant expiryDate;
    @JsonProperty(value = "user_id")
    private Long userId;
}
