package com.ecommerce.service;

import java.util.List;

import com.ecommerce.exception.OrderException;
import com.ecommerce.exception.UserException;
import com.ecommerce.model.Order;
import com.ecommerce.model.User;
import com.ecommerce.dto.AddressDto;
import com.ecommerce.dto.OrderDto;

public interface OrderService {

    public OrderDto createOrder(User user, AddressDto shippingAddress) throws OrderException;

    public OrderDto createOrder(User user, Long addressId) throws OrderException;

    public Order findOrderById(Long orderId) throws OrderException;

    public OrderDto findOrderByOrderId(User user, String orderId) throws UserException, OrderException;

    public OrderDto viewOrderById(Long orderId) throws OrderException;

    public List<OrderDto> usersOrderHistory(Long userId);

    public OrderDto confirmedOrder(Long orderId) throws OrderException;

    public OrderDto shippedOrder(Long orderId) throws OrderException;

    public OrderDto deliveredOrder(Long orderId) throws OrderException;

    public OrderDto cancelOrder(Long orderId) throws OrderException;

    public List<OrderDto> getAllOrders();

    public void deleteOrder(Long orderId) throws OrderException;

    public Order findOrderByRazorpayOrderId(String razorpayOrderId) throws OrderException;

}
