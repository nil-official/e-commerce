package com.ecommerce.service.impl;

import com.ecommerce.exception.ProductException;
import com.ecommerce.modal.Product;
import com.ecommerce.repository.ProductRepository;
import com.ecommerce.service.HomeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class HomeServiceImpl implements HomeService {

    private final ProductRepository productRepository;

    @Override
    public List<Product> getFeaturedProducts() throws ProductException {
        try {
            return productRepository.findByIsFeaturedTrue();
        } catch (Exception e) {
            throw new ProductException("Error while fetching featured products: " + e.getMessage());
        }
    }

    @Override
    public List<Product> getNewArrivals() throws ProductException {
        try {
            return productRepository.findNewArrivals(LocalDateTime.now().minusWeeks(1));
        } catch (Exception e) {
            throw new ProductException("Error while fetching new arrivals: " + e.getMessage());
        }
    }

    @Override
    public List<Product> getExclusiveDiscounts() throws ProductException {
        try {
            return productRepository.findDiscountedProducts();
        } catch (Exception e) {
            throw new ProductException("Error while fetching discounted products: " + e.getMessage());
        }
    }

    @Override
    public List<Product> getTopRatedProducts() throws ProductException {
        try {
            return productRepository.findTopRatedProducts();
        } catch (Exception e) {
            throw new ProductException("Error while fetching top rated products: " + e.getMessage());
        }
    }

}
