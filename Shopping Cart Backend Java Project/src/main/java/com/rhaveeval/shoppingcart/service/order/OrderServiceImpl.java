package com.rhaveeval.shoppingcart.service.order;

import com.rhaveeval.shoppingcart.model.Order;

public interface OrderServiceImpl {
	Order placeOrder(Long userId);
	Order getOrder(Long orderId);
}
