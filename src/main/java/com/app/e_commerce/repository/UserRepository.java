package com.app.e_commerce.repository;

import com.app.e_commerce.models.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class UserRepository {

    @Autowired
    public NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public boolean createUser(UserModel createUserRequest) {
        String query = """
               INSERT INTO users (ID, name, email, address)
               VALUES (:id, :name, :email, :address)
               """;
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("id", createUserRequest.getId())
                .addValue("name", createUserRequest.getName())
                .addValue("email", createUserRequest.getEmail())
                .addValue("address", createUserRequest.getAddress());
        int rowsAffected = namedParameterJdbcTemplate.update(query, params);
        return rowsAffected > 0;
    }
    
    public UserModel getUserById(String id) {
        String query = """
                SELECT * from users
                WHERE ID = :id
                """;
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("id", id);
        return namedParameterJdbcTemplate.queryForObject(query, params, (rs, rowNum) -> {
            return UserModel.builder()
                            .id(rs.getString("ID"))
                    .name(rs.getString("name"))
                    .email(rs.getString("email"))
                    .address(rs.getString("address")).build();
        });
    }

    public List<UserModel> getAllUsers(int offset, int limit) {
        String query = """
                SELECT * from users
                LIMIT :limit OFFSET :offset
                """;
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("limit", limit)
                .addValue("offset", offset);
        return namedParameterJdbcTemplate.query(query, params, (rs, rowNum) -> {
            return UserModel.builder()
                    .id(rs.getString("ID"))
                    .name(rs.getString("name"))
                    .email(rs.getString("email"))
                    .address(rs.getString("address")).build();
        });
    }

    public boolean updateUser(UserModel createUserRequest, String userId) {
        Map<String, Object> updates = new LinkedHashMap<>();
        if (createUserRequest.getName() != null) updates.put("name", ":name");
        if (createUserRequest.getEmail() != null) updates.put("email", ":email");
        if (createUserRequest.getAddress() != null) updates.put("address", ":address");

        if (updates.isEmpty()) {
            throw new IllegalArgumentException("No values to update");
        }

        String setClause = updates.entrySet().stream()
                .map(entry -> entry.getKey() + " = " + entry.getValue())
                .collect(Collectors.joining(", "));

        String query = "UPDATE users SET " + setClause + " WHERE ID = :id;";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("id", userId)
                .addValue("name", createUserRequest.getName())
                .addValue("email", createUserRequest.getEmail())
                .addValue("address", createUserRequest.getAddress());
        int rowsAffected = namedParameterJdbcTemplate.update(query, params);
        return rowsAffected > 0;
    }

    public boolean deleteUser(String userId) {
        String query = """
                DELETE FROM users
                WHERE ID = :id;
                """;
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("id", userId);
        int rowsAffected = namedParameterJdbcTemplate.update(query, params);
        return rowsAffected > 0;
    }

}
