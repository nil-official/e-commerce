package com.ecommerce.service.impl;

import com.ecommerce.exception.WishlistException;
import com.ecommerce.model.Product;
import com.ecommerce.model.Wishlist;
import com.ecommerce.model.WishlistItem;
import com.ecommerce.repository.WishlistItemRepository;
import com.ecommerce.service.WishlistItemService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

    @Override
    public void deleteWishlistItem(Long wishlistItemId) throws WishlistException {

        Optional<WishlistItem> wishlistItem = wishlistItemRepository.findById(wishlistItemId);
        if (wishlistItem.isEmpty()) {
            throw new WishlistException("WishlistItem not found with ID: " + wishlistItemId);
        }
        wishlistItemRepository.delete(wishlistItem.get());

    }

}
