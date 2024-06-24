package com.product.ProductService.service.impl;

import com.product.ProductService.entity.CompletedOrder;
import com.product.ProductService.entity.PendingOrder;
import com.product.ProductService.exception.OrderServiceCustomException;
import com.product.ProductService.projections.CombinedOrderProjection;
import com.product.ProductService.model.CompletedOrder.CompletedOrderRequest;
import com.product.ProductService.model.OrderResponse;
import com.product.ProductService.model.PendingOrder.PendingOrderRequest;
import com.product.ProductService.repository.CompletedOrderRepository;
import com.product.ProductService.repository.OrderRepository;
import com.product.ProductService.repository.PendingOrderRepository;
import com.product.ProductService.service.OrderService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.OffsetDateTime;

@Service
@Log4j2
public class OrderServiceImpl implements OrderService {

  @Autowired private PendingOrderRepository pendingOrderRepository;

  @Autowired private CompletedOrderRepository completedOrderRepository;

  @Autowired private OrderRepository orderRepository;

  @Override
  public String addPendingOrders(PendingOrderRequest pendingOrderRequest) {
    log.info("Pending Order Request Initiated: {}", pendingOrderRequest);
    PendingOrder pendingOrder =
        PendingOrder.builder()
            .productName(pendingOrderRequest.getProductName())
            .quantity(pendingOrderRequest.getQuantity())
            .orderDate(Instant.now())
            .amount(pendingOrderRequest.getAmount())
            .orderStatus("PENDING")
            .build();

    PendingOrder addedPendingOrder = pendingOrderRepository.save(pendingOrder);
    return addedPendingOrder.getId();
  }

  @Override
  public String addCompletedOrders(CompletedOrderRequest completedOrderRequest) {
    log.info("Completed Order Request Initiated: {}", completedOrderRequest);
    CompletedOrder completedOrder =
        CompletedOrder.builder()
            .productName(completedOrderRequest.getProductName())
            .quantity(completedOrderRequest.getQuantity())
            .orderDate(Instant.now())
            .amount(completedOrderRequest.getAmount())
            .orderStatus("COMPLETED")
            .build();

    CompletedOrder addedCompletedOrder = completedOrderRepository.save(completedOrder);

    return addedCompletedOrder.getId();
  }

  @Override
  public Page<OrderResponse> getCombinedOrders(
      Instant startDate, Instant endDate, Pageable pageable) {
    try {
      Page<CombinedOrderProjection> combinedOrderPage =
          orderRepository.findCombinedOrders(startDate, endDate, pageable);
      return combinedOrderPage.map(this::convertToOrderResponse);
    } catch (Exception e) {
      log.error("Failed to retrieve combined orders from the database", e);
      throw new OrderServiceCustomException(
          "Something went wrong while fetching the orders. Please contact the administrator!",
          HttpStatus.INTERNAL_SERVER_ERROR.toString());
    }
  }

  // Helper method to convert CombinedOrderProjection to OrderResponse
  private OrderResponse convertToOrderResponse(CombinedOrderProjection order) {
    return OrderResponse.builder()
        .id(order.getId())
        .quantity(order.getQuantity())
        .productName(order.getProductName())
        .orderDate(convertToInstant(order.getOrderDate()))
        .orderStatus(order.getOrderStatus())
        .amount(order.getAmount())
        .build();
  }

  // Helper method to convert OffsetDateTime to Instant
  private Instant convertToInstant(OffsetDateTime offsetDateTime) {
    return offsetDateTime != null ? offsetDateTime.toInstant() : null;
  }
}
