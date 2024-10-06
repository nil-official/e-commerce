package com.ecommerce.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import com.ecommerce.dto.RatingDto;
import com.ecommerce.service.ProductService;
import com.ecommerce.service.RatingService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ecommerce.exception.ProductException;
import com.ecommerce.modal.Product;
import com.ecommerce.modal.Rating;
import com.ecommerce.modal.User;
import com.ecommerce.repository.RatingRepository;
import com.ecommerce.request.RatingRequest;

@Slf4j
@Service
@AllArgsConstructor
public class RatingServiceImplementation implements RatingService {

    private RatingRepository ratingRepository;
    private ProductService productService;
    private static final Logger logger = LoggerFactory.getLogger(RatingServiceImplementation.class);

    @Override
    public Rating createRating(RatingRequest req, User user) throws ProductException {

        Product product = productService.findProductById(req.getProductId());

        // get the rating by user
        Rating ratingByUser = ratingRepository.getRatingByUserAndProduct(user.getId(), product.getId());
        logger.info("User id: " + user.getId());
        logger.info("Product id: " + product.getId());
        logger.info("Rating by user: " + ratingByUser);

        // if rating by user is not null then update the rating
        if (ratingByUser != null) {
            ratingByUser.setRating(req.getRating());
            ratingByUser.setCreatedAt(LocalDateTime.now());
            return ratingRepository.save(ratingByUser);
        }

        // otherwise add new rating
        Rating rating = new Rating();
        rating.setProduct(product);
        rating.setUser(user);
        rating.setRating(req.getRating());
        rating.setCreatedAt(LocalDateTime.now());

        return ratingRepository.save(rating);
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
