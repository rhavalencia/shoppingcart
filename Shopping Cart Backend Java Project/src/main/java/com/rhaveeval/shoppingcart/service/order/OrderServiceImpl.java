package com.rhaveeval.shoppingcart.service.order;

import java.util.List;

import com.rhaveeval.shoppingcart.dto.OrderDto;
import com.rhaveeval.shoppingcart.model.Order;

public interface OrderServiceImpl {
	Order placeOrder(Long userId);
	OrderDto getOrder(Long orderId);
	List<OrderDto> getUserOrders(Long userId);
}
