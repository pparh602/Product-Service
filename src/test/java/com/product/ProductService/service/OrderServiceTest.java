package com.product.ProductService.service;

import com.product.ProductService.model.OrderResponse;
import com.product.ProductService.projections.CombinedOrderProjection;
import com.product.ProductService.repository.OrderRepository;
import com.product.ProductService.service.impl.OrderServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.fail;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

public class OrderServiceTest {
  @Mock private OrderRepository orderRepository;
  @InjectMocks private OrderServiceImpl orderService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  void test_GetCombinedOrders_Success() {

    // Arrange
    Pageable pageable = PageRequest.of(0, 10);
    Instant startDate = Instant.parse("2024-01-01T00:00:00Z");
    Instant endDate = Instant.parse("2024-03-30T23:59:59Z");

    Page<CombinedOrderProjection> combinedOrderPage = getCombinedOrderProjections(pageable);

    when(orderRepository.findCombinedOrders(startDate, endDate, pageable))
        .thenReturn(combinedOrderPage);

    // Act
    Page<OrderResponse> result = orderService.getCombinedOrders(startDate, endDate, pageable);

    // Assert
    assertThat(result.getTotalElements()).isEqualTo(2);
    assertThat(result.getContent().get(0).getId()).isEqualTo("1");
    assertThat(result.getContent().get(0).getQuantity()).isEqualTo("10");
    assertThat(result.getContent().get(0).getProductName()).isEqualTo("Baseball");
    assertThat(result.getContent().get(0).getOrderDate())
        .isEqualTo(Instant.parse("2024-02-21T10:15:30Z"));
    assertThat(result.getContent().get(0).getOrderStatus()).isEqualTo("Pending");
    assertThat(result.getContent().get(0).getAmount()).isEqualTo(100);

    assertThat(result.getContent().get(1).getId()).isEqualTo("2");
    assertThat(result.getContent().get(1).getQuantity()).isEqualTo("5");
    assertThat(result.getContent().get(1).getProductName()).isEqualTo("Basketball");
    assertThat(result.getContent().get(1).getOrderDate())
        .isEqualTo(Instant.parse("2024-03-20T11:25:30Z"));
    assertThat(result.getContent().get(1).getOrderStatus()).isEqualTo("Completed");
    assertThat(result.getContent().get(1).getAmount()).isEqualTo(50);
  }

  @Test
  public void testGetCombinedOrders_Failure() {
    // Arrange
    Instant startDate = Instant.parse("2024-06-01T00:00:00Z");
    Instant endDate = Instant.parse("2024-06-30T23:59:59Z");
    Pageable pageable = PageRequest.of(0, 10);

    // Simulate repository throwing an exception
    when(orderRepository.findCombinedOrders(startDate, endDate, pageable))
        .thenThrow(new RuntimeException("Database connection failed"));

    Exception exception =
        assertThrows(
            RuntimeException.class,
            () -> {
              // Act
              orderService.getCombinedOrders(startDate, endDate, pageable);
            });

    String expectedMessage =
        "Something went wrong while fetching the orders. Please contact the administrator!";
    String actualMessage = exception.getMessage();

    // Assert
    assertTrue(actualMessage.contains(expectedMessage));
  }

  private static Page<CombinedOrderProjection> getCombinedOrderProjections(Pageable pageable) {

    CombinedOrderProjection order1 =
        new CombinedOrderProjection() {
          @Override
          public String getId() {
            return "1";
          }

          @Override
          public String getQuantity() {
            return "10";
          }

          @Override
          public String getProductName() {
            return "Baseball";
          }

          @Override
          public OffsetDateTime getOrderDate() {
            return OffsetDateTime.parse("2024-02-21T10:15:30+00:00");
          }

          @Override
          public String getOrderStatus() {
            return "Pending";
          }

          @Override
          public long getAmount() {
            return 100;
          }
        };

    CombinedOrderProjection order2 =
        new CombinedOrderProjection() {
          @Override
          public String getId() {
            return "2";
          }

          @Override
          public String getQuantity() {
            return "5";
          }

          @Override
          public String getProductName() {
            return "Basketball";
          }

          @Override
          public OffsetDateTime getOrderDate() {
            return OffsetDateTime.parse("2024-03-20T11:25:30+00:00");
          }

          @Override
          public String getOrderStatus() {
            return "Completed";
          }

          @Override
          public long getAmount() {
            return 50;
          }
        };

    List<CombinedOrderProjection> orders = Arrays.asList(order1, order2);
    return new PageImpl<>(orders, pageable, orders.size());
  }
}
