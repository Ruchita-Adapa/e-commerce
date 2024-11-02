package com.app.e_commerce.repository;

import com.app.e_commerce.models.ProductModel;
import com.app.e_commerce.models.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductRepository {

    @Autowired
    public NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public boolean createProduct(ProductModel productModel) {
        String query = """
                INSERT INTO product (id, name, cost, available_count, created_at, updated_at)
                VALUES(:id, :name, :cost, :available_count, :created_at, :updated_at)
                """;
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("id", productModel.getId())
                .addValue("name", productModel.getName())
                .addValue("cost", productModel.getCost())
                .addValue("available_count", productModel.getAvailableCount())
                .addValue("created_at", productModel.getCreatedAt())
                .addValue("updated_at", productModel.getUpdatedAt());
        int rowsAffected = namedParameterJdbcTemplate.update(query, params);
        return rowsAffected > 0;
    }

    public boolean updateProduct(ProductModel productModel, String productId) {
        String query = """
        UPDATE product SET
        name = :name,
        cost = :cost,
        available_count = :available_count,
        updated_at = :updated_at
        WHERE id = :id
        """;

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("id", productId)
                .addValue("name", productModel.getName())
                .addValue("cost", productModel.getCost())
                .addValue("available_count", productModel.getAvailableCount())
                .addValue("updated_at", productModel.getUpdatedAt());

        return namedParameterJdbcTemplate.update(query, params) > 0;
    }

    public boolean deleteProduct(String productId) {
        String query = """
                DELETE FROM product
                WHERE id = :id
                """;
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("id", productId);

        return namedParameterJdbcTemplate.update(query, params) > 0;
    }

    public ProductModel getProductById(String productId) {
        String query = """
                SELECT * FROM product
                WHERE id = :id
                """;
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("id", productId);
        return namedParameterJdbcTemplate.queryForObject(query, params, (rs, rowNum) -> {
            return ProductModel.builder()
                    .id(rs.getString("ID"))
                    .name(rs.getString("name"))
                    .cost(rs.getDouble("cost"))
                    .availableCount(rs.getInt("available_count"))
                    .createdAt(rs.getLong("created_at"))
                    .updatedAt(rs.getLong("updated_at"))
                    .build();
        });
    }

    public List<ProductModel> getListProductIds(List<String> productIds) {
        String query = """
                SELECT * FROM product
                WHERE id in (:productIds)
                """;

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("productIds", productIds);

        return namedParameterJdbcTemplate.query(query, params, (rs, rowNum) -> {
            return ProductModel.builder()
                    .id(rs.getString("ID"))
                    .name(rs.getString("name"))
                    .cost(rs.getDouble("cost"))
                    .availableCount(rs.getInt("available_count"))
                    .createdAt(rs.getLong("created_at"))
                    .updatedAt(rs.getLong("updated_at"))
                    .build();
        });
    }


    public boolean updateProductQuantity(String productId, int quanity) {
        String query = """
                UPDATE product
                SET available_count = :quanity
                WHERE id = :productId
                """;
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("productId", productId)
                .addValue("quanity", quanity);

        return namedParameterJdbcTemplate.update(query, params) > 0;
    }

    public List<ProductModel> getProducts(int limit, int offset) {
        String query = """
                SELECT * from product
                ORDER BY id LIMIT :limit OFFSET :offset""";

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("limit", limit)
                .addValue("offset", offset);

        return namedParameterJdbcTemplate.query(query, params, (rs, rowNum) -> {
            return ProductModel.builder()
                    .id(rs.getString("ID"))
                    .name(rs.getString("name"))
                    .cost(rs.getDouble("cost"))
                    .availableCount(rs.getInt("available_count"))
                    .createdAt(rs.getLong("created_at"))
                    .updatedAt(rs.getLong("updated_at"))
                    .build();
        });
    }
}
