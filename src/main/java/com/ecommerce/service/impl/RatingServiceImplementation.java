package com.ecommerce.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import com.ecommerce.dto.RatingDto;
import com.ecommerce.repository.ProductRepository;
import com.ecommerce.service.ProductService;
import com.ecommerce.service.RatingService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ecommerce.exception.ProductException;
import com.ecommerce.model.Product;
import com.ecommerce.model.Rating;
import com.ecommerce.model.User;
import com.ecommerce.repository.RatingRepository;
import com.ecommerce.request.RatingRequest;

@Slf4j
@Service
@AllArgsConstructor
public class RatingServiceImplementation implements RatingService {

    private RatingRepository ratingRepository;
    private ProductService productService;
    private ProductRepository productRepository;
    private static final Logger logger = LoggerFactory.getLogger(RatingServiceImplementation.class);

    @Override
    public Rating createRating(RatingRequest req, User user) throws ProductException {

        Product product = productService.findProductById(req.getProductId());

        // Declare rating variable
        Rating rating;

        // Get the rating by user
        Rating ratingByUser = ratingRepository.getRatingByUserAndProduct(user.getId(), product.getId());

        // If rating by user is not null, update the rating
        if (ratingByUser != null) {
            ratingByUser.setRating(req.getRating());
            ratingByUser.setCreatedAt(LocalDateTime.now());
            rating = ratingRepository.save(ratingByUser);
        } else {
            // Otherwise add new rating
            rating = new Rating();
            rating.setProduct(product);
            rating.setUser(user);
            rating.setRating(req.getRating());
            rating.setCreatedAt(LocalDateTime.now());
            ratingRepository.save(rating);

            // Increment totalRatings as this is a new rating from a new user
            product.setTotalRatings(product.getTotalRatings() + 1);
        }

        // Calculate and update the averageRating
        List<Rating> ratingsList = product.getRatings();
        double totalRatingSum = ratingsList.stream().mapToDouble(Rating::getRating).sum();
        double averageRating = totalRatingSum / product.getTotalRatings();

        // Round to 1 decimal place
        product.setAverageRating(Math.round(averageRating * 10) / 10.0);

        // Save the updated product with new averageRating and totalRatings
        productRepository.save(product);

        return rating;
    }

    @Override
    public RatingDto getProductsRating(Long productId) {

        // Get all the ratings of the product
        List<Rating> ratings = ratingRepository.getAllProductsRating(productId);
        int totalRatings = ratingRepository.countRatingByProductId(productId);

        // get average rating for the product
        double averageRating = ratings.stream().mapToDouble(Rating::getRating).average().orElse(0.0);

        // create the dto, set fields and send
        RatingDto ratingDto = new RatingDto();
        ratingDto.setTotalRatings(totalRatings);
        ratingDto.setAverageRating(averageRating);
        ratingDto.setRatings(ratings);
        ratingDto.setCreatedAt(LocalDateTime.now());
        return ratingDto;

    }

}
