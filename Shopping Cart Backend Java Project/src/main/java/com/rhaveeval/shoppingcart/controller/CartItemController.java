package com.rhaveeval.shoppingcart.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rhaveeval.shoppingcart.exceptions.ResourceNotFoundException;
import com.rhaveeval.shoppingcart.response.ApiResponse;
import com.rhaveeval.shoppingcart.service.cart.CartItemServiceImpl;
import com.rhaveeval.shoppingcart.service.cart.CartServiceImpl;

import lombok.RequiredArgsConstructor;
import static org.springframework.http.HttpStatus.*;

import org.springframework.http.ResponseEntity;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/cartItems")
public class CartItemController {
	private final CartItemServiceImpl cartItemServiceImpl;
	private final CartServiceImpl cartServiceImpl;

	@PostMapping("/item/add")
	public ResponseEntity<ApiResponse> addItemToCart(@RequestParam(required = false) Long cartId, @RequestParam Long productId,
			@RequestParam Integer quantity) {
		try {
			if(cartId == null) {
				cartId = cartServiceImpl.initializeNewCart();
			}
			cartItemServiceImpl.addItemToCart(cartId, productId, quantity);
			return ResponseEntity.ok(new ApiResponse("Item Added Successfully", null));
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
		}
	}

	@DeleteMapping("/cart/{cartId}/item/{productId}/remove")
	public ResponseEntity<ApiResponse> removeItemFromCart(@PathVariable Long cartId, @PathVariable Long productId) {
		try {
			cartItemServiceImpl.removeItemFromCart(cartId, productId);
			return ResponseEntity.ok(new ApiResponse("Item removed", null));
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
		}
	}
	
	
	@PutMapping("/cart/{cartId}/item/{productId}/update")
	public ResponseEntity<ApiResponse> updateItemQuantity(@PathVariable Long cartId,
														  @PathVariable Long productId, 
														  @RequestParam Integer quantity){
		try {
			cartItemServiceImpl.updateItemQuantity(cartId, productId, quantity);
			return ResponseEntity.ok(new ApiResponse("Quantity Updated Successfully", null));
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
		}
	}
}
