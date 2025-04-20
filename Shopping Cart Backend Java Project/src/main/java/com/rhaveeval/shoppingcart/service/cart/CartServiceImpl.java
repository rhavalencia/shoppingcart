package com.rhaveeval.shoppingcart.service.cart;

import java.math.BigDecimal;

import com.rhaveeval.shoppingcart.model.Cart;

public interface CartServiceImpl{
	Cart getCart(Long id);
	void clearCart(Long id);
	BigDecimal getTotalPrice(Long id);
	Long initializeNewCart();
	Cart getCartByUserId(Long userId);
}
