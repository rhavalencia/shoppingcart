package com.rhaveeval.shoppingcart.service.cart;

import java.math.BigDecimal;

import com.rhaveeval.shoppingcart.model.Cart;
import com.rhaveeval.shoppingcart.model.User;

public interface CartServiceImpl{
	Cart getCart(Long id);
	void clearCart(Long id);
	BigDecimal getTotalPrice(Long id);
	Cart getCartByUserId(Long userId);
	Cart initializeNewCart(User user);
}
