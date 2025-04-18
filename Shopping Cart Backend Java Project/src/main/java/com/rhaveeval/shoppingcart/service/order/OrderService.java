package com.rhaveeval.shoppingcart.service.order;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.rhaveeval.shoppingcart.enums.OrderStatus;
import com.rhaveeval.shoppingcart.exceptions.ResourceNotFoundException;
import com.rhaveeval.shoppingcart.model.Cart;
import com.rhaveeval.shoppingcart.model.Order;
import com.rhaveeval.shoppingcart.model.OrderItem;
import com.rhaveeval.shoppingcart.model.Product;
import com.rhaveeval.shoppingcart.repository.OrderRepository;
import com.rhaveeval.shoppingcart.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService implements OrderServiceImpl {

	private final OrderRepository orderRepository;
	private final ProductRepository productRepository;

	@Override
	public Order placeOrder(Long userId) {
		// TODO Auto-generated method stub
		return null;
	}

	private Order createOrder(Cart cart) {
		Order order = new Order();
		//set the user
		order.setOrderStatus(OrderStatus.PENDING);
		order.setOrderDate(LocalDate.now());
		return order;
	}
	
	private List<OrderItem> createOrderItems(Order order, Cart cart) {
		return cart.getItems().stream().map(cartItem -> {
			Product product = cartItem.getProduct();
			product.setInventory(product.getInventory() - cartItem.getQuantity());
			productRepository.save(product);
			return new OrderItem(order, product, cartItem.getQuantity(), cartItem.getUnitPrice());
		}).toList();
	}

	private BigDecimal calculateTotalAmount(List<OrderItem> orderItems) {
		return orderItems.stream().map(item -> item.getPrice().multiply(new BigDecimal(item.getQuantity())))
				.reduce(BigDecimal.ZERO, BigDecimal::add);
	}

	@Override
	public Order getOrder(Long orderId) {

		return orderRepository.findById(orderId).orElseThrow(() -> new ResourceNotFoundException("Order not found"));
	}

}
