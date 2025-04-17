package com.rhaveeval.shoppingcart.service.cart;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.rhaveeval.shoppingcart.exceptions.ResourceNotFoundException;
import com.rhaveeval.shoppingcart.model.Cart;
import com.rhaveeval.shoppingcart.model.CartItem;
import com.rhaveeval.shoppingcart.model.Product;
import com.rhaveeval.shoppingcart.repository.CartItemRepository;
import com.rhaveeval.shoppingcart.repository.CartRepository;
import com.rhaveeval.shoppingcart.service.product.ProductServiceImpl;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartItemService implements CartItemServiceImpl {

	private final CartItemRepository cartItemRepository;
	private final CartRepository cartRepository;
	private final CartServiceImpl cartServiceImpl;
	private final ProductServiceImpl productServiceImpl;

	@Override
	public void addItemToCart(Long cartId, Long productId, int quantity) {
		Cart cart = cartServiceImpl.getCart(productId);
		Product product = productServiceImpl.getProductById(productId);
		CartItem cartItem = cart.getItems().stream().filter(item -> item.getProduct().getId().equals(productId))
				.findFirst().orElse(null);
		if (cartItem.getId() == null) {
			cartItem.setCart(cart);
			cartItem.setProduct(product);
			cartItem.setQuantity(quantity);
			cartItem.setUnitPrice(product.getPrice());
		} else {
			cartItem.setQuantity(cartItem.getQuantity() + quantity);
		}
		cartItem.setTotalPrice();
		cart.addItem(cartItem);
		cartItemRepository.save(cartItem);
		cartRepository.save(cart);
	}

	@Override
	public void removeItemFromCart(Long cartId, Long productId) {
		Cart cart = cartServiceImpl.getCart(cartId);
		CartItem itemToRemove = getCartItem(cartId, productId);
		cart.removeItem(itemToRemove);
		cartRepository.save(cart);
	}

	@Override
	public void updateItemQuantity(Long cartId, Long productId, int quantity) {
		Cart cart = cartServiceImpl.getCart(cartId);
		cart.getItems().stream().filter(items -> items.getProduct().getId().equals(productId)).findFirst()
				.ifPresent(item -> {
					item.setQuantity(quantity);
					item.setUnitPrice(item.getProduct().getPrice());
					item.setTotalPrice();
				});
		BigDecimal totalAmount = cart.getTotalAmount();
		cart.setTotalAmount(totalAmount);
		cartRepository.save(cart);
	}
	
	@Override
	public CartItem getCartItem(Long cartId, Long productId) {
		Cart cart = cartServiceImpl.getCart(cartId);
		return cart.getItems().stream().filter(item -> item.getProduct().getId().equals(productId))
		.findFirst().orElseThrow(() -> new ResourceNotFoundException("Product not found"));
	}

}
