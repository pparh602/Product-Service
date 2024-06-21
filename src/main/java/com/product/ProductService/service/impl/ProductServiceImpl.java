package com.product.ProductService.service.impl;

import com.product.ProductService.entity.Product;
import com.product.ProductService.exception.ProductServiceCustomException;
import com.product.ProductService.model.ProductRequest;
import com.product.ProductService.model.ProductResponse;
import com.product.ProductService.repository.ProductRepository;
import com.product.ProductService.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.beans.BeanUtils.copyProperties;

@Service
public class ProductServiceImpl implements ProductService {

  @Autowired ProductRepository productRepository;

  @Override
  public ProductResponse addProduct(ProductRequest productRequest) {
    Product product =
        Product.builder()
            .name(productRequest.getName())
            .category(productRequest.getCategory())
            .brand(productRequest.getBrand())
            .tags(productRequest.getTags())
            .description(productRequest.getDescription())
            .createdAt(productRequest.getCreatedAt())
            .build();
    try {
      Product savedProduct = productRepository.save(product);
      ProductResponse productResponse = new ProductResponse();
      copyProperties(savedProduct, productResponse);

      return productResponse;

    } catch (Exception e) {
      throw new ProductServiceCustomException(
          "Something went wrong while saving the product",
          HttpStatus.INTERNAL_SERVER_ERROR.toString());
    }
  }

  @Override
  public Page<ProductResponse> getAllProductByCategory(Pageable pageable, String category) {

    Page<Product> productPage =
        productRepository.findByCategoryOrderByCreatedAtDesc(category, pageable);

    if (productPage.isEmpty()) {
      throw new ProductServiceCustomException(
          "No products found for category: " + category, HttpStatus.NOT_FOUND.toString());
    }

    List<ProductResponse> productResponses =
        productPage.stream().map(this::convertToProductResponse).toList();

    return new PageImpl<>(productResponses, pageable, productPage.getTotalElements());
  }

  private ProductResponse convertToProductResponse(Product product) {
    ProductResponse productResponse = new ProductResponse();
    productResponse.setId(product.getId());
    productResponse.setName(product.getName());
    productResponse.setDescription(product.getDescription());
    productResponse.setBrand(product.getBrand());
    productResponse.setTags(product.getTags());
    productResponse.setCategory(product.getCategory());
    productResponse.setCreatedAt(product.getCreatedAt());
    return productResponse;
  }
}
