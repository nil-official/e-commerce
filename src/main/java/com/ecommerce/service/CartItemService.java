package com.ecommerce.service;

import com.ecommerce.exception.CartItemException;
import com.ecommerce.exception.UserException;
import com.ecommerce.model.Cart;
import com.ecommerce.model.CartItem;
import com.ecommerce.model.Product;

public interface CartItemService {
	
	CartItem createCartItem(CartItem cartItem);
	
	CartItem updateCartItem(Long userId, Long id,CartItem cartItem) throws CartItemException, UserException;
	
	CartItem isCartItemExist(Cart cart,Product product,String size, Long userId);
	
	void removeCartItem(Long userId,Long cartItemId) throws CartItemException, UserException;
	
	CartItem findCartItemById(Long cartItemId) throws CartItemException;
	
}
