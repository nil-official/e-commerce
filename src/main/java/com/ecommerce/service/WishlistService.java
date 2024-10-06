package com.ecommerce.service;

import com.ecommerce.dto.WishlistDto;
import com.ecommerce.exception.ProductException;
import com.ecommerce.modal.Wishlist;

public interface WishlistService {

    public Wishlist findUserWishlist(Long userId) throws ProductException;

    public void addToWishlist(Long id, WishlistDto wishlistDto) throws ProductException;

}
