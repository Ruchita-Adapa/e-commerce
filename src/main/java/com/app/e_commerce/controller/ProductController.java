package com.app.e_commerce.controller;

import com.app.e_commerce.dto.request.CreateProductRequest;
import com.app.e_commerce.dto.response.ProductDto;
import com.app.e_commerce.service.ProductService;
import com.app.e_commerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductService productService;

    @PostMapping
    public boolean createProduct(@RequestBody CreateProductRequest createProductRequest) {
        return productService.createProduct(createProductRequest);
    }

    @PutMapping("/{id}")
    public boolean updateProduct(@RequestBody CreateProductRequest createProductRequest, @PathVariable("id") String productId) {
        return productService.updateProduct(createProductRequest, productId);
    }

    @DeleteMapping("/{id}")
    public boolean deleteProduct(@PathVariable("id") String productId) {
        return productService.deleteProduct(productId);
    }

    @GetMapping("/{id}")
    public ProductDto getProductById(@PathVariable("id") String productId) {
        return productService.getProductById(productId);
    }
}
