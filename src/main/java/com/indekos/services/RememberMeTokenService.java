package com.indekos.services;

import com.indekos.common.helper.exception.InvalidRequestException;
import com.indekos.common.helper.exception.InvalidRequestIdException;
import com.indekos.model.Account;
import com.indekos.model.RememberMeToken;
import com.indekos.repository.RememberMeTokenRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class RememberMeTokenService {
    @Autowired
    RememberMeTokenRepository rememberMeTokenRepository;
    public RememberMeToken getById(String id){
        try {
            return rememberMeTokenRepository.findById(id).get();
        }catch (NoSuchElementException e){
            throw new InvalidRequestIdException("Invalid Token ID");
        }
    }

    public RememberMeToken getByAccount(Account account){
        try{
            return rememberMeTokenRepository.findByAccount(account).get();
        }catch (NoSuchElementException e){
            throw new InvalidRequestException("Invalid Account");
        }
    }

    public RememberMeToken rememberAccount(Account account){
        RememberMeToken rememberMeToken = new RememberMeToken(account);
        rememberMeToken.create("System");
        save(rememberMeToken);
        return rememberMeToken;
    }

    public boolean deleteByAccount(Account account){
        try {
            RememberMeToken rememberMeToken = getByAccount(account);
            rememberMeTokenRepository.delete(rememberMeToken);
        }catch (Exception e){
            System.out.println(e);
            System.out.println("User Not Login With Remember Me");
            return false;
        }
        return true;
    }

    private void save(RememberMeToken rememberMeToken){
        try {
            rememberMeToken.update("system");
            rememberMeTokenRepository.save(rememberMeToken);
        }
        catch (DataIntegrityViolationException e){
            System.out.println(e);
        }
        catch (Exception e){
            System.out.println(e);
            throw new RuntimeException();
        }
    }


}
