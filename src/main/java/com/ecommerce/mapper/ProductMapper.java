package com.ecommerce.mapper;

import com.ecommerce.dto.ProductDto;
import com.ecommerce.model.Product;

import java.util.List;
import java.util.stream.Collectors;

public class ProductMapper {

    public static ProductDto toDto(Product product) {
        ProductDto dto = new ProductDto();
        dto.setId(product.getId());
        dto.setTitle(product.getTitle());
        dto.setDescription(product.getDescription());
        dto.setPrice(product.getPrice());
        dto.setDiscountedPrice(product.getDiscountedPrice());
        dto.setDiscountPercent(product.getDiscountPercent());
        dto.setBrand(product.getBrand());
        dto.setColor(product.getColor());
        dto.setImageUrl(product.getImageUrl());
        dto.setSizes(product.getSizes());
        dto.setReviews(product.getReviews());
        dto.setRatings(product.getRatings());
        dto.setTotalRatings(product.getTotalRatings());
        dto.setAverageRating(product.getAverageRating());
        return dto;
    }

    public static List<ProductDto> toDtoList(List<Product> products) {
        return products.stream()
                .map(ProductMapper::toDto)
                .collect(Collectors.toList());
    }

}
