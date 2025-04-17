package com.rhaveeval.shoppingcart.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ShopConfig {
	
	@Bean
	ModelMapper modelMapper() {
		return new ModelMapper();
	}
}
