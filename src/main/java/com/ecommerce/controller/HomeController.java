package com.ecommerce.controller;

import com.ecommerce.exception.ProductException;
import com.ecommerce.modal.Product;
import com.ecommerce.service.HomeService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.response.ApiResponse;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/public")
public class HomeController {

    private final HomeService homeService;

    @GetMapping("/")
    public ResponseEntity<ApiResponse> welcome() {
        ApiResponse res = new ApiResponse("Welcome To E-Commerce Backend", true);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping("/featured-products")
    public ResponseEntity<List<Product>> getFeaturedProducts() throws ProductException {
        List<Product> featuredProducts = homeService.getFeaturedProducts();
        return new ResponseEntity<>(featuredProducts, HttpStatus.OK);
    }

    @GetMapping("/new-arrivals")
    public ResponseEntity<List<Product>> getNewArrivals() throws ProductException {
        List<Product> newArrivals = homeService.getNewArrivals();
        return new ResponseEntity<>(newArrivals, HttpStatus.OK);
    }

    @GetMapping("/exclusive-discounts")
    public ResponseEntity<List<Product>> getExclusive() throws ProductException {
        List<Product> discountedProducts = homeService.getExclusiveDiscounts();
        return new ResponseEntity<>(discountedProducts, HttpStatus.OK);
    }

    @GetMapping("/top-rated")
    public ResponseEntity<List<Product>> getTopRated() throws ProductException {
        List<Product> topRatedProducts = homeService.getTopRatedProducts();
        return new ResponseEntity<>(topRatedProducts, HttpStatus.OK);
    }

    @GetMapping("/best-seller")
    public ResponseEntity<List<Product>> getBestSeller() throws ProductException {
        List<Product> bestSellerProducts = homeService.getBestSellerProducts();
        return new ResponseEntity<>(bestSellerProducts, HttpStatus.OK);
    }

}
