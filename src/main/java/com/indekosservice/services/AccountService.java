package com.indekosservice.services;

import com.indekosservice.dto.request.AccountUpdateRequest;
import com.indekosservice.dto.request.AccountRegisterRequest;
import com.indekosservice.helper.exception.InvalidUserCredentialException;
import com.indekosservice.model.Account;
import com.indekosservice.repository.AccountRepository;
import com.indekosservice.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class AccountService {
    @Autowired
    AccountRepository accountRepository;
    public Account getByUsername(String userName){
        Account account = null;
        try {
            account = accountRepository.findByUsername(userName).get();
        }catch (NoSuchElementException e){
            throw new InvalidUserCredentialException("Invalid username");
        }
        return account;
    }

    public Account register(AccountRegisterRequest accountRegisterRequest){
        Account account = new Account();
        account.setAccoutID(UUID.randomUUID().toString());
        account.setUsername(accountRegisterRequest.getUsername());
        account.setPassword(Utils.passwordHashing(accountRegisterRequest.getPassword()));
        System.out.println(account.getAccoutID());
        accountRepository.save(account);
        return account;
    }

    public Account linkToUser(AccountUpdateRequest accountUpdateRequest){
        Account account = accountRepository.findById(accountUpdateRequest.getAccountID()).get();
        account.setUserID(accountUpdateRequest.getUserID());
        accountRepository.save(account);
        return account;
    }

    public Account update(){
        Account account = accountRepository.findById("null").get();
        account.setPassword("null");
        account.setUserID("null");

        return account;
    }




    public List<Account> allUser(){
        return accountRepository.findAll();
    }
}
