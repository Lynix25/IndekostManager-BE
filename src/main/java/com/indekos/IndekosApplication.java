package com.indekos;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class IndekosApplication {

	public static void main(String[] args) {
		SpringApplication.run(IndekosApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
//				registry.addMapping("/user/all").allowedOrigins("http://127.0.0.1:5500");
				registry.addMapping("/**").allowedOrigins("http://127.0.0.1:5502");
				registry.addMapping("/**").allowedMethods("*");
			}
		};
	}
}
