package com.product.ProductService.service;

import com.product.ProductService.model.ProductRequest;
import com.product.ProductService.model.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {

  ProductResponse addProduct(ProductRequest productRequest);

  Page<ProductResponse> getAllProductByCategory(Pageable pageable, String category);
}
