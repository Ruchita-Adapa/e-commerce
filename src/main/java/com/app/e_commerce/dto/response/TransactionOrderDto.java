package com.app.e_commerce.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class TransactionOrderDto {
    private List<OrderProductDto> orders;
    private TransactionDto transaction;
}
