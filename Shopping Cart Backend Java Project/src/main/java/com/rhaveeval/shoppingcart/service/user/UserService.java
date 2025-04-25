package com.rhaveeval.shoppingcart.service.user;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.rhaveeval.shoppingcart.dto.UserDto;
import com.rhaveeval.shoppingcart.exceptions.AlreadyExistsException;
import com.rhaveeval.shoppingcart.exceptions.ResourceNotFoundException;
import com.rhaveeval.shoppingcart.model.User;
import com.rhaveeval.shoppingcart.repository.UserRepository;
import com.rhaveeval.shoppingcart.request.CreateUserRequest;
import com.rhaveeval.shoppingcart.request.UpdateUserRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService implements UserServiceImpl {

	private final UserRepository userRepository;
	private final ModelMapper modelMapper;

	@Override
	public User getUserById(Long userId) {
		return userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
	}

	@Override
	public User createUser(CreateUserRequest request) {
		return Optional.of(request).filter(user -> !userRepository.existsByEmail(request.getEmail())).map(req -> {
			User newUser = new User();
			newUser.setEmail(request.getEmail());
			newUser.setPassword(request.getPassword());
			newUser.setFirstName(request.getFirstName());
			newUser.setLastName(request.getLastName());
			return userRepository.save(newUser);
		}).orElseThrow(() -> new AlreadyExistsException(request.getEmail() + " already exists."));
	}

	@Override
	public User updateUser(UpdateUserRequest request, Long userId) {
		return userRepository.findById(userId).map(existingUser -> {
			existingUser.setFirstName(request.getFirstName());
			existingUser.setLastName(request.getLastName());
			return userRepository.save(existingUser);
		}).orElseThrow(() -> new ResourceNotFoundException("User not found"));
	}

	@Override
	public void deleteUser(Long userId) {
		userRepository.findById(userId).ifPresentOrElse(userRepository::delete, () -> {
			new ResourceNotFoundException("User not found");
		});
	}

	@Override
	public UserDto convertToDto(User user) {
		return modelMapper.map(user, UserDto.class);
	}

}
