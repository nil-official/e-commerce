package com.ecommerce.repository;

import com.ecommerce.model.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface WishlistRepository extends JpaRepository<Wishlist, Long> {

    @Query("SELECT w From Wishlist w where w.user.id=:userId")
    public Wishlist findByUserId(@Param("userId") Long userId);

}
