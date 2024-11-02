package com.app.e_commerce.service;

import com.app.e_commerce.dto.response.OrderProductDto;
import com.app.e_commerce.dto.response.TransactionDto;
import com.app.e_commerce.dto.response.TransactionOrderDto;
import com.app.e_commerce.models.TransactionModel;
import com.app.e_commerce.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private OrderService orderService;



    public List<TransactionOrderDto> getTransactions(int limit, int order) {
        List<TransactionModel> transactions =  transactionRepository.getTransactions(limit, order);
        return transactions.stream().map((transaction) -> {
             List<OrderProductDto> orderProductDtos = orderService.getOrdersByTransactionId(transaction.getTransactionId());
             TransactionDto transactionDto = TransactionDto.builder()
                    .transactionId(transaction.getTransactionId())
                    .userId(transaction.getUserId())
                    .cost(transaction.getCost())
                    .status(transaction.getStatus())
                    .createdAt(transaction.getCreatedAt())
                    .updatedAt(transaction.getUpdatedAt())
                    .build();
             return TransactionOrderDto.builder()
                     .orders(orderProductDtos)
                     .transaction(transactionDto)
                     .build();
        }).collect(Collectors.toList());
    }
}
