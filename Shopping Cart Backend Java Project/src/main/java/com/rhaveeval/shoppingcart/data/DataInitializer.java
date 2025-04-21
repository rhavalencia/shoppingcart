package com.rhaveeval.shoppingcart.data;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.rhaveeval.shoppingcart.model.User;
import com.rhaveeval.shoppingcart.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DataInitializer implements ApplicationListener<ApplicationReadyEvent>{
	private final UserRepository userRepository;

	@Override
	public void onApplicationEvent(ApplicationReadyEvent event) {
		createDefaultUserIfNotExists();
		
	}

	private void createDefaultUserIfNotExists() {
		for (int i =1; i <= 5; i++) {
			String defaultEmail = "user" + i + "@email.com";
			
			if (userRepository.existsByEmail(defaultEmail)) {
				continue;
			}
			
			User user = new User();
			user.setFirstName("User");
			user.setLastName("U"+ i);
			user.setEmail(defaultEmail);
			user.setPassword("123456");
			userRepository.save(user);
			System.out.println("Default User " + i + "created");
			
		}
		
	}
	
	
	
}
