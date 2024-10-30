package com.app.e_commerce.dto.request;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class CreateProductRequest {
    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "cost is required")
    private Double cost;

    @NotBlank(message = "Available Count is required")
    @JsonProperty("available_count")
    private int availableCount;
}
