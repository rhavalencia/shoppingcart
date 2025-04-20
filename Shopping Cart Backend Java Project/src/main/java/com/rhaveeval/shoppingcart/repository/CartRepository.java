package com.rhaveeval.shoppingcart.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rhaveeval.shoppingcart.model.Cart;

public interface CartRepository extends JpaRepository<Cart, Long>{

	Cart findByUserId(Long userId);

}
