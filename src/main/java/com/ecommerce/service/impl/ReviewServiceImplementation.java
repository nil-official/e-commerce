package com.ecommerce.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import com.ecommerce.dto.ReviewDto;
import com.ecommerce.service.ProductService;
import com.ecommerce.service.ReviewService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ecommerce.exception.ProductException;
import com.ecommerce.model.Product;
import com.ecommerce.model.Review;
import com.ecommerce.model.User;
import com.ecommerce.repository.ProductRepository;
import com.ecommerce.repository.ReviewRepository;
import com.ecommerce.request.ReviewRequest;

@Service
@AllArgsConstructor
public class ReviewServiceImplementation implements ReviewService {

    private ReviewRepository reviewRepository;
    private ProductService productService;
    private ProductRepository productRepository;
    private static final Logger logger = LoggerFactory.getLogger(ReviewServiceImplementation.class);

    @Override
    public Review createReview(ReviewRequest req, User user) throws ProductException {

        // Getting the product
        Product product = productService.findProductById(req.getProductId());
        // Getting the review by user
        Review reviewByUser = reviewRepository.getReviewByUserAndProduct(user.getId(), product.getId());

        // If review by user is not null then update the review
        if (reviewByUser != null) {
            reviewByUser.setReview(req.getReview());
            reviewByUser.setCreatedAt(LocalDateTime.now());
            return reviewRepository.save(reviewByUser);
        }

        // Otherwise add new review
        Review review = new Review();
        review.setUser(user);
        review.setProduct(product);
        review.setReview(req.getReview());
        review.setCreatedAt(LocalDateTime.now());
        productRepository.save(product);

        return reviewRepository.save(review);

    }

    @Override
    public ReviewDto getAllReview(Long productId) {

        // Get all the ratings of the product
        List<Review> reviews = reviewRepository.getAllProductsReview(productId);
        int totalReviews = reviewRepository.countReviewByProductId(productId);

        // create the dto, set fields and send
        ReviewDto reviewDto = new ReviewDto();
        reviewDto.setTotalReviews(totalReviews);
        reviewDto.setReviews(reviews);
        reviewDto.setCreatedAt(LocalDateTime.now());
        return reviewDto;

    }

}
