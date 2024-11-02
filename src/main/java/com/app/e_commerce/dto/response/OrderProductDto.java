package com.app.e_commerce.dto.response;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class OrderProductDto {
    private String productId;
    private String productName;
    private Double cost;
    private String orderId;
    private String placedFrom;
    private int quantity;
    private Long createdAt;
    private Long updatedAt;
}
