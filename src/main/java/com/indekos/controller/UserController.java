package com.indekos.controller;

import com.indekos.common.helper.exception.InvalidRequestException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
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
        if(errors.hasErrors()){
            List<String> errorList = new ArrayList<>();
            for (ObjectError error:errors.getAllErrors()) {
                errorList.add(error.getDefaultMessage());
            }
            throw new InvalidRequestException("Invalid Request", errorList);
        }

        userService.register(userRegisterRequest);
        return new ResponseEntity(new Response("Berhasil","User berhasil di tambahkan"), HttpStatus.OK);
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
    
    @PutMapping("/{userId}")
    public ResponseEntity<User> updateUser(@PathVariable String userId, @Validated @RequestBody UserRegisterRequest userRegisterRequest){
    	final User updatedUser = userService.update(userId, userRegisterRequest);
    	return ResponseEntity.ok(updatedUser);
    }
    
    @DeleteMapping("/{userId}")
    public Map<String, Boolean> deleteUser(@PathVariable String userId) {
    	Map<String, Boolean> response = new HashMap<>();
		response.put("Deleted", userService.delete(userId));
		return response;
    }
}
