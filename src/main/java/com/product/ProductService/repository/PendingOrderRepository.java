package com.product.ProductService.repository;

import com.product.ProductService.entity.PendingOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;

@Repository
public interface PendingOrderRepository extends JpaRepository<PendingOrder, String> {
    Page<PendingOrder> findByOrderDateBetween(Instant startData, Instant endDate, Pageable pageable);

    long countByOrderDateBetween(Instant startData, Instant endDate);
}
