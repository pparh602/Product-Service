package com.product.ProductService.loader;

import com.product.ProductService.entity.CompletedOrder;
import com.product.ProductService.entity.PendingOrder;
import com.product.ProductService.repository.CompletedOrderRepository;
import com.product.ProductService.repository.PendingOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Arrays;

@Configuration
public class DataLoader implements CommandLineRunner {

  private final CompletedOrderRepository completedOrderRepository;
  private final PendingOrderRepository pendingOrderRepository;

  @Autowired
  public DataLoader(CompletedOrderRepository completedOrderRepository, PendingOrderRepository pendingOrderRepository) {
    this.pendingOrderRepository = pendingOrderRepository;
    this.completedOrderRepository = completedOrderRepository;
  }

  @Override
  public void run(String... args) throws Exception {
    PendingOrder pendingOrder1 =
        PendingOrder.builder()
            .quantity("3")
            .productName("Laptop")
            .orderDate(Instant.parse("2024-01-21T10:15:30Z"))
            .orderStatus("PENDING")
            .amount(50000)
            .build();

    PendingOrder pendingOrder2 =
        PendingOrder.builder()
            .quantity("4")
            .productName("Mobile")
            .orderDate(Instant.parse("2024-02-21T10:15:30Z"))
            .orderStatus("PENDING")
            .amount(500)
            .build();

    PendingOrder pendingOrder3 =
        PendingOrder.builder()
            .quantity("5")
            .productName("Charger")
            .orderDate(Instant.parse("2024-02-21T10:15:30Z"))
            .orderStatus("PENDING")
            .amount(1500)
            .build();

    PendingOrder pendingOrder4 =
        PendingOrder.builder()
            .quantity("3")
            .productName("Kindle")
            .orderDate(Instant.parse("2024-02-21T10:15:30Z"))
            .orderStatus("PENDING")
            .amount(8000)
            .build();

    PendingOrder pendingOrder5 =
        PendingOrder.builder()
            .quantity("3")
            .productName("Zero to One by Peter Thiel")
            .orderDate(Instant.parse("2024-02-21T10:15:30Z"))
            .orderStatus("PENDING")
            .amount(358)
            .build();

    PendingOrder pendingOrder6 =
        PendingOrder.builder()
            .quantity("5")
            .productName("Blue Shirt")
            .orderDate(Instant.parse("2024-01-21T10:15:30Z"))
            .orderStatus("PENDING")
            .amount(500)
            .build();

    PendingOrder pendingOrder7 =
        PendingOrder.builder()
            .quantity("7")
            .productName("Thala Shirt")
            .orderDate(Instant.parse("2024-02-21T10:15:30Z"))
            .orderStatus("PENDING")
            .amount(777)
            .build();

    CompletedOrder completedOrder1 =
        CompletedOrder.builder()
            .quantity("5")
            .productName("Macbook")
            .orderDate(Instant.parse("2024-02-21T10:15:30Z"))
            .orderStatus("COMPLETED")
            .amount(895000)
            .build();

    CompletedOrder completedOrder2 =
        CompletedOrder.builder()
            .quantity("15")
            .productName("iPhone 15 Pro")
            .orderDate(Instant.parse("2024-02-21T10:15:30Z"))
            .orderStatus("COMPLETED")
            .amount(94000)
            .build();

    CompletedOrder completedOrder3 =
        CompletedOrder.builder()
            .quantity("2")
            .productName("Charger")
            .orderDate(Instant.parse("2024-02-21T10:15:30Z"))
            .orderStatus("COMPLETED")
            .amount(1200)
            .build();

    CompletedOrder completedOrder4 =
        CompletedOrder.builder()
            .quantity("5")
            .productName("Kindle")
            .orderDate(Instant.parse("2024-04-21T10:15:30Z"))
            .orderStatus("COMPLETED")
            .amount(12000)
            .build();

    CompletedOrder completedOrder5 =
        CompletedOrder.builder()
            .quantity("50")
            .productName("iWatch")
            .orderDate(Instant.parse("2024-02-21T10:15:30Z"))
            .orderStatus("COMPLETED")
            .amount(44000)
            .build();

    CompletedOrder completedOrder6 =
        CompletedOrder.builder()
            .quantity("50")
            .productName("Bat")
            .orderDate(Instant.parse("2024-02-21T10:15:30Z"))
            .orderStatus("COMPLETED")
            .amount(44000)
            .build();

    pendingOrderRepository.saveAll(
        Arrays.asList(
            pendingOrder1,
            pendingOrder2,
            pendingOrder3,
            pendingOrder4,
            pendingOrder5,
            pendingOrder6,
            pendingOrder7));

    completedOrderRepository.saveAll(
        Arrays.asList(
            completedOrder1,
            completedOrder2,
            completedOrder3,
            completedOrder4,
            completedOrder5,
            completedOrder6));
  }
}
