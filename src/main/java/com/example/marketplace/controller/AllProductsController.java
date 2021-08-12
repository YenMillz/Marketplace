package com.example.marketplace.controller;

import com.example.marketplace.payload.response.PageResponse;
import com.example.marketplace.payload.response.ProductResponse;
import com.example.marketplace.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Min;

@RestController
@Tag(name = "all-products", description = "All products API")
@RequestMapping("/all-products")
public class AllProductsController {

    @Autowired
    ProductService productService;

    @Operation(summary = "Find all products", description = "Find all products", responses = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = @Content(schema = @Schema(implementation = PageResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid page format",
                    content = @Content)
    }
    )
    @GetMapping(produces = "application/json")
    public ResponseEntity<PageResponse> findAll(@RequestParam(value = "Current page number") @Min(0) int page,
                                                @RequestParam(value = "Quantity per page") @Min(0) int size) {
        return new ResponseEntity<>(productService.findAll(page, size), HttpStatus.OK);
    }
}
