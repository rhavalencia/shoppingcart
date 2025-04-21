package com.rhaveeval.shoppingcart.dto;

import java.math.BigDecimal;

import com.rhaveeval.shoppingcart.model.Product;

import lombok.Data;

@Data
public class CartItemDto {
	private Long id;
	private int quantity;
	private BigDecimal unitPrice;
	private Product product;
}
