package com.app.e_commerce.dto.response;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class TransactionDto {
    private String transactionId;
    private String userId;
    private Double cost;
    private String status;
    private Long createdAt;
    private Long updatedAt;

}