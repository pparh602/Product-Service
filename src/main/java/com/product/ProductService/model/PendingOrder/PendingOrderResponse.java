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
public class PendingOrderResponse {

  private String id;

  private String quantity;

  private String productName;

  private Instant orderDate;

  private String orderStatus;

  private long amount;
}
