package com.app.e_commerce.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class OrderDto {
    private String order_id;
    private ProductDto productInfo;
    private int quantity;
    private String user_id;
    private String status;
    private String placed_from;
    private Long created_at;
    private Long updated_at;
}
