package com.example.marketplace.payload.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponse {
    private Long id;
    private String title;
    private Double price;
    private String description;
    private int likes;
    private int unlikes;
    @JsonIgnore
    private Long userId;
}
