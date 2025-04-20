package com.rhaveeval.shoppingcart.controller;

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
import com.rhaveeval.shoppingcart.model.User;
import com.rhaveeval.shoppingcart.request.CreateUserRequest;
import com.rhaveeval.shoppingcart.request.UpdateUserRequest;
import com.rhaveeval.shoppingcart.response.ApiResponse;
import com.rhaveeval.shoppingcart.service.user.UserServiceImpl;

import lombok.RequiredArgsConstructor;

import static org.springframework.http.HttpStatus.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/users")
public class UserController {
	private final UserServiceImpl userServiceImpl;
	
	@GetMapping("user/{userId}")
	public ResponseEntity<ApiResponse> getUserById(@PathVariable Long userId){
		try {
			User user = userServiceImpl.getUserById(userId);
			return ResponseEntity.ok(new ApiResponse("OK", user));
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
		}
	}
	
	@PostMapping("/add")
	public ResponseEntity<ApiResponse> createUser(@RequestBody CreateUserRequest request){
		try {
			User user = userServiceImpl.createUser(request);
			return ResponseEntity.ok(new ApiResponse("New User Created", user));
		} catch (AlreadyExistsException e) {
			return ResponseEntity.status(CONFLICT).body(new ApiResponse(e.getMessage(), null));
		}
	}
	
	@PutMapping("/user/{userId}/update")
	public ResponseEntity<ApiResponse> updateUser(@RequestBody UpdateUserRequest request, @PathVariable Long userId){
		try {
			User user = userServiceImpl.updateUser(request, userId);
			return ResponseEntity.ok(new ApiResponse("Updated User Details Successfully", user));
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
		}
	}
	
	@DeleteMapping("/user/{userId}/delete")
	public ResponseEntity<ApiResponse> deleteUser(@PathVariable Long userId){
		try {
			userServiceImpl.deleteUser(userId);
			return ResponseEntity.ok(new ApiResponse("User has been deleted", null));
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
		}
	}
}




















