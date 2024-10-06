package com.ecommerce.service;

import com.ecommerce.modal.*;

public interface WishlistItemService {

    public WishlistItem isWishlistItemExist(Wishlist wishlist, Product product, String size, Long userId);

    public WishlistItem createWishlistItem(WishlistItem wishlistItem);

}
