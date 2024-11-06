package com.ecommerce.service;

import com.ecommerce.exception.WishlistException;
import com.ecommerce.model.*;

public interface WishlistItemService {

    public WishlistItem isWishlistItemExist(Wishlist wishlist, Product product, String size, Long userId);

    public WishlistItem createWishlistItem(WishlistItem wishlistItem);

    public void deleteWishlistItem(Long wishlistId) throws WishlistException;

}
