package com.ecommerce.repository;

import java.util.List;
import java.util.Optional;

import com.ecommerce.dto.OrderDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ecommerce.modal.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

//	@Query("SELECT o FROM Order o WHERE o.user.id = :userId AND (o.orderStatus = PLACED OR o.orderStatus = CONFIRMED OR o.orderStatus = SHIPPED OR o.orderStatus = DELIVERED)")
	@Query("SELECT o FROM Order o WHERE o.user.id = :userId")
	public List<Order> getUsersOrders(@Param("userId") Long userId);

	Optional<Order> findByRazorpayOrderId(String razorpayOrderId);

	@Query("SELECT o FROM Order o WHERE o.orderId = :orderId")
	public Optional<Order> findOrderByOrderId(String orderId);

}
