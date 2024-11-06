package com.ecommerce.service.impl;

import com.ecommerce.dto.ProductDto;
import com.ecommerce.exception.ProductException;
import com.ecommerce.mapper.ProductMapper;
import com.ecommerce.model.Product;
import com.ecommerce.repository.OrderItemRepository;
import com.ecommerce.repository.ProductRepository;
import com.ecommerce.service.HomeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class HomeServiceImpl implements HomeService {

    private final ProductRepository productRepository;
    private final OrderItemRepository orderItemRepository;

    @Override
    public List<ProductDto> getFeaturedProducts() throws ProductException {
        try {
            List<Product> featuredProducts = productRepository.findByIsFeaturedTrue();
            return ProductMapper.toDtoList(featuredProducts);
        } catch (Exception e) {
            throw new ProductException("Error while fetching featured products: " + e.getMessage());
        }
    }

    @Override
    public List<ProductDto> getNewArrivals() throws ProductException {
        try {
            List<Product> newArrivals = productRepository.findNewArrivals(LocalDateTime.now().minusWeeks(1));
            return ProductMapper.toDtoList(newArrivals);
        } catch (Exception e) {
            throw new ProductException("Error while fetching new arrivals: " + e.getMessage());
        }
    }

    @Override
    public List<ProductDto> getExclusiveDiscounts() throws ProductException {
        try {
            List<Product> discountedProducts = productRepository.findDiscountedProducts();
            return ProductMapper.toDtoList(discountedProducts);
        } catch (Exception e) {
            throw new ProductException("Error while fetching discounted products: " + e.getMessage());
        }
    }

    @Override
    public List<ProductDto> getTopRatedProducts() throws ProductException {
        try {
            List<Product> topRatedProducts = productRepository.findTopRatedProducts();
            return ProductMapper.toDtoList(topRatedProducts);
        } catch (Exception e) {
            throw new ProductException("Error while fetching top rated products: " + e.getMessage());
        }
    }

    @Override
    public List<ProductDto> getBestSellerProducts() throws ProductException {
        try {
            List<Product> bestSellerProducts = orderItemRepository.findBestSellerProducts()
                    .stream()
                    .map(result -> (Product) result[0])
                    .collect(Collectors.toList());
            return ProductMapper.toDtoList(bestSellerProducts);
        } catch (Exception e) {
            throw new ProductException("Error while fetching best seller products: " + e.getMessage());
        }
    }

}
