package com.indekos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import com.indekos.common.helper.GlobalAcceptions;
import com.indekos.common.helper.exception.InvalidRequestException;
import com.indekos.common.helper.exception.InvalidUserCredentialException;
import com.indekos.dto.request.AccountRegisterRequest;
import com.indekos.dto.request.AccountUpdateRequest;
import com.indekos.dto.request.LoginRequest;
import com.indekos.dto.response.Response;
import com.indekos.model.Account;
import com.indekos.model.User;
import com.indekos.services.AccountService;
import com.indekos.services.UserService;
import com.indekos.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/account")
//@CrossOrigin(origins = "http://127.0.0.1:5500")
public class AccountController {
    @Autowired
    AccountService accountService;
    @Autowired
    UserService userService;

    @GetMapping("/all")
    public ResponseEntity allAccount(){
        List<Account> allAccount = accountService.allUser();
        return new ResponseEntity(new Response("Sukses", "this all user"), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity login (@Valid @RequestBody LoginRequest loginRequest, Errors errors){
        if(errors.hasErrors()){
            List<String> errorList = new ArrayList<>();
            for (ObjectError error:errors.getAllErrors()) {
                errorList.add(error.getDefaultMessage());
            }
            throw new InvalidRequestException("Invalid Request", errorList);
        }

        Account account = accountService.getByUsername(loginRequest.getUsername());
        if(Utils.passwordHashing(loginRequest.getPassword()).equals(account.getPassword())){
            User user = userService.getByID(account.getUserID());
            return GlobalAcceptions.loginAllowed(user, "Success");
        }

        throw new InvalidUserCredentialException("Invalid username or password");
    }

    @PostMapping("/register")
    public ResponseEntity register(@Valid @RequestBody AccountRegisterRequest accountRegisterRequest, Errors errors){
        if(errors.hasErrors()){
            List<String> errorList = new ArrayList<>();
            for (ObjectError error:errors.getAllErrors()) {
                errorList.add(error.getDefaultMessage());
            }
            throw new InvalidRequestException("Invalid Request", errorList);
        }


        accountService.register(accountRegisterRequest);
        return new ResponseEntity(new Response("Sukses", "Success to register"), HttpStatus.BAD_REQUEST);
    }

    @PutMapping
    public ResponseEntity update(@RequestBody AccountUpdateRequest accountUpdateRequest, Errors errors){
        if(errors.hasErrors()){
            List<String> errorList = new ArrayList<>();
            for (ObjectError error:errors.getAllErrors()) {
                errorList.add(error.getDefaultMessage());
            }
            throw new InvalidRequestException("Invalid Request", errorList);
        }


        accountService.linkToUser(accountUpdateRequest);
        return new ResponseEntity(new Response("Sukses","linked"), HttpStatus.OK);
    }


}
