package com.ecommerce.service;

import com.ecommerce.exception.ProductException;
import com.ecommerce.modal.Rating;
import com.ecommerce.modal.User;
import com.ecommerce.request.RatingRequest;
import com.ecommerce.dto.RatingDto;

public interface RatingService {
	
	public Rating createRating(RatingRequest req,User user) throws ProductException;

	public RatingDto getProductsRating(Long productId);

}
