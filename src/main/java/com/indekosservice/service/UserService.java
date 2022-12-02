package com.indekosservice.service;

import com.indekosservice.dto.request.UserRegisterRequest;
import com.indekosservice.model.User;
import com.indekosservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;
    public User register(UserRegisterRequest userRegisterRequest){
        User user = new User();
        user.setUserID(UUID.randomUUID().toString());
        user.setName(userRegisterRequest.getName());
        user.setNoTelp(userRegisterRequest.getNoTelp());
        userRepository.save(user);

        return user;
    }

    public List<User> getAll(){
        return  userRepository.findAll();
    }
}
