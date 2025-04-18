package com.rhaveeval.shoppingcart.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rhaveeval.shoppingcart.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
