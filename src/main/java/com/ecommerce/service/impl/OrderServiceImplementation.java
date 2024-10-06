package com.ecommerce.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.ecommerce.mapper.AddressMapper;
import com.ecommerce.mapper.OrderMapper;
import com.ecommerce.dto.AddressDto;
import com.ecommerce.dto.OrderDto;
import com.ecommerce.service.CartService;
import com.ecommerce.service.OrderItemService;
import com.ecommerce.service.OrderService;
import com.ecommerce.utility.DtoValidatorUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import com.ecommerce.exception.OrderException;
import com.ecommerce.modal.Address;
import com.ecommerce.modal.Cart;
import com.ecommerce.modal.CartItem;
import com.ecommerce.modal.Order;
import com.ecommerce.modal.OrderItem;
import com.ecommerce.modal.User;
import com.ecommerce.repository.AddressRepository;
import com.ecommerce.repository.OrderItemRepository;
import com.ecommerce.repository.OrderRepository;
import com.ecommerce.repository.UserRepository;
import com.ecommerce.user.domain.OrderStatus;
import com.ecommerce.user.domain.PaymentStatus;

@Service
@AllArgsConstructor
public class OrderServiceImplementation implements OrderService {

    private OrderRepository orderRepository;
    private CartService cartService;
    private AddressRepository addressRepository;
    private UserRepository userRepository;
    private OrderItemRepository orderItemRepository;

    @Override
    public OrderDto createOrder(User user, AddressDto add) throws OrderException {

        if (add.getFirstName().isEmpty() ||
                add.getLastName().isEmpty() ||
                add.getStreetAddress().isEmpty() ||
                add.getCity().isEmpty() ||
                add.getState().isEmpty() ||
                add.getZipCode().isEmpty() ||
                add.getMobile().isEmpty()) {
            throw new OrderException("Invalid Address");
        }

        DtoValidatorUtil.validate(add);
        Address reqAddress = AddressMapper.toAddress(add);
        reqAddress.setUser(user);
        Address address = addressRepository.save(reqAddress);
        user.getAddresses().add(address);
        userRepository.save(user);

        Cart cart = cartService.findUserCart(user.getId());
        List<OrderItem> orderItems = new ArrayList<>();

        for (CartItem item : cart.getCartItems()) {
            OrderItem orderItem = new OrderItem();

            orderItem.setPrice(item.getPrice());
            orderItem.setProduct(item.getProduct());
            orderItem.setQuantity(item.getQuantity());
            orderItem.setSize(item.getSize());
            orderItem.setUserId(item.getUserId());
            orderItem.setDiscountedPrice(item.getDiscountedPrice());


            OrderItem createdOrderItem = orderItemRepository.save(orderItem);

            orderItems.add(createdOrderItem);
        }


        Order createdOrder = new Order();
        createdOrder.setUser(user);
        createdOrder.setOrderItems(orderItems);
        createdOrder.setTotalPrice(cart.getTotalPrice());
        createdOrder.setTotalDiscountedPrice(cart.getTotalDiscountedPrice());
        createdOrder.setDiscount(cart.getDiscount());
        createdOrder.setTotalItem(cart.getTotalItem());

        createdOrder.setShippingAddress(address);
        createdOrder.setOrderDate(LocalDateTime.now());
        createdOrder.setOrderStatus(OrderStatus.PENDING);
        createdOrder.getPaymentDetails().setStatus(PaymentStatus.PENDING);
        createdOrder.setCreatedAt(LocalDateTime.now());

        Order savedOrder = orderRepository.save(createdOrder);

        OrderDto resOrder = OrderMapper.toOrderDto(savedOrder);
        for (OrderItem item : orderItems) {
            item.setOrder(savedOrder);
            orderItemRepository.save(item);
        }

        return resOrder;

    }

//	@Override
//	public Order placedOrder(Long orderId) throws OrderException {
//		Order order=findOrderById(orderId);
//		order.setOrderStatus(OrderStatus.PLACED);
//		order.getPaymentDetails().setStatus(PaymentStatus.COMPLETED);
//		return order;
//	}

    @Override
    public OrderDto confirmedOrder(Long orderId) throws OrderException {
        Order order = findOrderById(orderId);
        order.setOrderStatus(OrderStatus.CONFIRMED);

        Order saveOrder = orderRepository.save(order);
        return OrderMapper.toOrderDto(saveOrder);
    }

    @Override
    public OrderDto shippedOrder(Long orderId) throws OrderException {
        Order order = findOrderById(orderId);
        order.setOrderStatus(OrderStatus.SHIPPED);

        Order saveOrder = orderRepository.save(order);
        return OrderMapper.toOrderDto(saveOrder);
    }

    @Override
    public OrderDto deliveredOrder(Long orderId) throws OrderException {
        Order order = findOrderById(orderId);
        order.setOrderStatus(OrderStatus.DELIVERED);

        Order saveOrder = orderRepository.save(order);
        return OrderMapper.toOrderDto(saveOrder);
    }

    @Override
    public OrderDto cancelOrder(Long orderId) throws OrderException {
        Order order = findOrderById(orderId);
        order.setOrderStatus(OrderStatus.CANCELLED);

        Order saveOrder = orderRepository.save(order);
        return OrderMapper.toOrderDto(saveOrder);
    }

    @Override
    public Order findOrderById(Long orderId) throws OrderException {
        Optional<Order> opt = orderRepository.findById(orderId);

        if (opt.isPresent()) {
            return opt.get();
        }
        throw new OrderException("order not exist with id " + orderId);
    }

    @Override
    public OrderDto viewOrderById(Long orderId) throws OrderException {
        Optional<Order> opt = orderRepository.findById(orderId);
        if (opt.isPresent()) {
            return OrderMapper.toOrderDto(opt.get());
        }
        throw new OrderException("order not exist with id " + orderId);
    }

    @Override
    public List<OrderDto> usersOrderHistory(Long userId) {
        List<Order> saveOrder = orderRepository.getUsersOrders(userId);
        List<OrderDto> orderDto = new ArrayList<>();
        for (Order order : saveOrder) {
            orderDto.add(OrderMapper.toOrderDto(order));
        }
        return orderDto;
        //return orderRepository.getUsersOrders(userId);
    }

    @Override
    public List<OrderDto> getAllOrders() {
        List<Order> saveOrder = orderRepository.findAll();
        List<OrderDto> orderDto = new ArrayList<>();
        for (Order order : saveOrder) {
            orderDto.add(OrderMapper.toOrderDto(order));
        }
        return orderDto;
    }

    @Override
    public void deleteOrder(Long orderId) throws OrderException {
        Order order = findOrderById(orderId);
        if (order == null) {
            throw new OrderException("order not exist with id " + orderId);
        }
        orderRepository.deleteById(orderId);

    }

}
