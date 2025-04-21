package com.rhaveeval.shoppingcart.model;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Cart {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private BigDecimal totalAmount = BigDecimal.ZERO;
	
	@OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<CartItem> items = new HashSet<>();
	
	@JsonIgnore
	@OneToOne
	@JoinColumn(name = "user_id")
	private User user;

	public void addItem(CartItem item) {
		this.items.add(item);
		item.setCart(this);
		updateTotalAmount();
	}

	public void removeItem(CartItem item) {
		this.items.remove(item);
		item.setCart(null);
		updateTotalAmount();
	}

	private void updateTotalAmount() {
		this.totalAmount = items.stream().map(item -> {
			BigDecimal unitPrice = item.getUnitPrice();
			if (unitPrice == null) {
				return BigDecimal.ZERO;
			}
			return unitPrice.multiply(BigDecimal.valueOf(item.getQuantity()));
		}).reduce(BigDecimal.ZERO, BigDecimal::add);
	}

	public void clearCart() {
		this.items.clear();
		updateTotalAmount();
	}
}
