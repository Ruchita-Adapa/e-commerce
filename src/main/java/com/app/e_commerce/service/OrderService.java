package com.app.e_commerce.service;

import com.app.e_commerce.dto.request.CreateOrderRequest;
import com.app.e_commerce.dto.response.OrderDto;
import com.app.e_commerce.dto.response.OrderProductDto;
import com.app.e_commerce.models.OrderModel;
import com.app.e_commerce.models.OrderProductModel;
import com.app.e_commerce.models.ProductModel;
import com.app.e_commerce.models.TransactionModel;
import com.app.e_commerce.repository.OrderRepository;
import com.app.e_commerce.repository.ProductRepository;
import com.app.e_commerce.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public Double productCost(CreateOrderRequest createOrderRequest, Map<String, ProductModel> productModels) {
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
        return cost;
    }

    public List<OrderModel> createOrderModel(CreateOrderRequest createOrderRequest, String transactionId) {
        return createOrderRequest.getProductOrder().stream().map(item -> {
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
    }

    public TransactionModel createTransactionModel(CreateOrderRequest createOrderRequest, String transactionId, Double cost) {
        return TransactionModel.builder()
                .transactionId(transactionId)
                .userId(createOrderRequest.getUserId())
                .cost(cost)
                .status("PLACED_ORDER")
                .createdAt(System.currentTimeMillis())
                .updatedAt(System.currentTimeMillis())
                .build();
    }

    @Transactional
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

        Double cost = this.productCost(createOrderRequest, productModels);
        UUID transactionId = UUID.randomUUID();
        List<OrderModel> createdOrders = this.createOrderModel(createOrderRequest, transactionId.toString());
        TransactionModel transactionModel = this.createTransactionModel(createOrderRequest, transactionId.toString(), cost);
        transactionRepository.createOrder(transactionModel);
        return orderRepository.createOrder(createdOrders);
    }


    public List<OrderProductDto> getOrdersByTransactionId(String transactionId) {
        List<OrderProductModel> orderProductModels = orderRepository.getOrdersByTransactionId(transactionId);
        return orderProductModels.stream().map((orderProduct) -> {
            return OrderProductDto.builder()
                    .productId(orderProduct.getProductId())
                    .productName(orderProduct.getProductName())
                    .cost(orderProduct.getCost())
                    .orderId(orderProduct.getOrderId())
                    .placedFrom(orderProduct.getPlacedFrom())
                    .quantity(orderProduct.getQuantity())
                    .createdAt(orderProduct.getCreatedAt())
                    .updatedAt(orderProduct.getUpdatedAt())
                    .build();
        }).collect(Collectors.toList());
    }
}
