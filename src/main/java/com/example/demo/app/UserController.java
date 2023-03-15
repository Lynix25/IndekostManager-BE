package com.example.demo.app;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/user")
@RestController
public class UserController {
	@Autowired
	UserRepository userRepository;
	@GetMapping
	public List<User> getAllUsers(){
		return userRepository.findAll();
	}
	@PostMapping
	public User createUser(@RequestBody User user) {
		 User u = new User();
		 u.setAge(user.getAge());
		 u.setName(user.getName());
		 return userRepository.save(u);
	}
	
}
