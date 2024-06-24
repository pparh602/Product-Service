package com.product.ProductService.model;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class OrderResponse {
    private String id;

    private String quantity;

    private String productName;

    private Instant orderDate;

    private String orderStatus;

    private long amount;
}
