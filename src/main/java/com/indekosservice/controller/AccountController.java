package com.indekosservice.controller;

import com.indekosservice.dto.request.AccountLinkRequset;
import com.indekosservice.dto.request.AccountRegisterRequest;
import com.indekosservice.dto.request.LoginRequest;
import com.indekosservice.dto.response.Response;
import com.indekosservice.model.Account;
import com.indekosservice.service.AccountService;
import com.indekosservice.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.MethodNotAllowedException;

import java.util.List;

@RestController
@RequestMapping(value = "/account/")
//@CrossOrigin(origins = "http://127.0.0.1:5500")
public class AccountController {
    @Autowired
    AccountService accountService;

    @PostMapping("login")
    public ResponseEntity login (@RequestBody LoginRequest loginRequest){
        System.out.println(loginRequest.getUsername() + loginRequest.getPassword());
        Account account = accountService.getByUsername(loginRequest.getUsername());
        if(Utils.passwordHashing(loginRequest.getPassword()).equals(account.getPassword())){
            return new ResponseEntity(new Response("Sukses","Allowed to Login"), HttpStatus.OK);
        }

        return new ResponseEntity(new Response("Fail", "Not allowed to login"), HttpStatus.BAD_REQUEST);
    }

    @GetMapping("all")
    public ResponseEntity allAccount(){
        List<Account> allAccount = accountService.allUser();
        return new ResponseEntity(new Response("Sukses", "this all user"), HttpStatus.OK);
    }

    @PostMapping("register")
    public ResponseEntity register(@RequestBody AccountRegisterRequest accountRegisterRequest){
        accountService.register(accountRegisterRequest);
        return new ResponseEntity(new Response("Sukses", "Success to register"), HttpStatus.BAD_REQUEST);
    }

    @PutMapping("linkuser")
    public ResponseEntity linkUser(@RequestBody AccountLinkRequset accountLinkRequset){
        accountService.linkUser(accountLinkRequset);
        return new ResponseEntity(new Response("Sukses","linked"), HttpStatus.OK);
    }


}
