package com.app.e_commerce.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class ProductDto {
    private String name;
    private Double cost;
    private int availableCount;
    private Long createdAt;
    private Long updatedAt;
}
