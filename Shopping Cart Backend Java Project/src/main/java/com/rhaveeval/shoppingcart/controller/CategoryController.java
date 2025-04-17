package com.rhaveeval.shoppingcart.controller;

import java.util.List;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rhaveeval.shoppingcart.exceptions.AlreadyExistsException;
import com.rhaveeval.shoppingcart.exceptions.ResourceNotFoundException;
import com.rhaveeval.shoppingcart.model.Category;
import com.rhaveeval.shoppingcart.response.ApiResponse;
import com.rhaveeval.shoppingcart.service.category.CategoryServiceImpl;
import static org.springframework.http.HttpStatus.*;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/categories")
public class CategoryController {
	private static final HttpStatusCode CONFLICT = null;
	private final CategoryServiceImpl categoryServiceImpl;

	@GetMapping("/all")
	public ResponseEntity<ApiResponse> getAllCategories() {
		try {
			List<Category> categories = categoryServiceImpl.getAllCategories();
			return ResponseEntity.ok(new ApiResponse("Found!", categories));
		} catch (Exception e) {
			return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("Error:", INTERNAL_SERVER_ERROR));
		}
	}

	@GetMapping("category/{id}")
	public ResponseEntity<ApiResponse> getCategoryById(@PathVariable Long id) {
		try {
			Category category = categoryServiceImpl.getCategoryById(id);
			return ResponseEntity.ok(new ApiResponse("Found", category));
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
		}
	}

	@GetMapping("category/{name}")
	public ResponseEntity<ApiResponse> getCategoryByName(@PathVariable String name) {
		try {
			Category category = categoryServiceImpl.getCategoryByName(name);
			return ResponseEntity.ok(new ApiResponse("Found", category));
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
		}
	}

	@PostMapping("/add")
	public ResponseEntity<ApiResponse> addCategory(@RequestBody Category name) {
		try {
			Category newCategory = categoryServiceImpl.addCategory(name);
			return ResponseEntity.ok(new ApiResponse("New Category Added", newCategory));
		} catch (AlreadyExistsException e) {
			return ResponseEntity.status(CONFLICT).body(new ApiResponse(e.getMessage(), null));
		}
	}

	@PutMapping("/category/{id}/update")
	public ResponseEntity<ApiResponse> updateCategory(@PathVariable Long id, @RequestBody Category category) {
		try {
			Category updatedCategory = categoryServiceImpl.updateCategory(category, id);
			return ResponseEntity.ok(new ApiResponse("Update success!", updatedCategory));
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
		}
	}

	@DeleteMapping("category/{id}/delete")
	public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Long id) {
		try {
			categoryServiceImpl.deleteCategory(id);
			return ResponseEntity.ok(new ApiResponse("Deleted", null));
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
		}
	}

}
