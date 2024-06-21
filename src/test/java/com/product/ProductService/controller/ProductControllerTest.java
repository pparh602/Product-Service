package com.product.ProductService.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.product.ProductService.ProductServiceApplication;
import com.product.ProductService.entity.Product;
import com.product.ProductService.model.ProductRequest;
import com.product.ProductService.repository.ProductRepository;
import com.product.ProductService.service.ProductService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.List;

@SpringBootTest(
        classes = ProductServiceApplication.class,
        properties = "spring.config.location=classpath:/application-test.properties",
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class ProductControllerTest{

    @Autowired private ProductService productService;
    @Autowired private ObjectMapper objectMapper;
    @Autowired private MockMvc mockMvc;
    @Autowired private ProductRepository productRepository;

    @BeforeEach
    void setUpMock() {
        Product product1 = Product.builder()
                .name("Thala Shirt")
                .description("Thala for a reason special edition")
                .tags(List.of("blue", "shirt", "slim fit"))
                .brand("Thala")
                .category("apparel")
                .build();

        Product product2 = Product.builder()
                .name("UFC Shirt")
                .description("UFC Fight Night edition")
                .tags(List.of("gold", "shirt", "slim fit"))
                .brand("UFC")
                .category("apparel")
                .build();

        Product product3 = Product.builder()
                .name("The Almanack Of Naval Ravikant")
                .description("Naval Book")
                .tags(List.of("naval", "book"))
                .brand("HarperBusiness")
                .category("books")
                .build();

        productRepository.saveAll(Arrays.asList(product1, product2, product3));

    }

    @AfterEach
    void cleanUpDb() {
        productRepository.deleteAll();
    }


    @Test
    @DisplayName("Add Product with Valid ProductRequest: Success")
    void test_Add_Product_Success() throws Exception {
        ProductRequest productRequest = ProductRequest.builder()
                .name("Red Shirt")
                .description("Red hugo boss shirt")
                .tags(List.of("red", "shirt", "slim fit"))
                .brand("Hugo Boss")
                .category("apparel")
                .build();


        mockMvc.perform(post("/v1/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value(productRequest.getName()))
                .andExpect(jsonPath("$.category").value(productRequest.getCategory()))
                .andExpect(jsonPath("$.brand").value(productRequest.getBrand()))
                .andExpect(jsonPath("$.tags[0]").value(productRequest.getTags().get(0)))
                .andExpect(jsonPath("$.description").value(productRequest.getDescription()))
                .andExpect(jsonPath("$.createdAt").exists());
    }

    @Test
    @DisplayName("Add Product with Invalid ProductRequest: Failure")
    void test_Add_Product_InvalidRequest() throws Exception {
        ProductRequest invalidProductRequest = ProductRequest.builder()
                .name("")
                .description("Red hugo boss shirt")
                .tags(List.of("red", "shirt", "slim fit"))
                .brand("Hugo Boss")
                .category("apparel")
                .build();
        mockMvc.perform(post("/v1/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidProductRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.name").value("Product name is required"));
    }

    @Test
    @DisplayName("Get Products with valid category: Success")
    void test_Search_Product_By_Category_Success() throws Exception {

        ProductRequest productRequest = ProductRequest.builder()
                .name("Lakers Shirt")
                .description("Kobe Bryant edition shirt")
                .tags(List.of("Violet", "shirt", "slim fit"))
                .brand("Mamba")
                .category("apparel")
                .build();

        productService.addProduct(productRequest);

        mockMvc.perform(get("/v1/product")
                        .param("category", "apparel")
                        .param("page", "0")
                        .param("size", "10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].name").value(productRequest.getName()))
                .andExpect(jsonPath("$.content[0].category").value(productRequest.getCategory()))
                .andExpect(jsonPath("$.content[0].brand").value(productRequest.getBrand()))
                .andExpect(jsonPath("$.content[0].tags[0]").value(productRequest.getTags().get(0)))
                .andExpect(jsonPath("$.content[0].description").value(productRequest.getDescription()))
                .andExpect(jsonPath("$.content[0].createdAt").exists());
    }

    @Test
    @DisplayName("Get Products with invalid category: Failure")
    void test_Search_ProductByCategory_InvalidCategory() throws Exception {

        mockMvc.perform(get("/v1/product")
                        .param("category", "stationary")
                        .param("page", "0")
                        .param("size", "10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.errorMessage").value("No products found for category: stationary"));
    }

    @Test
    @DisplayName("Test the order for the search product by valid category: Success")

    void test_Order_Of_Search_ProductBy_Category_Success() throws Exception {
        mockMvc.perform(get("/v1/product")
                        .param("category", "apparel")
                        .param("page", "0")
                        .param("size", "10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(2)) // Ensure correct number of results per page
                .andExpect(jsonPath("$.content[0].name").value("UFC Shirt")) // Verify sorting by createdAt
                .andExpect(jsonPath("$.content[1].name").value("Thala Shirt"));
    }
}
