package com.product.ProductService.projections;

import java.time.OffsetDateTime;

public interface CombinedOrderProjection {
    String getId();
    String getQuantity();
    String getProductName();
    OffsetDateTime getOrderDate();
    String getOrderStatus();
    long getAmount();
}
