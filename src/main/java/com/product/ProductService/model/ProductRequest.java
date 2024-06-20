package com.product.ProductService.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.List;

@Data
@Builder
public class ProductRequest {
    @NotBlank(message = "Product name is required")
    private String name;

    @NotBlank(message = "Product description is required")
    private String description;

    @NotBlank(message = "Product brand is required")
    private String brand;

    private List<String> tags;

    @NotEmpty(message = "Product category is required")
    private String category;
    private Instant createdAt;
}
