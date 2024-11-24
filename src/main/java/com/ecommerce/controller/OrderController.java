package com.ecommerce.controller;

import java.util.List;

import com.ecommerce.dto.AddressDto;
import com.ecommerce.dto.OrderDto;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.exception.OrderException;
import com.ecommerce.exception.UserException;
import com.ecommerce.model.User;
import com.ecommerce.service.OrderService;
import com.ecommerce.service.UserService;

@RestController
@AllArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {

    private OrderService orderService;
    private UserService userService;

    @PostMapping("/")
    public ResponseEntity<OrderDto> createOrderHandler(@RequestBody AddressDto shippingAddress,
                                                       @RequestHeader("Authorization") String jwt) throws UserException, OrderException {

        User user = userService.findUserProfileByJwt(jwt);
        OrderDto order = orderService.createOrder(user, shippingAddress);
        return new ResponseEntity<>(order, HttpStatus.OK);

    }

    // Overloaded method
    @PostMapping("/{addressId}")
    public ResponseEntity<OrderDto> createOrderHandler(@PathVariable Long addressId,
                                                       @RequestHeader("Authorization") String jwt) throws UserException, OrderException {

        User user = userService.findUserProfileByJwt(jwt);
        OrderDto order = orderService.createOrder(user, addressId);
        return new ResponseEntity<>(order, HttpStatus.OK);

    }

    @GetMapping("/user")
    public ResponseEntity<List<OrderDto>> usersOrderHistoryHandler(@RequestHeader("Authorization") String jwt)
            throws OrderException, UserException {

        User user = userService.findUserProfileByJwt(jwt);
        List<OrderDto> orders = orderService.usersOrderHistory(user.getId());
        return new ResponseEntity<>(orders, HttpStatus.ACCEPTED);

    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDto> findOrderHandler(@RequestHeader("Authorization") String jwt,
                                                     @PathVariable String orderId) throws UserException, OrderException {

        User user = userService.findUserProfileByJwt(jwt);
        OrderDto orders = orderService.findOrderByOrderId(user, orderId);
        return new ResponseEntity<>(orders, HttpStatus.ACCEPTED);

    }

}
