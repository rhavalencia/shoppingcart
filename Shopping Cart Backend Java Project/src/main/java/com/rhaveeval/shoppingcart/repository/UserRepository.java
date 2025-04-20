package com.rhaveeval.shoppingcart.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rhaveeval.shoppingcart.model.User;

public interface UserRepository extends JpaRepository<User, Long>{

	boolean existsByEmail(String email);

}
