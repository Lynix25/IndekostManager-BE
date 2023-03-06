package com.indekos.controller;

import com.indekos.common.helper.GlobalAcceptions;
import com.indekos.common.helper.exception.InvalidUserCredentialException;
import com.indekos.dto.DataIdDTO;
import com.indekos.dto.request.AccountChangePasswordRequest;
import com.indekos.dto.request.AccountRegisterRequest;
import com.indekos.dto.request.AccountLoginRequest;
import com.indekos.dto.response.Response;
import com.indekos.model.Account;
import com.indekos.model.User;
import com.indekos.services.AccountService;
import com.indekos.services.UserService;
import com.indekos.utils.Validated;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/account")
//@CrossOrigin(origins = "http://127.0.0.1:5500")
public class AccountController {
    @Autowired
    private AccountService accountService;
    @Autowired
    private UserService userService;
    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity getAllAccount(){
        List<Account> allAccount = accountService.allUser();
        return GlobalAcceptions.listData(allAccount, "Success");
    }
    @PostMapping
    public ResponseEntity register(@Valid @RequestBody AccountRegisterRequest accountRegisterRequest, Errors errors){
        Validated.request(errors);

        accountService.register(accountRegisterRequest);

        return new ResponseEntity(new Response("Sukses", "Success to register"), HttpStatus.OK);
    }
    @PostMapping("/login")
    public ResponseEntity login (@Valid @RequestBody AccountLoginRequest accountLoginRequest, Errors errors){
        Validated.request(errors);

        Account account = accountService.getByUsername(accountLoginRequest.getUsername());
        if(accountService.comparePasswordTo(account, accountLoginRequest.getPassword())){
//            User user = userService.getByAccountId(account.getId());
            User user = account.getUser();
            return GlobalAcceptions.loginAllowed(user, "Success");
        }

        throw new InvalidUserCredentialException("Invalid username or password");
    }
    @PutMapping("/{id}")
    public ResponseEntity updatePassword(@PathVariable String id, @Valid @RequestBody AccountChangePasswordRequest requestBody, Errors errors){
        Validated.request(errors);

        DataIdDTO data = modelMapper.map(accountService.updatePassword(id,requestBody), DataIdDTO.class);

        return GlobalAcceptions.data(data, "Success changes password");
    }
}
