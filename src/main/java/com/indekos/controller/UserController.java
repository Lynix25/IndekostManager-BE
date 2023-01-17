package com.indekos.controller;

import com.indekos.common.helper.exception.InvalidRequestException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

import javax.validation.Valid;

import com.indekos.dto.MasterServiceDTO;
import com.indekos.dto.response.Response;
import com.indekos.model.Service;
import com.indekos.model.User;
import com.indekos.utils.Validated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import com.indekos.dto.request.UserRegisterRequest;
import com.indekos.model.User;
import com.indekos.services.UserService;


@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController {
	
    @Autowired
	private UserService userService;

    @PostMapping(value = "/register")
    public ResponseEntity register(@Valid @RequestBody UserRegisterRequest userRegisterRequest, Errors errors){
        Validated.request(errors);
        userService.register(userRegisterRequest);

        return new ResponseEntity(new Response("Berhasil","User berhasil di tambahkan"), HttpStatus.OK.OK);
    }
    
    @GetMapping
    public List<User> getAllUser() {
    	return userService.getAll();
    }
    
    // @PostMapping("/register")
    // public User registerUser(@Validated @RequestBody UserRegisterRequest userRegisterRequest){
//    	return new ResponseEntity(new Response("Berhasil","User berhasil di tambahkan"), HttpStatus.OK);
    // 	return userService.register(userRegisterRequest);
    // }
    
//    @PutMapping("/{userId}")
//    public ResponseEntity<User> updateUser(@PathVariable String userId, @Validated @RequestBody UserRegisterRequest userRegisterRequest){
//    	final User updatedUser = userService.update(userId, userRegisterRequest);
//    	return ResponseEntity.ok(updatedUser);
//    }
    
    @DeleteMapping("/{userId}")
    public Map<String, Boolean> deleteUser(@PathVariable String userId) {
    	Map<String, Boolean> response = new HashMap<>();
		response.put("Deleted", userService.delete(userId));
		return response;
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity getUser(@PathVariable String id){
        User user = userService.getByID(id);

        return ResponseEntity.ok().body(user);
    }
}
