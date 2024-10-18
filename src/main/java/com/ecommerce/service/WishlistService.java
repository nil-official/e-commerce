package com.ecommerce.service;

import com.ecommerce.dto.WishlistDto;
import com.ecommerce.exception.ProductException;
import com.ecommerce.exception.WishlistException;
import com.ecommerce.modal.Wishlist;

public interface WishlistService {

    public Wishlist findUserWishlist(Long userId) throws WishlistException;

    public void addToWishlist(Long id, WishlistDto wishlistDto) throws WishlistException;

    public void removeFromWishlist(Long userId, Long wishlistItemId) throws WishlistException;

}
