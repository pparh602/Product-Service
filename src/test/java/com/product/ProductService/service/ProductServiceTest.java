package com.product.ProductService.service;


import com.product.ProductService.entity.Product;
import com.product.ProductService.exception.ProductServiceCustomException;
import com.product.ProductService.model.ProductRequest;
import com.product.ProductService.model.ProductResponse;
import com.product.ProductService.repository.ProductRepository;
import com.product.ProductService.service.impl.ProductServiceImpl;
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
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ProductServiceTest {

    @Mock private ProductRepository productRepository;
    @InjectMocks private ProductServiceImpl productService;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void test_AddProduct_Success() {
        // Arrange
        ProductRequest mockProductRequest = ProductRequest.builder()
                .name("Red Shirt")
                .description("Red hugo boss shirt")
                .tags(List.of("red", "shirt", "slim fit"))
                .brand("Hugo Boss")
                .category("apparel")
                .build();

        Product mockExpectedProduct = Product
                .builder()
                .id("123")
                .name(mockProductRequest.getName())
                .description(mockProductRequest.getDescription())
                .brand(mockProductRequest.getBrand())
                .tags(mockProductRequest.getTags())
                .category(mockProductRequest.getCategory())
                .createdAt(mockProductRequest.getCreatedAt())
                .build();

        when(productRepository.save(any(Product.class))).thenReturn(mockExpectedProduct);

        // Act
        ProductResponse actualProductResponse = productService.addProduct(mockProductRequest);

        // Assert the Response
        verify(productRepository, times(1)).save(any());
        assertEquals(mockProductRequest.getName(), actualProductResponse.getName());
        assertEquals(mockProductRequest.getCategory(), actualProductResponse.getCategory());
        assertEquals(mockProductRequest.getBrand(), actualProductResponse.getBrand());
        assertEquals(mockProductRequest.getTags(), actualProductResponse.getTags());
        assertEquals(mockProductRequest.getDescription(), actualProductResponse.getDescription());
        assertEquals(mockProductRequest.getCreatedAt(), actualProductResponse.getCreatedAt());

    }

    @Test
    void test_AddProduct_Failure() {
        // Arrange
        ProductRequest mockProductRequest = ProductRequest.builder()
                .name("Red Shirt")
                .description("Red hugo boss shirt")
                .tags(List.of("red", "shirt", "slim fit"))
                .brand("Hugo Boss")
                .category("apparel")
                .build();

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            // Act
            productService.addProduct(mockProductRequest);
        });
        // Assert the Response
        assertEquals("Something went wrong while saving the product", exception.getMessage());
    }

    @Test
    void test_GetAllProductByCategory_Success() {
        // Arrange
        Pageable pageable = PageRequest.of(0, 10);
        String category = "Test Category";

        Product mockExpectedProduct = Product
                .builder()
                .id("123")
                .name("Red Shirt")
                .description("Red hugo boss shirt")
                .brand("Hugo Boss")
                .tags(List.of("red", "shirt", "slim fit"))
                .category("apparel")
                .createdAt(Instant.now())
                .build();

        Page<Product> mockExpectedProductPage = new PageImpl<>(Collections.singletonList(mockExpectedProduct), pageable, 1);
        when(productRepository.findByCategoryOrderByCreatedAtDesc(eq(category), any(Pageable.class))).thenReturn(mockExpectedProductPage);

        // Act
        Page<ProductResponse> response = productService.getAllProductByCategory(pageable, category);

        // Assert the Response
        assertNotNull(response);
        assertEquals(1, response.getTotalElements());
        assertEquals(1, response.getContent().size());
        assertEquals("123", response.getContent().get(0).getId());
        assertEquals("Red Shirt", response.getContent().get(0).getName());
    }

    @Test
    void test_GetAllProductByCategory_InvalidCategory_Failure() {
        // Arrange
        Pageable pageable = PageRequest.of(0, 10);
        String invalidCategory = "caefw"; // Invalid category (empty)

        when(productRepository.findByCategoryOrderByCreatedAtDesc(eq(invalidCategory), any(Pageable.class)))
                .thenReturn(Page.empty(pageable));

        ProductServiceCustomException exception = assertThrows(ProductServiceCustomException.class, () -> {
            // Act
            productService.getAllProductByCategory(pageable, invalidCategory);
        });
        // Assert the Response
        assertEquals("No products found for category: " + invalidCategory, exception.getMessage());
    }




}
