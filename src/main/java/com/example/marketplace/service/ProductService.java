package com.example.marketplace.service;

import com.example.marketplace.exception.ResourceConflictException;
import com.example.marketplace.exception.ResourceNotFoundException;
import com.example.marketplace.mapper.ProductMapper;
import com.example.marketplace.mapper.RatingMapper;
import com.example.marketplace.model.Product;
import com.example.marketplace.model.Rating;
import com.example.marketplace.payload.request.ProductRequest;
import com.example.marketplace.payload.response.PageResponse;
import com.example.marketplace.payload.response.ProductResponse;
import com.example.marketplace.security.jwt.JwtUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProductService {
    @Autowired
    ProductMapper productMapper;

    @Autowired
    RatingMapper ratingMapper;

    @Autowired
    JwtUtils jwtUtils;

    public PageResponse findAll(int page, int size) {
        PageHelper.startPage(page, size).getEndRow();
        List<ProductResponse> products = productMapper.findAll();
        PageInfo<ProductResponse> pageInfo = new PageInfo<>(products);
        return new PageResponse(pageInfo.getSize(), products);
    }

    public PageResponse findAllUserProducts(int page, int size) {
        PageHelper.startPage(page, size).getEndRow();
        List<ProductResponse> products = productMapper.findAllUserProducts(jwtUtils.getCurrentUser());
        PageInfo<ProductResponse> pageInfo = new PageInfo<>(products);
        return new PageResponse(pageInfo.getSize(), products);
    }

    public ProductResponse findUserProductById(Long productId) throws ResourceNotFoundException {
        ProductResponse product = productMapper.findUserProductById(productId, jwtUtils.getCurrentUser());
        if (product == null) {
            throw new ResourceNotFoundException(String.format("product with id %s not found", productId));
        }
        return product;
    }

    public ProductResponse createUserProduct(ProductRequest productRequest) {
        return productMapper.createUserProduct(new Product(
                productRequest.getTitle(),
                productRequest.getPrice(),
                productRequest.getDescription(),
                jwtUtils.getCurrentUser())
        );
    }

    public ProductResponse updateUserProduct(Long productId, ProductRequest productRequest) throws ResourceNotFoundException {

        Long userId = jwtUtils.getCurrentUser();
        ProductResponse product = productMapper.findUserProductById(productId, userId);
        if (product == null) {
            throw new ResourceNotFoundException(String.format("product with id %s not found", productId));
        }
        return productMapper.updateUserProduct(new Product(
                productId,
                productRequest.getTitle(),
                productRequest.getPrice(),
                productRequest.getDescription(),
                userId
        ));
    }

    public Map<String, String> deleteUserProduct(Long productId) {
        ProductResponse product = productMapper.findUserProductById(productId, jwtUtils.getCurrentUser());
        if (product == null) {
            throw new ResourceNotFoundException(String.format("product with id %s not found", productId));
        }

        productMapper.deleteUserProduct(productId);

        Map<String, String> response = new HashMap<>();
        response.put("detail", "Object deleted");
        return response;
    }

    public ProductResponse patchLikeUnlike(Long productId, Boolean status) {

        Long userId = jwtUtils.getCurrentUser();

        ProductResponse product = productMapper.findProductById(productId);
        if (product == null) {
            throw new ResourceNotFoundException(String.format("product with id %s not found", productId));
        } else if (product.getUserId().equals(userId)) {
            throw new ResourceConflictException("unable to like/unlike own products");
        } else {
            Rating rating = ratingMapper.findRatingById(userId, productId);
            if (rating == null) {
                ratingMapper.createRating(userId, productId, status);
            } else {
                if (rating.getLike() == status) {
                    rating.setLike(null);
                } else if (rating.getLike() == null) {
                    rating.setLike(status);
                } else {
                    rating.setLike(!rating.getLike());
                }
                ratingMapper.updateRating(rating);
            }
        }

        return productMapper.findProductById(productId);

    }
}
