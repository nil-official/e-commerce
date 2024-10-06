package com.ecommerce.service;

import java.util.List;

import com.ecommerce.exception.OrderException;
import com.ecommerce.modal.Order;
import com.ecommerce.modal.User;
import com.ecommerce.dto.AddressDto;
import com.ecommerce.dto.OrderDto;

public interface OrderService {

    public OrderDto createOrder(User user, AddressDto shippingAdress) throws OrderException;

    public Order findOrderById(Long orderId) throws OrderException;

    public OrderDto viewOrderById(Long orderId) throws OrderException;

    public List<OrderDto> usersOrderHistory(Long userId);

    //public Order placedOrder(Long orderId) throws OrderException;

    public OrderDto confirmedOrder(Long orderId) throws OrderException;

    public OrderDto shippedOrder(Long orderId) throws OrderException;

    public OrderDto deliveredOrder(Long orderId) throws OrderException;

    public OrderDto cancelOrder(Long orderId) throws OrderException;

    public List<OrderDto> getAllOrders();

    public void deleteOrder(Long orderId) throws OrderException;

}
