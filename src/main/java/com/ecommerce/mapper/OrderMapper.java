package com.ecommerce.mapper;

import com.ecommerce.model.Order;
import com.ecommerce.dto.OrderDto;

public class OrderMapper {
    public static OrderDto toOrderDto(Order order) {
        OrderDto orderDto = new OrderDto();
        orderDto.setId(order.getId());
        orderDto.setOrderId(order.getOrderId());
        orderDto.setOrderDate(order.getOrderDate());
        orderDto.setDeliveryDate(order.getDeliveryDate());
//        orderDto.setShippingAddress(AddressMapper.toAddress(order.getOrderAddress()));
        orderDto.setOrderAddress(order.getOrderAddress());
        orderDto.setPaymentDetails(order.getPaymentDetails());
        orderDto.setTotalPrice(order.getTotalPrice());
        orderDto.setTotalDiscountedPrice(order.getTotalDiscountedPrice());
        orderDto.setOrderStatus(order.getOrderStatus());
        orderDto.setTotalItem(order.getTotalItem());
        orderDto.setCreatedAt(order.getCreatedAt());
        orderDto.setUserDto(UserMapper.toUserDto(order.getUser()));
        orderDto.setOrderItems(order.getOrderItems());
        return orderDto;
    }
}