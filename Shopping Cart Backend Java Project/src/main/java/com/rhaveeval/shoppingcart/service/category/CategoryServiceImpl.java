package com.rhaveeval.shoppingcart.service.category;

import java.util.List;

import com.rhaveeval.shoppingcart.model.Category;

public interface CategoryServiceImpl {
	Category getCategoryById(Long id);
	Category getCategoryByName(String name);
	List<Category> getAllCategories();
	Category addCategory(Category category);
	Category updateCategory(Category category, Long id);
	void deleteCategory(Long id);
	
	
}
