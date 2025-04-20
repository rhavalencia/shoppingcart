package com.rhaveeval.shoppingcart.service.user;

import com.rhaveeval.shoppingcart.dto.UserDto;
import com.rhaveeval.shoppingcart.model.User;
import com.rhaveeval.shoppingcart.request.CreateUserRequest;
import com.rhaveeval.shoppingcart.request.UpdateUserRequest;

public interface UserServiceImpl {
	User getUserById(Long userId);
	User createUser (CreateUserRequest request);
	User updateUser (UpdateUserRequest request, Long userId);
	void deleteUser(Long userId);
	UserDto convertToDto(User user);
}
