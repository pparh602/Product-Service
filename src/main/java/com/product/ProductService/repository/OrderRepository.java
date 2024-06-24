package com.product.ProductService.repository;

import com.product.ProductService.entity.CompletedOrder;
import com.product.ProductService.projections.CombinedOrderProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;

@Repository
public interface OrderRepository extends JpaRepository<CompletedOrder, String> {
  @Query(value = "SELECT po.id, po.quantity, po.product_name as productName, po.order_date as orderDate, po.status as orderStatus, po.total_amount as amount " +
          "FROM pending_order po " +
          "WHERE po.order_date BETWEEN :startDate AND :endDate " +
          "UNION ALL " +
          "SELECT co.id, co.quantity, co.product_name as productName, co.order_date as orderDate, co.status as orderStatus, co.total_amount as amount " +
          "FROM completed_order co " +
          "WHERE co.order_date BETWEEN :startDate AND :endDate",
          countQuery = "SELECT count(*) FROM (" +
                  "SELECT po.id FROM pending_order po WHERE po.order_date BETWEEN :startDate AND :endDate " +
                  "UNION ALL " +
                  "SELECT co.id FROM completed_order co WHERE co.order_date BETWEEN :startDate AND :endDate) as combined",
          nativeQuery = true)
  Page<CombinedOrderProjection> findCombinedOrders(@Param("startDate") Instant startDate, @Param("endDate") Instant endDate, Pageable pageable);
}
