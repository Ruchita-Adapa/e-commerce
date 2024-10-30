package com.app.e_commerce.models;

import lombok.Builder;
import lombok.Data;

import java.math.BigInteger;

@Builder
@Data
public class OrderModel {
    public String order_id;
    public String transaction_id;
    public String product_id;
    public int quantity;
    public String user_id;
    public String status;
    public String placed_from;
    public Long created_at;
    public Long updated_at;
}