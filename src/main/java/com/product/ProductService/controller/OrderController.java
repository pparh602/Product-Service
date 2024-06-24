package com.product.ProductService.controller;

import com.product.ProductService.entity.PendingOrder;
import com.product.ProductService.model.CompletedOrder.CompletedOrderRequest;
import com.product.ProductService.model.OrderResponse;
import com.product.ProductService.model.PendingOrder.PendingOrderRequest;
import com.product.ProductService.service.OrderService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;

@RestController
@RequestMapping("/v1/orders/")
@Log4j2
public class OrderController {

  @Autowired private OrderService orderService;

  @PostMapping("complete")
  public ResponseEntity<String> addCompleteOrders(
      @RequestBody CompletedOrderRequest completedOrderRequest) {

    String placedOrderId = orderService.addCompletedOrders(completedOrderRequest);

    return new ResponseEntity<>(placedOrderId, HttpStatus.OK);
  }

  @PostMapping("pending")
  public ResponseEntity<String> addPendingOrders(
      @RequestBody PendingOrderRequest pendingOrderRequest) {

    String placedOrderId = orderService.addPendingOrders(pendingOrderRequest);

    return new ResponseEntity<>(placedOrderId, HttpStatus.OK);
  }

  @GetMapping("combined")
  public ResponseEntity<Page<OrderResponse>> getCombinedOrders(
      @RequestParam(name = "startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
      @RequestParam(name = "endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate,
      @RequestParam(name = "page", defaultValue = "0", required = false) int page,
      @RequestParam(name = "size", defaultValue = "10", required = false) int size,
      @RequestParam(name = "sortField", defaultValue = "orderDate", required = false)
          String sortFieldName,
      @RequestParam(name = "isSortDesc", defaultValue = "false") boolean isDescSortOrder) {

    // Handle invalid sortField
    if (!isValidOrderSortField(sortFieldName)) {
      log.error("Invalid sortField: {}", sortFieldName);
      sortFieldName = "orderDate"; // Default to sorting by orderDate
    }

    Sort sort =
        isDescSortOrder ? Sort.by(sortFieldName).descending() : Sort.by(sortFieldName).ascending();

    // Convert date to instant
    Instant startInstant = startDate.atStartOfDay().toInstant(ZoneOffset.UTC);
    Instant endInstant = endDate.plusDays(1).atStartOfDay().toInstant(ZoneOffset.UTC).minusNanos(1);

    Pageable pageable = PageRequest.of(page, size, sort);
    Page<OrderResponse> orders = orderService.getCombinedOrders(startInstant, endInstant, pageable);
    return new ResponseEntity<>(orders, HttpStatus.OK);
  }

  private boolean isValidOrderSortField(String sortFieldName) {
    try {
      // Check if the sortField exists as a property in the entity class
      OrderResponse.class.getDeclaredField(sortFieldName);
      return true;
    } catch (Exception e) {
      log.info("Invalid Sort field name provided: {}", sortFieldName);
      return false;
    }
  }
}
