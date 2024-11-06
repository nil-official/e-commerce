package com.ecommerce.controller;

import com.ecommerce.dto.RatingDto;
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
import com.ecommerce.model.Rating;
import com.ecommerce.model.User;
import com.ecommerce.request.RatingRequest;
import com.ecommerce.service.RatingService;
import com.ecommerce.service.UserService;

@RestController
@AllArgsConstructor
@RequestMapping("/api/ratings")
public class RatingController {

    private UserService userService;
    private RatingService ratingServices;

    @PostMapping("/create")
    public ResponseEntity<Rating> createRatingHandler(@RequestBody RatingRequest req,
                                                      @RequestHeader("Authorization") String jwt) throws UserException, ProductException {
        User user = userService.findUserProfileByJwt(jwt);
        Rating rating = ratingServices.createRating(req, user);
        return new ResponseEntity<>(rating, HttpStatus.ACCEPTED);
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<RatingDto> getProductsReviewHandler(@PathVariable Long productId) {
        RatingDto ratings = ratingServices.getProductsRating(productId);
        return new ResponseEntity<>(ratings, HttpStatus.OK);
    }

}
