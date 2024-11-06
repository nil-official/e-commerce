package com.ecommerce.controller;

import com.ecommerce.dto.ReviewDto;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.exception.ProductException;
import com.ecommerce.exception.UserException;
import com.ecommerce.model.Review;
import com.ecommerce.model.User;
import com.ecommerce.request.ReviewRequest;
import com.ecommerce.service.ReviewService;
import com.ecommerce.service.UserService;

@AllArgsConstructor
@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    private ReviewService reviewService;
    private UserService userService;

    @PostMapping("/create")
    public ResponseEntity<Review> createReviewHandler(@RequestBody ReviewRequest req, @RequestHeader("Authorization") String jwt) throws UserException, ProductException {
        User user = userService.findUserProfileByJwt(jwt);
        System.out.println("product id " + req.getProductId() + " - " + req.getReview());
        Review review = reviewService.createReview(req, user);
        System.out.println("product review " + req.getReview());
        return new ResponseEntity<Review>(review, HttpStatus.ACCEPTED);
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<ReviewDto> getProductsReviewHandler(@PathVariable Long productId) {
        ReviewDto reviews = reviewService.getAllReview(productId);
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }

}
