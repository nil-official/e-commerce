package com.ecommerce.service;

import com.ecommerce.exception.ProductException;
import com.ecommerce.modal.Product;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface HomeService {

    // Getting all featured products
    List<Product> getFeaturedProducts() throws ProductException;

    // Getting the most recent products added to the catalog
    List<Product> getNewArrivals() throws ProductException;

    // Getting all discounted products
    List<Product> getExclusiveDiscounts() throws ProductException;

    // Getting all top-rated products
    List<Product> getTopRatedProducts() throws ProductException;

    // Getting all bestseller products
    List<Product> getBestSellerProducts() throws ProductException;

}
