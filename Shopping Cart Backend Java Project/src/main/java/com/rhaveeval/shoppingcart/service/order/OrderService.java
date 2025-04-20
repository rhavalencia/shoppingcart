package com.rhaveeval.shoppingcart.service.order;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.rhaveeval.shoppingcart.dto.OrderDto;
import com.rhaveeval.shoppingcart.enums.OrderStatus;
import com.rhaveeval.shoppingcart.exceptions.ResourceNotFoundException;
import com.rhaveeval.shoppingcart.model.Cart;
import com.rhaveeval.shoppingcart.model.Order;
import com.rhaveeval.shoppingcart.model.OrderItem;
import com.rhaveeval.shoppingcart.model.Product;
import com.rhaveeval.shoppingcart.repository.OrderRepository;
import com.rhaveeval.shoppingcart.repository.ProductRepository;
import com.rhaveeval.shoppingcart.service.cart.CartService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService implements OrderServiceImpl {

	private final OrderRepository orderRepository;
	private final ProductRepository productRepository;
	private final CartService cartService;
	private final ModelMapper modelMapper;

	@Override
	public Order placeOrder(Long userId) {
		Cart cart = cartService.getCartByUserId(userId);
		Order order = createOrder(cart);

		List<OrderItem> orderItemList = createOrderItems(order, cart);
		order.setOrderItems(new HashSet<>(orderItemList));
		order.setOrderTotalAmout(calculateTotalAmount(orderItemList));
		Order savedOrder = orderRepository.save(order);

		cartService.clearCart(cart.getId());

		return savedOrder;
	}

	private Order createOrder(Cart cart) {
		Order order = new Order();
		order.setUser(cart.getUser());
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
	public OrderDto getOrder(Long orderId) {
		return orderRepository.findById(orderId).map(this::convertToDto)
				.orElseThrow(() -> new ResourceNotFoundException("Order not found"));
	}

	public List<OrderDto> getUserOrders(Long userId) {
		List<Order> orders = orderRepository.findByUserId(userId);
		return orders.stream().map(this::convertToDto).toList();
	}

	private OrderDto convertToDto(Order order) {
		return modelMapper.map(order, OrderDto.class);
	}
}
