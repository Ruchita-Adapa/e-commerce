package com.app.e_commerce.repository;

import com.app.e_commerce.dto.response.OrderDto;
import com.app.e_commerce.models.OrderModel;
import com.app.e_commerce.models.OrderProductModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class OrderRepository {

    @Autowired
    public NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public boolean createOrder(List<OrderModel> orderModels) {
        String sql = "INSERT INTO orders (order_id, transaction_id, product_id, quantity, user_id, status, placed_from, created_at, updated_at) " +
                "VALUES (:orderId, :transactionId, :productId, :quantity, :userId, :status, :placedFrom, :createdAt, :updatedAt)";

        SqlParameterSource[] batchParams = orderModels.stream()
                .map(order -> {
                    MapSqlParameterSource params = new MapSqlParameterSource();
                    params.addValue("orderId", order.getOrder_id());
                    params.addValue("transactionId", order.getTransaction_id());
                    params.addValue("productId", order.getProduct_id());
                    params.addValue("quantity", order.getQuantity());
                    params.addValue("userId", order.getUser_id());
                    params.addValue("status", order.getStatus());
                    params.addValue("placedFrom", order.getPlaced_from());
                    params.addValue("createdAt", order.getCreated_at());
                    params.addValue("updatedAt", order.getUpdated_at());
                    return params;
                })
                .toArray(SqlParameterSource[]::new);

        int[] updateCounts = namedParameterJdbcTemplate.batchUpdate(sql, batchParams);

        return updateCounts.length == orderModels.size();
    }

    public List<OrderProductModel> getOrdersByTransactionId(String transactionId) {
        String query = """
                SELECT p.id as product_id, p.name as product_name,
                p.cost as product_cost, o.order_id as order_id,
                o.placed_from as placed_from, o.quantity as quantity,
                o.created_at as created_at, o.updated_at as updated_at
                FROM product p
                inner join orders o on p.id = o.product_id
                WHERE o.transaction_id = :transactionId
                """;
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("transactionId", transactionId);

        return namedParameterJdbcTemplate.query(query, params, (rs, rowValue) -> {
            return OrderProductModel.builder()
                    .productId(rs.getString("product_id"))
                    .productName(rs.getString("product_name"))
                    .cost(rs.getDouble("product_cost"))
                    .orderId(rs.getString("order_id"))
                    .placedFrom(rs.getString("placed_from"))
                    .quantity(rs.getInt("quantity"))
                    .createdAt(rs.getLong("created_at"))
                    .updatedAt(rs.getLong("updated_at"))
                    .build();
        });
    }
}
