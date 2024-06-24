package com.product.ProductService.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "pending_order")
public class PendingOrder {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  @Column(name = "QUANTITY")
  private String quantity;

  @Column(name = "PRODUCT_NAME")
  private String productName;

  @Column(name = "ORDER_DATE")
  private Instant orderDate;

  @Column(name = "STATUS")
  private String orderStatus;

  @Column(name = "TOTAL_AMOUNT")
  private long amount;
}
