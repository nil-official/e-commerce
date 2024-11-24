package com.ecommerce.service;

import com.ecommerce.dto.WishlistDto;
import com.ecommerce.exception.WishlistException;
import com.ecommerce.model.Wishlist;
import com.ecommerce.request.AddToWishlistRequest;

public interface WishlistService {

    public Wishlist findUserWishlist(Long userId) throws WishlistException;

    public void addToWishlist(Long id, AddToWishlistRequest addToWishlistRequest) throws WishlistException;

    public void removeFromWishlist(Long userId, Long wishlistItemId) throws WishlistException;

}
