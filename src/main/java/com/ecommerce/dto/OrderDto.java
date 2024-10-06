package com.ecommerce.dto;

import com.ecommerce.modal.Address;
import com.ecommerce.modal.PaymentDetails;
import com.ecommerce.user.domain.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OrderDto {

    private Long id;

    private LocalDateTime orderDate;

    private LocalDateTime deliveryDate;

    private Address shippingAddress;

    private PaymentDetails paymentDetails=new PaymentDetails();

    private double totalPrice;

    private Integer totalDiscountedPrice;

    private Integer discounte;

    private OrderStatus orderStatus;

    private int totalItem;

    private LocalDateTime createdAt;

    private UserDto userDto;

}