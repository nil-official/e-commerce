package com.ecommerce.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ecommerce.modal.Product;
import com.ecommerce.user.domain.ProductSubCategory;


public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT p From Product p Where LOWER(p.category.name)=:category")
    public List<Product> findByCategory(@Param("category") String category);

    //	@Query("SELECT p From Product p where LOWER(p.title) Like %:query% OR LOWER(p.description) Like %:query% OR LOWER(p.brand) LIKE %:query% OR LOWER(p.category.name) LIKE %:query%")
    @Query(value = "SELECT * FROM product WHERE title ~* CONCAT('\\y', :query, '\\y')", nativeQuery = true)
    public List<Product> searchProduct(@Param("query") String query);

    @Query("SELECT p FROM Product p " +
            "WHERE (p.category.name = :category OR :category = '') " +
            "AND ((:minPrice IS NULL AND :maxPrice IS NULL) OR (p.discountedPrice BETWEEN :minPrice AND :maxPrice)) " +
            "AND (:minDiscount IS NULL OR p.discountPercent >= :minDiscount) " +
            "ORDER BY " +
            "CASE WHEN :sort = 'price_low' THEN p.discountedPrice END ASC, " +
            "CASE WHEN :sort = 'price_high' THEN p.discountedPrice END DESC")
    List<Product> filterProducts(
            @Param("category") String category,
            @Param("minPrice") Integer minPrice,
            @Param("maxPrice") Integer maxPrice,
            @Param("minDiscount") Integer minDiscount,
            @Param("sort") String sort
    );

    // Getting all featured products
    List<Product> findByIsFeaturedTrue();

    // Getting the most recent products added to the catalog
    @Query("SELECT p FROM Product p WHERE p.createdAt >= :fromDate ORDER BY p.createdAt DESC")
    List<Product> findNewArrivals(@Param("fromDate") LocalDateTime fromDate);

    // Getting all discounted products
    @Query("SELECT p FROM Product p WHERE p.discountPercent > 0 ORDER BY p.discountPercent DESC")
    List<Product> findDiscountedProducts();
    
}
