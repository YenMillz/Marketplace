package com.example.marketplace.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Product {
    private Long id;
    private String title;
    private Double price;
    private String description;
    @JsonProperty(value = "user_id")
    private Long userId;

    public Product(String title, Double price, String description, Long userId) {
        this.title = title;
        this.price = price;
        this.description = description;
        this.userId = userId;
    }
}
