package com.ecommerce.service;

import com.ecommerce.exception.ProductException;
import com.ecommerce.modal.Product;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface HomeService {

    // Getting all featured products
    public List<Product> getFeaturedProducts() throws ProductException;

    // Getting the most recent products added to the catalog
    public List<Product> getNewArrivals() throws ProductException;

    // Getting all discounted products
    public List<Product> getExclusiveDiscounts() throws ProductException;

}
