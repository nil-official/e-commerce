package com.ecommerce.service.impl;

import com.ecommerce.dto.WishlistDto;
import com.ecommerce.exception.ProductException;
import com.ecommerce.exception.WishlistException;
import com.ecommerce.model.*;
import com.ecommerce.repository.WishlistRepository;
import com.ecommerce.request.AddToWishlistRequest;
import com.ecommerce.service.ProductService;
import com.ecommerce.service.WishlistItemService;
import com.ecommerce.service.WishlistService;
import com.ecommerce.user.domain.ProductSize;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class WishlistServiceImplementation implements WishlistService {

    private WishlistRepository wishlistRepository;
    private ProductService productService;
    private WishlistItemService wishlistItemService;

    @Override
    public Wishlist findUserWishlist(Long userId) throws WishlistException {
        Wishlist wishlist = wishlistRepository.findByUserId(userId);
        if (wishlist == null) {
            throw new WishlistException("Wishlist not found for user with ID: " + userId);
        }
        return wishlist;
    }

    @Override
    public void addToWishlist(Long userId, AddToWishlistRequest addToWishlistRequest) throws WishlistException {
        try {
            // Check if the size is provided in the request body
            if (addToWishlistRequest.getSize() == null || addToWishlistRequest.getSize().isEmpty()) {
                throw new ProductException("Size cannot be null or empty.");
            }

            // Find the product to be added
            Product product = productService.findProductById(addToWishlistRequest.getProductId());

            // Validate the size against available sizes
            if (product.getSizes() == null || product.getSizes().isEmpty()) {
                throw new ProductException("No sizes available for this product.");
            }

            // Validate if the requested size is available
            ProductSize requestedSize;
            try {
                requestedSize = ProductSize.valueOf(addToWishlistRequest.getSize().toUpperCase()); // Convert to enum
            } catch (IllegalArgumentException e) {
                throw new ProductException("Invalid size: " + addToWishlistRequest.getSize());
            }

            boolean sizeAvailable = product.getSizes().stream()
                    .anyMatch(size -> size.getName() == requestedSize && size.getQuantity() > 0);

            if (!sizeAvailable) {
                throw new ProductException("Size " + addToWishlistRequest.getSize() + " is not available for this product.");
            }

            // Find the user's wishlist or create a new one if it doesn't exist
            Wishlist wishlist = wishlistRepository.findByUserId(userId);
            if (wishlist == null) {
                wishlist = new Wishlist();
                User user = new User();
                user.setId(userId);
                wishlist.setUser(user);
                wishlist.setCreatedAt(LocalDateTime.now());
                wishlist = wishlistRepository.save(wishlist); // Save the new wishlist
            }

            // Check if the wishlist already contains the product with the specified size
            WishlistItem isPresent = wishlistItemService.isWishlistItemExist(wishlist, product, requestedSize.name(), userId);

            if (isPresent == null) {
                // If the item is not in the wishlist, create a new WishlistItem
                WishlistItem wishlistItem = new WishlistItem();
                wishlistItem.setWishlist(wishlist);
                wishlistItem.setProduct(product);
                wishlistItem.setSize(requestedSize.name()); // Use the name of the ProductSize enum
                wishlistItem.setUserId(userId);

                // Save and add the new WishlistItem to the wishlist
                WishlistItem createdWishlistItem = wishlistItemService.createWishlistItem(wishlistItem);
                wishlist.getWishlistItems().add(createdWishlistItem);
            } else {
                throw new ProductException("Product already exists in the wishlist with the selected size.");
            }
        } catch (Exception e) {
            throw new WishlistException("Failed to add to the wishlist: " + e.getMessage());
        }
    }

    @Override
    public void removeFromWishlist(Long userId, Long wishlistItemId) throws WishlistException {

        Wishlist wishlist = wishlistRepository.findByUserId(userId);
        if (wishlist == null) {
            throw new WishlistException("Wishlist not found for user with ID: " + userId);
        }
        wishlistItemService.deleteWishlistItem(wishlistItemId);

    }

}
