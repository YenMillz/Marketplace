package com.example.marketplace.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class PageResponse {

    private int count;
    private List<ProductResponse> result;

}
