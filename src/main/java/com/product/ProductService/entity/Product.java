package com.product.ProductService.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "PRODUCT_NAME")
    private String name;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "BRAND")
    private String brand;

    @Column(name = "TAGS")
    private List<String> tags;

    @Column(name = "CATEGORY")
    private String category;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "UTC")
    @CreationTimestamp
    private Instant createdAt;
}
