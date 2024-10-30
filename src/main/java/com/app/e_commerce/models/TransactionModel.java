package com.app.e_commerce.models;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class TransactionModel {
    public String transaction_id;
    public String user_id;
    public Double cost;
    public String status;
    public Long created_at;
    public Long updated_at;
}
