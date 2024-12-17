package com.ecommerce.service;

import com.ecommerce.exception.CartException;
import com.ecommerce.exception.CartItemException;
import com.ecommerce.exception.ProductException;
import com.ecommerce.model.Cart;
import com.ecommerce.model.User;
import com.ecommerce.request.AddToCartRequest;

public interface CartService {
	
	void createCart(User user);
	
	Cart findCart(Long userId) throws CartException;

	void addToCart(Long userId, AddToCartRequest req) throws ProductException, CartException, CartItemException;

	void clearCart(Long userId) throws CartException;

}
