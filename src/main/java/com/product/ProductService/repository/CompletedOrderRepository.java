package com.product.ProductService.repository;

import com.product.ProductService.entity.CompletedOrder;
import com.product.ProductService.entity.PendingOrder;
import java.time.Instant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompletedOrderRepository extends JpaRepository<CompletedOrder, String> {
  Page<PendingOrder> findByOrderDateBetween(Instant startData, Instant endDate, Pageable pageable);
}
