package com.ecommerce.dto;

import com.ecommerce.modal.Address;
import com.ecommerce.modal.OrderItem;
import com.ecommerce.modal.PaymentDetails;
import com.ecommerce.user.domain.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OrderDto {

    private Long id;

    private String orderId;

    private LocalDateTime orderDate;

    private LocalDateTime deliveryDate;

    private Address shippingAddress;

    private PaymentDetails paymentDetails = new PaymentDetails();

    private double totalPrice;

    private Integer totalDiscountedPrice;

    private Integer discount;

    private OrderStatus orderStatus;

    private int totalItem;

    private LocalDateTime createdAt;

    private UserDto userDto;

    private List<OrderItem> orderItems = new ArrayList<>();

}