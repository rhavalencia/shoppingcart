package com.rhaveeval.shoppingcart.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rhaveeval.shoppingcart.dto.OrderDto;
import com.rhaveeval.shoppingcart.exceptions.ResourceNotFoundException;
import com.rhaveeval.shoppingcart.model.Order;
import com.rhaveeval.shoppingcart.response.ApiResponse;
import com.rhaveeval.shoppingcart.service.order.OrderServiceImpl;

import lombok.RequiredArgsConstructor;

import static org.springframework.http.HttpStatus.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/orders")
public class OrderController {

	private final OrderServiceImpl orderServiceImpl;

	@PostMapping("/order")
	public ResponseEntity<ApiResponse> createOrder(@RequestParam Long userId) {
		try {
			Order order = orderServiceImpl.placeOrder(userId);
			return ResponseEntity.ok(new ApiResponse("Order placed successfully", order));
		} catch (Exception e) {
			return ResponseEntity.status(INTERNAL_SERVER_ERROR)
					.body(new ApiResponse("Error Occured", e.getMessage()));
		}
	}
	
	@GetMapping("/order/{orderId}")
	public ResponseEntity<ApiResponse> getOrderById(@PathVariable Long orderId){
		try {
			OrderDto order = orderServiceImpl.getOrder(orderId);
			return ResponseEntity.ok(new ApiResponse("OK", order));
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
		}
	}
	
	@GetMapping("/orders/user/{userId}")
	public ResponseEntity<ApiResponse> getUserOrders(@PathVariable Long userId){
		try {
			List<OrderDto> order = orderServiceImpl.getUserOrders(userId);
			return ResponseEntity.ok(new ApiResponse("OK", order));
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
		}
	}
	
}
