package com.app.e_commerce.models;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Builder
@Data
public class ProductModel {
    public String id;
    public String name;
    public Double cost;
    public int availableCount;
    public Long createdAt;
    public Long updatedAt;
}
