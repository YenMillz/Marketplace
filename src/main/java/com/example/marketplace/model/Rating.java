package com.example.marketplace.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Rating {
    private Long userId;
    private Long productId;
    private Boolean like;
}