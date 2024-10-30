package com.app.e_commerce.service;

import com.app.e_commerce.dto.request.CreateProductRequest;
import com.app.e_commerce.dto.response.ProductDto;
import com.app.e_commerce.models.ProductModel;
import com.app.e_commerce.models.UserModel;
import com.app.e_commerce.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.UUID;


@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public boolean createProduct(CreateProductRequest createProductRequest) {
        UUID productId = UUID.randomUUID();
        ProductModel productData = ProductModel.builder()
                .id(productId.toString())
                .name(createProductRequest.getName())
                .cost(createProductRequest.getCost())
                .availableCount(createProductRequest.getAvailableCount())
                .createdAt(System.currentTimeMillis())
                .updatedAt(System.currentTimeMillis())
                .build();
        return productRepository.createProduct(productData);
    }

    public boolean updateProduct(CreateProductRequest createProductRequest, String productId) {
        ProductModel productModel = ProductModel.builder()
                .name(createProductRequest.getName())
                .cost(createProductRequest.getCost())
                .availableCount(createProductRequest.getAvailableCount())
                .updatedAt(System.currentTimeMillis())
                .build();
        return productRepository.updateProduct(productModel, productId);
    }

    public boolean deleteProduct(String productId) {
        return productRepository.deleteProduct(productId);
    }

    public ProductDto getProductById(String productId) {
        ProductModel productData = productRepository.getProductById(productId);
        return ProductDto.builder()
                .name(productData.getName())
                .cost(productData.getCost())
                .availableCount(productData.getAvailableCount())
                .createdAt(productData.getCreatedAt())
                .updatedAt(productData.getUpdatedAt())
                .build();

    }

}
