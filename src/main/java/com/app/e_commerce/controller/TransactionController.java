package com.app.e_commerce.controller;

import com.app.e_commerce.dto.response.TransactionOrderDto;
import com.app.e_commerce.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @GetMapping
    public List<TransactionOrderDto> getTransaction(@RequestParam(defaultValue = "20") int limit,
                                                    @RequestParam(defaultValue = "0") int offset) {

        return transactionService.getTransactions(limit, offset);
    }
}
