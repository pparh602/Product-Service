package com.product.ProductService.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.Instant;
import java.util.List;

@Data
public class ProductResponse {
    private String id;
    private String name;
    private String description;
    private String brand;
    private List<String> tags;
    private String category;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "UTC")
    private Instant createdAt;

}
