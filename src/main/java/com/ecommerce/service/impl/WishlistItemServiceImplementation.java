package com.ecommerce.service.impl;

import com.ecommerce.modal.CartItem;
import com.ecommerce.modal.Product;
import com.ecommerce.modal.Wishlist;
import com.ecommerce.modal.WishlistItem;
import com.ecommerce.repository.WishlistItemRepository;
import com.ecommerce.service.WishlistItemService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class WishlistItemServiceImplementation implements WishlistItemService {

    private WishlistItemRepository wishlistItemRepository;

    @Override
    public WishlistItem isWishlistItemExist(Wishlist wishlist, Product product, String size, Long userId) {
        return wishlistItemRepository.isWishlistItemExist(wishlist, product, size, userId);
    }

    @Override
    public WishlistItem createWishlistItem(WishlistItem wishlistItem) {
        return wishlistItemRepository.save(wishlistItem);
    }

}
