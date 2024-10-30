package com.app.e_commerce.repository;

import com.app.e_commerce.models.OrderModel;
import com.app.e_commerce.models.TransactionModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TransactionRepository {

    @Autowired
    public NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public boolean createOrder(TransactionModel transactionModels) {
        String query = "INSERT INTO transactions (transaction_id, user_id, cost, status, created_at, updated_at) " +
                "VALUES (:transactionId, :userId, :cost, :status, :createdAt, :updatedAt)";

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("transactionId", transactionModels.getTransaction_id())
                .addValue("userId", transactionModels.getUser_id())
                .addValue("cost", transactionModels.getCost())
                .addValue("status", transactionModels.getStatus())
                .addValue("createdAt", transactionModels.getCreated_at())
                .addValue("updatedAt", transactionModels.getUpdated_at());

        int rowsAffected = namedParameterJdbcTemplate.update(query, params);
        return rowsAffected > 0;
    }
}
