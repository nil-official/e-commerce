package com.ecommerce.controller;

import java.util.List;

import com.ecommerce.dto.OrderDto;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.exception.OrderException;
import com.ecommerce.response.ApiResponse;
import com.ecommerce.service.OrderService;

@RestController
@AllArgsConstructor
@RequestMapping("/api/admin/orders")
public class AdminOrderController {

    private OrderService orderService;

    @GetMapping("/")
    public ResponseEntity<List<OrderDto>> getAllOrdersHandler() {
        List<OrderDto> orders = orderService.getAllOrders();
        return new ResponseEntity<>(orders, HttpStatus.ACCEPTED);
    }

    @PutMapping("/{orderId}/confirmed")
    public ResponseEntity<OrderDto> ConfirmedOrderHandler(@PathVariable Long orderId, @RequestHeader("Authorization") String jwt) throws OrderException {
        OrderDto order = orderService.confirmedOrder(orderId);
        return new ResponseEntity<>(order, HttpStatus.ACCEPTED);
    }

    @PutMapping("/{orderId}/ship")
    public ResponseEntity<OrderDto> shippedOrderHandler(@PathVariable Long orderId, @RequestHeader("Authorization") String jwt) throws OrderException {
        OrderDto order = orderService.shippedOrder(orderId);
        return new ResponseEntity<>(order, HttpStatus.ACCEPTED);
    }

    @PutMapping("/{orderId}/deliver")
    public ResponseEntity<OrderDto> deliveredOrderHandler(@PathVariable Long orderId, @RequestHeader("Authorization") String jwt) throws OrderException {
        OrderDto order = orderService.deliveredOrder(orderId);
        return new ResponseEntity<>(order, HttpStatus.ACCEPTED);
    }

    @PutMapping("/{orderId}/cancel")
    public ResponseEntity<OrderDto> canceledOrderHandler(@PathVariable Long orderId, @RequestHeader("Authorization") String jwt) throws OrderException {
        OrderDto order = orderService.cancelOrder(orderId);
        return new ResponseEntity<>(order, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{orderId}/delete")
    public ResponseEntity<ApiResponse> deleteOrderHandler(@PathVariable Long orderId, @RequestHeader("Authorization") String jwt) throws OrderException {
        orderService.deleteOrder(orderId);
        ApiResponse res = new ApiResponse("Order Deleted Successfully", true);
        System.out.println("delete method working....");
        return new ResponseEntity<>(res, HttpStatus.ACCEPTED);
    }

}
