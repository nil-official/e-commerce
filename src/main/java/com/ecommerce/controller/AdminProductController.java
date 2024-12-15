package com.ecommerce.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ecommerce.exception.ProductException;
import com.ecommerce.model.Product;
import com.ecommerce.request.ProductRequest;
import com.ecommerce.response.ApiResponse;
import com.ecommerce.service.ProductService;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api/admin/products")
public class AdminProductController {

    private ProductService productService;

    @PostMapping("/")
    public ResponseEntity<Product> createProductHandler(@RequestBody ProductRequest req) throws ProductException {

        Product createdProduct = productService.createProduct(req);
        return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);

    }

    @PostMapping("/creates")
    public ResponseEntity<ApiResponse> createMultipleProductHandler(@RequestBody ProductRequest[] reqs) throws RuntimeException {

        List<String> failedProducts = new ArrayList<>();

        Arrays.stream(reqs).forEach(product -> {
            try {
                productService.createProduct(product);
            } catch (ProductException e) {
                failedProducts.add(product.getTitle()); // Log the failed product title or ID
                log.error("Failed to create product: {}", product.getTitle());
                System.out.println("Failed to create product: {}" + product.getTitle());
            }
        });

        String message = failedProducts.isEmpty()
                ? "Products created successfully"
                : "Some products could not be created: " + String.join(", ", failedProducts);

        ApiResponse res = new ApiResponse(message, failedProducts.isEmpty());
        return new ResponseEntity<>(res, HttpStatus.CREATED);

    }

    @DeleteMapping("/{productId}/delete")
    public ResponseEntity<ApiResponse> deleteProductHandler(@PathVariable Long productId) throws ProductException {

        String msg = productService.deleteProduct(productId);
        ApiResponse res = new ApiResponse(msg, true);
        return new ResponseEntity<>(res, HttpStatus.ACCEPTED);

    }

    @GetMapping("/all")
    public ResponseEntity<Page<Product>> findAllProductHandler(@RequestParam Integer pageNumber, @RequestParam Integer pageSize) {

        Page<Product> products = productService.getAllProducts(pageNumber, pageSize);
        return new ResponseEntity<>(products, HttpStatus.OK);

    }

    @PutMapping("/{productId}/update")
    public ResponseEntity<Product> fullUpdateProductHandler(@PathVariable Long productId, @RequestBody ProductRequest req)
            throws ProductException {

        Product updatedProduct = productService.fullUpdate(productId, req);
        return new ResponseEntity<>(updatedProduct, HttpStatus.OK);

    }

    @PatchMapping("/{productId}/updates")
    public ResponseEntity<Product> partialUpdateProductHandler(@PathVariable Long productId, @RequestBody ProductRequest req)
            throws ProductException {

        Product updatedProduct = productService.partialUpdate(productId, req);
        return new ResponseEntity<>(updatedProduct, HttpStatus.OK);

    }

}
