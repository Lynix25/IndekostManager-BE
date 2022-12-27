package com.indekos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Collections;

@SpringBootApplication
public class IndekostApplication {

	public static void main(String[] args) {
		SpringApplication.run(IndekostApplication.class, args);
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
//				registry.addMapping("/user/all").allowedOrigins("http://127.0.0.1:5500");
				registry.addMapping("/**").allowedOrigins("http://127.0.0.1:5500");
			}
		};
	}
}
