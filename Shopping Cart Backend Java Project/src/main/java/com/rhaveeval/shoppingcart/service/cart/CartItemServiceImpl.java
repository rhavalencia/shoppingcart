package com.rhaveeval.shoppingcart.service.cart;

import com.rhaveeval.shoppingcart.model.CartItem;

public interface CartItemServiceImpl {
	void addItemToCart(Long cartId, Long productId, int quantity);

	void removeItemFromCart(Long cartId, Long productId);

	void updateItemQuantity(Long cartId, Long productId, int quantity);

	CartItem getCartItem(Long cartId, Long productId);
}
