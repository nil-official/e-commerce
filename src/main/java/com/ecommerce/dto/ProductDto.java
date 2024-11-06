package com.ecommerce.dto;

import com.ecommerce.model.Category;
import com.ecommerce.model.Rating;
import com.ecommerce.model.Review;
import com.ecommerce.model.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {

    private Long id;
    private String title;
    private String description;
    private int price;
    private int discountedPrice;
    private int discountPercent;
    private String brand;
    private String color;
    private String imageUrl;
    private Set<Size> sizes;
    private List<Review> reviews;
    private List<Rating> ratings;
    private int totalRatings;
    private double averageRating;

}
