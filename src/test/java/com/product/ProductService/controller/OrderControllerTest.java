package com.product.ProductService.controller;

import com.product.ProductService.ProductServiceApplication;
import com.product.ProductService.entity.CompletedOrder;
import com.product.ProductService.entity.PendingOrder;
import com.product.ProductService.repository.CompletedOrderRepository;
import com.product.ProductService.repository.PendingOrderRepository;
import com.product.ProductService.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(
        classes = ProductServiceApplication.class,
        properties = "spring.config.location=classpath:/application-test.properties",
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class OrderControllerTest {
  @Autowired private CompletedOrderRepository completedOrderRepository;
  @Autowired private PendingOrderRepository pendingOrderRepository;
  @Autowired private MockMvc mockMvc;
  @Autowired private OrderService orderService;

  @BeforeEach
  void setUpMock() {
    // Clean up test product db
    pendingOrderRepository.deleteAll();
    completedOrderRepository.deleteAll();

    // Adding mock data in test product db
    // Add mock data
    List<PendingOrder> pendingOrders =
        Arrays.asList(
            PendingOrder.builder()
                .quantity("3")
                .productName("Laptop")
                .orderDate(Instant.parse("2024-01-21T10:15:30Z"))
                .orderStatus("PENDING")
                .amount(50000)
                .build(),
            PendingOrder.builder()
                .quantity("4")
                .productName("Nokia 1300")
                .orderDate(Instant.parse("2024-02-21T11:15:30Z"))
                .orderStatus("PENDING")
                .amount(2400)
                .build(),
            PendingOrder.builder()
                .quantity("5")
                .productName("Charger")
                .orderDate(Instant.parse("2024-03-21T12:15:30Z"))
                .orderStatus("PENDING")
                .amount(1500)
                .build(),
            PendingOrder.builder()
                .quantity("3")
                .productName("Kindle")
                .orderDate(Instant.parse("2024-04-21T13:15:30Z"))
                .orderStatus("PENDING")
                .amount(8000)
                .build(),
            PendingOrder.builder()
                .quantity("7")
                .productName("Thala Shirt")
                .orderDate(Instant.parse("2024-07-21T21:15:30Z"))
                .orderStatus("PENDING")
                .amount(777)
                .build(),
            PendingOrder.builder()
                .quantity("3")
                .productName("Zero to One by Peter Thiel")
                .orderDate(Instant.parse("2024-05-21T14:15:30Z"))
                .orderStatus("PENDING")
                .amount(358)
                .build(),
            PendingOrder.builder()
                .quantity("5")
                .productName("Blue Shirt")
                .orderDate(Instant.parse("2024-06-21T15:15:30Z"))
                .orderStatus("PENDING")
                .amount(500)
                .build());

    List<CompletedOrder> completedOrders =
        Arrays.asList(
            CompletedOrder.builder()
                .quantity("5")
                .productName("Macbook")
                .orderDate(Instant.parse("2024-01-21T16:15:30Z"))
                .orderStatus("COMPLETED")
                .amount(895000)
                .build(),
            CompletedOrder.builder()
                .quantity("15")
                .productName("iPhone 15 Pro")
                .orderDate(Instant.parse("2024-02-21T17:15:30Z"))
                .orderStatus("COMPLETED")
                .amount(134900)
                .build(),
            CompletedOrder.builder()
                .quantity("2")
                .productName("Mobile Charger")
                .orderDate(Instant.parse("2024-03-21T18:15:30Z"))
                .orderStatus("COMPLETED")
                .amount(1200)
                .build(),
            CompletedOrder.builder()
                .quantity("5")
                .productName("Kindle")
                .orderDate(Instant.parse("2024-04-21T19:15:30Z"))
                .orderStatus("COMPLETED")
                .amount(12000)
                .build(),
            CompletedOrder.builder()
                .quantity("50")
                .productName("iWatch")
                .orderDate(Instant.parse("2024-05-21T20:15:30Z"))
                .orderStatus("COMPLETED")
                .amount(44000)
                .build(),
            CompletedOrder.builder()
                .quantity("50")
                .productName("Bat")
                .orderDate(Instant.parse("2024-05-21T21:15:30Z"))
                .orderStatus("COMPLETED")
                .amount(44000)
                .build());

    pendingOrderRepository.saveAll(pendingOrders);

    completedOrderRepository.saveAll(completedOrders);
  }

  @Test
  void test_Get_Combined_Orders_Sorted_By_Amounts_And_DESC() throws Exception {
    mockMvc
        .perform(
            get("/v1/orders/combined")
                .param("startDate", "2023-12-01")
                .param("endDate", "2024-07-01")
                .param("page", "0")
                .param("size", "10")
                .param("sortField", "amount")
                .param("isSortDesc", "true")
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.content[0].productName").value("Macbook"))
        .andExpect(jsonPath("$.content[0].amount").value(895000))
        .andExpect(jsonPath("$.content[1].productName").value("iPhone 15 Pro"))
        .andExpect(jsonPath("$.content[1].amount").value(134900));
  }

  @Test
  void test_Get_Combined_Orders_Sorted_By_Amounts_And_ASC() throws Exception {
    mockMvc
        .perform(
            get("/v1/orders/combined")
                .param("startDate", "2023-12-01")
                .param("endDate", "2024-07-01")
                .param("page", "0")
                .param("size", "10")
                .param("sortField", "amount")
                .param("isSortDesc", "false")
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.content[0].productName").value("Zero to One by Peter Thiel"))
        .andExpect(jsonPath("$.content[0].amount").value(358))
        .andExpect(jsonPath("$.content[1].productName").value("Blue Shirt"))
        .andExpect(jsonPath("$.content[1].amount").value(500));
  }

  @Test
  void test_Get_Combined_Orders_Sorted_By_OrderDate_ASC() throws Exception {
    mockMvc
        .perform(
            get("/v1/orders/combined")
                .param("startDate", "2024-05-01")
                .param("endDate", "2024-07-01")
                .param("page", "0")
                .param("size", "10")
                .param("sortField", "orderDate")
                .param("isSortDesc", "false")
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.content[0].productName").value("Zero to One by Peter Thiel"))
        .andExpect(jsonPath("$.content[0].orderDate").value("2024-05-21T14:15:30Z"))
        .andExpect(jsonPath("$.content[1].productName").value("iWatch"))
        .andExpect(jsonPath("$.content[1].orderDate").value("2024-05-21T20:15:30Z"));
  }

  @Test
  void test_Get_Combined_Orders_Sorted_By_OrderDate_DESC() throws Exception {
    mockMvc
        .perform(
            get("/v1/orders/combined")
                .param("startDate", "2024-05-01")
                .param("endDate", "2024-07-31")
                .param("page", "0")
                .param("size", "10")
                .param("sortField", "orderDate")
                .param("isSortDesc", "true")
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.content[0].productName").value("Thala Shirt"))
        .andExpect(jsonPath("$.content[0].orderDate").value("2024-07-21T21:15:30Z"))
        .andExpect(jsonPath("$.content[1].productName").value("Blue Shirt"))
        .andExpect(jsonPath("$.content[1].orderDate").value("2024-06-21T15:15:30Z"));
  }

  @Test
  void test_Get_Combined_Orders_With_Invalid_Page_Request() throws Exception {
    mockMvc
        .perform(
            get("/v1/orders/combined")
                .param("startDate", "2024-05-01")
                .param("endDate", "2024-07-31")
                .param("page", "5555")
                .param("size", "10")
                .param("sortField", "orderDate")
                .param("isSortDesc", "true")
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.content").isEmpty());
  }
}
