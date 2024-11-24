package com.ecommerce.mapper;

import com.ecommerce.dto.WishlistDto;
import com.ecommerce.model.Wishlist;

public class WishlistMapper {
    public static WishlistDto toWishlistDto(Wishlist wishlist) {
        WishlistDto wishlistDto = new WishlistDto();
        wishlistDto.setId(wishlist.getId());
        wishlistDto.setUserDto(UserMapper.toUserDto(wishlist.getUser()));
        wishlistDto.setWishlistItems(wishlist.getWishlistItems());
        wishlistDto.setCreatedAt(wishlist.getCreatedAt());
        return wishlistDto;
    }
}
