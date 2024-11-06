package com.ecommerce.repository;

import com.ecommerce.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface WishlistItemRepository extends JpaRepository<WishlistItem, Long> {

    @Query("SELECT wi From WishlistItem wi Where wi.wishlist=:wishlist And wi.product=:product And wi.size=:size And wi.userId=:userId")
    public WishlistItem isWishlistItemExist(@Param("wishlist") Wishlist wishlist,
                                            @Param("product") Product product,
                                            @Param("size") String size,
                                            @Param("userId") Long userId);

}
