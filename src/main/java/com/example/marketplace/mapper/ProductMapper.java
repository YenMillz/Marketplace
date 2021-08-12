package com.example.marketplace.mapper;

import com.example.marketplace.model.Product;
import com.example.marketplace.payload.response.ProductResponse;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProductMapper {

    Page<ProductResponse> findAll();

    Page<ProductResponse> findAllUserProducts(Long userId);

    ProductResponse findProductById(Long productId);

    ProductResponse findUserProductById(Long productId, Long userId);

    ProductResponse createUserProduct(Product product);

    ProductResponse updateUserProduct(Product product);

    void deleteUserProduct(Long productId);
}
