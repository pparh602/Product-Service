package com.product.ProductService.service;

import com.product.ProductService.model.CompletedOrder.CompletedOrderRequest;
import com.product.ProductService.model.OrderResponse;
import com.product.ProductService.model.PendingOrder.PendingOrderRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.Instant;

public interface OrderService {

    String addPendingOrders(PendingOrderRequest pendingOrderRequest);

    String addCompletedOrders(CompletedOrderRequest completedOrderRequest);

    Page<OrderResponse> getCombinedOrders(Instant startDate, Instant endDate, Pageable pageable);
}
