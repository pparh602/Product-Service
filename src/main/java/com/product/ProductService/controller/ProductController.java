package com.product.ProductService.controller;

import com.product.ProductService.model.ProductRequest;
import com.product.ProductService.model.ProductResponse;
import com.product.ProductService.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/v1/product")
public class ProductController {

  @Autowired private ProductService productService;

  @PostMapping
  public ResponseEntity<?> addProduct(
      @Valid @RequestBody ProductRequest productRequest, BindingResult result) {

    if (result.hasErrors()) {
      Map<String, String> errors = new HashMap<>();

      for (FieldError fieldError : result.getFieldErrors()) {
        errors.put(fieldError.getField(), fieldError.getDefaultMessage());
      }

      return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
    ProductResponse productResponse = productService.addProduct(productRequest);
    return new ResponseEntity<>(productResponse, HttpStatus.CREATED);
  }

  @GetMapping
  ResponseEntity<Page<ProductResponse>> searchProductByCategory(
      @RequestParam(name = "category", required = true) String category,
      @RequestParam(name = "page", defaultValue = "0", required = false) int page,
      @RequestParam(name = "size", defaultValue = "10", required = false) int size) {
    Pageable pageable = PageRequest.of(page, size);
    Page<ProductResponse> resultProducts =
        productService.getAllProductByCategory(pageable, category);
    return new ResponseEntity<>(resultProducts, HttpStatus.OK);
  }
}
