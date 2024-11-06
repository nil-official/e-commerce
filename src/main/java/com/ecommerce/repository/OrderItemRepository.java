package com.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.model.OrderItem;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    @Query("SELECT oi.product, SUM(oi.quantity) as totalQuantity " +
            "FROM OrderItem oi " +
            "GROUP BY oi.product " +
            "ORDER BY totalQuantity DESC")
    List<Object[]> findBestSellerProducts();

}
