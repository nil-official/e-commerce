package com.ecommerce.service;

import com.ecommerce.exception.ProductException;
import com.ecommerce.model.Rating;
import com.ecommerce.model.User;
import com.ecommerce.request.RatingRequest;
import com.ecommerce.dto.RatingDto;

public interface RatingService {
	
	public Rating createRating(RatingRequest req,User user) throws ProductException;

	public RatingDto getProductsRating(Long productId);

}
