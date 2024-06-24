package com.product.ProductService.model.PendingOrder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PendingOrderRequest {

  private String productName;

  private String quantity;

  private Instant orderDate;

  private String orderStatus;

  private long amount;
}
