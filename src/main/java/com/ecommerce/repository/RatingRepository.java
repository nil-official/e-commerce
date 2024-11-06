package com.ecommerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ecommerce.model.Rating;

public interface RatingRepository extends JpaRepository<Rating, Long> {
	
	@Query("Select r From Rating r where r.product.id=:productId")
	public List<Rating> getAllProductsRating(@Param("productId") Long productId);

	@Query("Select r From Rating r where r.user.id=:userId and r.product.id=:productId")
	public Rating getRatingByUserAndProduct(@Param("userId") Long userId, @Param("productId") Long productId);

	@Query("Select count(r) from Rating r where r.product.id=:productId")
	public int countRatingByProductId(Long productId);

}
