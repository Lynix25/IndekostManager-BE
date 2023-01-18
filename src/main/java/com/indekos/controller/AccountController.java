package com.indekos.controller;

import com.indekos.utils.Validated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import com.indekos.common.helper.GlobalAcceptions;
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

import java.util.List;

import javax.validation.Valid;

@Controller
@RequestMapping(value = "/account")
//@CrossOrigin(origins = "http://127.0.0.1:5500")
public class AccountController {
    @Autowired
    AccountService accountService;
    @Autowired
    UserService userService;

    @GetMapping
    public ResponseEntity getAllAccount(){
        List<Account> allAccount = accountService.allUser();
        GlobalAcceptions.listData(allAccount, "Success");
        return null;
    }

    @PostMapping
    public ResponseEntity register(@Valid @RequestBody AccountRegisterRequest accountRegisterRequest, Errors errors){
        Validated.request(errors);
        accountService.register(accountRegisterRequest);
        return new ResponseEntity(new Response("Sukses", "Success to register"), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity login (@Valid @RequestBody LoginRequest loginRequest, Errors errors){
        Validated.request(errors);

        Account account = accountService.getByUsername(loginRequest.getUsername());
        if(Utils.passwordHashing(loginRequest.getPassword()).equals(account.getPassword())){
            System.out.println(account.getId());
            User user = userService.getByAccountId(account.getId());
            return GlobalAcceptions.loginAllowed(user, "Success");
        }

        throw new InvalidUserCredentialException("Invalid username or password");
    }

    @PutMapping("/{id}")
    public ResponseEntity updatePassword(@PathVariable String id,@Valid @RequestBody AccountUpdateRequest requestBody, Errors errors){
        Validated.request(errors);
        accountService.updatePassword(id,requestBody);
        return new ResponseEntity(new Response("Sukses","password change"), HttpStatus.OK);
    }

}
