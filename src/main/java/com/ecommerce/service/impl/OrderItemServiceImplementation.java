package com.ecommerce.service.impl;

import com.ecommerce.service.OrderItemService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import com.ecommerce.model.OrderItem;
import com.ecommerce.repository.OrderItemRepository;

@Service
@AllArgsConstructor
public class OrderItemServiceImplementation implements OrderItemService {

	private OrderItemRepository orderItemRepository;

	@Override
	public OrderItem createOrderItem(OrderItem orderItem) {
		return orderItemRepository.save(orderItem);
	}

}
