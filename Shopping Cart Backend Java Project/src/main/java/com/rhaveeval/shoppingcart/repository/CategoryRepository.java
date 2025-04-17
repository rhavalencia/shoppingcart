package com.rhaveeval.shoppingcart.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rhaveeval.shoppingcart.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

	Category findByName(String name);

	boolean existsByName(String name);
	

}
