package com.indekosservice.controller;

import com.indekosservice.dto.request.UserRegisterRequest;
import com.indekosservice.dto.response.Response;
import com.indekosservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user/")
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping(value = "register")
    public ResponseEntity register(@RequestBody UserRegisterRequest userRegisterRequest){
        userService.register(userRegisterRequest);
        return new ResponseEntity(new Response("Berhasil","User berhasil di tambahkan"), HttpStatus.OK);
    }
    @GetMapping(value = "all")
    public ResponseEntity getAllUser(){
        userService.getAll();
        return new ResponseEntity<>(new Response("Sukses","Sukses"),HttpStatus.OK);
    }

//    @GetMapping(value = "{id}")
//    public ResponseEntity getUser()
}
