package com.app.e_commerce.repository;

import com.app.e_commerce.models.TransactionModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
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
                .addValue("transactionId", transactionModels.getTransactionId())
                .addValue("userId", transactionModels.getUserId())
                .addValue("cost", transactionModels.getCost())
                .addValue("status", transactionModels.getStatus())
                .addValue("createdAt", transactionModels.getCreatedAt())
                .addValue("updatedAt", transactionModels.getUpdatedAt());

        int rowsAffected = namedParameterJdbcTemplate.update(query, params);
        return rowsAffected > 0;
    }

    public List<TransactionModel> getTransactions(int limit, int offset) {
        String query = """
                SELECT * from transactions ORDER BY updated_at DESC limit :limit offset :offset
                """;

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("limit", limit)
                .addValue("offset", offset);

        return namedParameterJdbcTemplate.query(query, params, ((rs, rowNum) -> {
            return TransactionModel.builder()
                    .transactionId(rs.getString("transaction_id"))
                    .userId(rs.getString("user_id"))
                    .cost(rs.getDouble("cost"))
                    .status(rs.getString("status"))
                    .createdAt(rs.getLong("created_at"))
                    .updatedAt(rs.getLong("updated_at"))
                    .build();
        }));
    }
}
