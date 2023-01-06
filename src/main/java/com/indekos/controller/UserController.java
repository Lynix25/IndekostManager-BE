package com.indekos.controller;

import com.indekos.common.helper.exception.InvalidRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import com.indekos.dto.request.UserRegisterRequest;
import com.indekos.dto.response.Response;
import com.indekos.services.UserService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

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
    @GetMapping(value = "/all")
    public ResponseEntity getAllUser(){
        userService.getAll();
        return new ResponseEntity<>(new Response("Sukses","Sukses"),HttpStatus.OK);
    }

//    @GetMapping(value = "{id}")
//    public ResponseEntity getUser()
}
