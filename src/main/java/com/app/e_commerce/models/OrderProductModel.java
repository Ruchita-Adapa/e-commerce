package com.app.e_commerce.models;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class OrderProductModel {
    private String productId;
    private String productName;
    private Double cost;
    private String orderId;
    private String placedFrom;
    private int quantity;
    private Long createdAt;
    private Long updatedAt;
}
