package com.product.ProductService.model.CompletedOrder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CompletdOrderResponse {

  private String id;

  private String quantity;

  private String productName;

  private Instant orderDate;

  private String orderStatus;

  private long amount;
}
