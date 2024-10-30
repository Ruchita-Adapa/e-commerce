package com.app.e_commerce.database;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix = "custom.postgres")
public class PostgresProperties {
    private String host;
    private int port;
    private String database;
    private String username;
    private String password;
    private String schema;
}