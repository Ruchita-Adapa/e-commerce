package com.app.e_commerce.service;

import com.app.e_commerce.dto.request.CreateOrderRequest;
import com.app.e_commerce.models.OrderModel;
import com.app.e_commerce.models.ProductModel;
import com.app.e_commerce.models.TransactionModel;
import com.app.e_commerce.repository.OrderRepository;
import com.app.e_commerce.repository.ProductRepository;
import com.app.e_commerce.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    public boolean createOrder(CreateOrderRequest createOrderRequest) {
        List<String> productIds = createOrderRequest.getProductOrder().stream().map(item -> {
            String currentItemId = item.getProductId();
            return currentItemId;
        }).collect(Collectors.toList());
        Map<String, ProductModel> productModels = productRepository.getListProductIds(productIds).stream().collect(Collectors.toMap((item) -> {
            return item.getId();
        }, (item) -> {
            return item;
        }));

        Double cost = createOrderRequest.getProductOrder().stream().map(item -> {
            String currentItemId = item.getProductId();
            ProductModel productModel = productModels.get(currentItemId);
            if (item.getQuantity() > productModel.availableCount) {
                throw new RuntimeException("Wrong quantity requested");
            } else {
                productRepository.updateProductQuantity(currentItemId, productModel.availableCount - item.getQuantity());
                return item.getQuantity() * productModel.cost;
            }
        }).reduce(0.0, (a, b) -> {
            return a + b;
        });

        UUID transactionId = UUID.randomUUID();
        List<OrderModel> createdOrders = createOrderRequest.getProductOrder().stream().map(item -> {
            String currentItemId = item.getProductId();
            OrderModel orderModel = OrderModel.builder()
                    .order_id(UUID.randomUUID().toString())
                    .transaction_id(transactionId.toString())
                    .product_id(currentItemId)
                    .quantity(item.getQuantity())
                    .user_id(createOrderRequest.getUserId())
                    .status("PLACED_ORDER")
                    .placed_from(createOrderRequest.getPlacedFrom())
                    .created_at(System.currentTimeMillis())
                    .updated_at(System.currentTimeMillis())
                    .build();
            return orderModel;
        }).collect(Collectors.toList());
        TransactionModel transactionModel = TransactionModel.builder()
                .transaction_id(transactionId.toString())
                .user_id(createOrderRequest.getUserId())
                .cost(cost)
                .status("PLACED_ORDER")
                .created_at(System.currentTimeMillis())
                .updated_at(System.currentTimeMillis())
                .build();
        transactionRepository.createOrder(transactionModel);
        return orderRepository.createOrder(createdOrders);
    }

    public Double productCost(CreateOrderRequest createOrderRequest, ProductModel productModels) {
        Double cost = createOrderRequest.getProductOrder().stream().map(item -> {
            String currentItemId = item.getProductId();
            ProductModel productModel = productModels.get(currentItemId);
            if (item.getQuantity() > productModel.availableCount) {
                throw new RuntimeException("Wrong quantity requested");
            } else {
                productRepository.updateProductQuantity(currentItemId, productModel.availableCount - item.getQuantity());
                return item.getQuantity() * productModel.cost;
            }
        }).reduce(0.0, (a, b) -> {
            return a + b;
        });
    }
}
