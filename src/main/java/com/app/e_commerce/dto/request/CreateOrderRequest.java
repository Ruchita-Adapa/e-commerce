package com.app.e_commerce.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

import java.math.BigInteger;
import java.util.List;

@Builder
@Data
public class CreateOrderRequest {

    @JsonProperty("user_id")
    @NotBlank(message = "UserId is required")
    private String userId;

    @NotBlank(message = "Placed From is required")
    @JsonProperty("placed_from")
    private String placedFrom;

    @JsonProperty("product_order")
    private List<ProductOrder> productOrder;

    @Data
    @Builder
    public static class ProductOrder {
        @NotBlank(message = "Product Id is required")
        @JsonProperty("product_id")
        private String productId;

        @NotBlank(message = "Quantity is required")
        private int quantity;
    }
}
