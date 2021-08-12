package com.example.marketplace.payload.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
public class ProductRequest {
    @NotBlank(message = "Title is required")
    private String title;

    @NotNull(message = "Price is required")
    @DecimalMin(value = "1.00", message = "price must be greater or equal to 1.00")
    private Double price;

    private String description;
}
