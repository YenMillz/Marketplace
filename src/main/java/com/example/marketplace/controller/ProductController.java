package com.example.marketplace.controller;

import com.example.marketplace.advice.ApiResponseMessage;
import com.example.marketplace.payload.request.ProductRequest;
import com.example.marketplace.payload.response.PageResponse;
import com.example.marketplace.payload.response.ProductResponse;
import com.example.marketplace.security.jwt.JwtUtils;
import com.example.marketplace.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;

@SecurityRequirement(name = "bearer-key")
@Tag(name = "products", description = "The products API")
@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    ProductService productService;

    @Autowired
    JwtUtils jwtUtils;

    @Operation(summary = "Find all user products", description = "Find all user products", responses = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = @Content(schema = @Schema(implementation = PageResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid page format",
                    content = @Content)
    }
    )
    @GetMapping(produces = "application/json")
    public ResponseEntity<PageResponse> findAllUserProducts(@RequestParam(value = "Current page number") @Min(0) int page,
                                                            @RequestParam(value = "Quantity per page") @Min(0) int size) {
        return new ResponseEntity<>(productService.findAllUserProducts(page, size), HttpStatus.OK);
    }

    @Operation(summary = "Find product by ID", description = "Returns a single product", responses = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = @Content(schema = @Schema(implementation = ProductResponse.class))),
            @ApiResponse(responseCode = "404", description = "Product with such id not found",
                    content = @Content)
    }
    )
    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<ProductResponse> findUserProductById(@Parameter(description = "ID of product to return", required = true)
                                                               @PathVariable(value = "id") Long id) {
        return new ResponseEntity<>(productService.findUserProductById(id), HttpStatus.OK);
    }

    @Operation(summary = "Add a new product", description = "Add a new product to the marketplace", responses = {
            @ApiResponse(responseCode = "201", description = "Created",
                    content = @Content(schema = @Schema(implementation = ProductResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input",
                    content = @Content)
    }
    )
    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<ProductResponse> createUserProduct(@Valid @RequestBody ProductRequest productRequest) {
        return new ResponseEntity<>(productService.createUserProduct(productRequest), HttpStatus.CREATED);
    }

    @Operation(summary = "Update an existing product", description = "Update an existing product by ID", responses = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = @Content(schema = @Schema(implementation = ProductResponse.class))),
            @ApiResponse(responseCode = "404", description = "Product with such id not found",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid input",
                    content = @Content)
    }
    )
    @PutMapping(value = "/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<ProductResponse> updateUserProduct(@Parameter(description = "ID of product to update", required = true)
                                                             @PathVariable(value = "id") Long id,
                                                             @Valid @RequestBody ProductRequest productRequest) {
        return new ResponseEntity<>(productService.updateUserProduct(id, productRequest), HttpStatus.OK);
    }

    @Operation(summary = "Delete a product", description = "Delete a product", responses = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Product with such id not found",
                    content = @Content)
    }
    )
    @DeleteMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<ApiResponseMessage> deleteUserProduct(@PathVariable(value = "id") Long id) {
        return new ResponseEntity<>(new ApiResponseMessage(HttpStatus.OK.toString(), "Product deleted"), HttpStatus.OK);
    }

    @Operation(summary = "Patch like product", description = "Patch like product", responses = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = @Content(schema = @Schema(implementation = ProductResponse.class))),
            @ApiResponse(responseCode = "404", description = "Product with such id not found",
                    content = @Content)
    }
    )
    @PatchMapping(value = "/{id}/like", produces = "application/json")
    public ResponseEntity<ProductResponse> patchLike(@PathVariable(value = "id") Long id) {
        return new ResponseEntity<>(productService.patchLikeUnlike(id, true), HttpStatus.OK);
    }

    @Operation(summary = "Patch unlike product", description = "Patch unlike product", responses = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = @Content(schema = @Schema(implementation = ProductResponse.class))),
            @ApiResponse(responseCode = "404", description = "Product with such id not found",
                    content = @Content)
    }
    )
    @PatchMapping(value = "/{id}/unlike", produces = "application/json")
    public ResponseEntity<ProductResponse> patchUnlike(@PathVariable(value = "id") Long id) {
        return new ResponseEntity<>(productService.patchLikeUnlike(id, false), HttpStatus.OK);
    }

}
