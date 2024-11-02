package com.app.e_commerce.models;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class TransactionModel {
    public String transactionId;
    public String userId;
    public Double cost;
    public String status;
    public Long createdAt;
    public Long updatedAt;
}
