package com.ecommerce.service;

import com.ecommerce.dto.ProductDto;
import com.ecommerce.exception.ProductException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface HomeService {

    // Getting all featured products
    List<ProductDto> getFeaturedProducts() throws ProductException;

    // Getting the most recent products added to the catalog
    List<ProductDto> getNewArrivals() throws ProductException;

    // Getting all discounted products
    List<ProductDto> getExclusiveDiscounts() throws ProductException;

    // Getting all top-rated products
    List<ProductDto> getTopRatedProducts() throws ProductException;

    // Getting all bestseller products
    List<ProductDto> getBestSellerProducts() throws ProductException;

}
