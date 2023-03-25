package com.indekos.services;

import com.indekos.common.helper.exception.InvalidRequestException;
import com.indekos.common.helper.exception.InvalidRequestIdException;
import com.indekos.common.helper.exception.InvalidUserCredentialException;
import com.indekos.common.helper.exception.UnauthorizedException;
import com.indekos.dto.request.AccountChangePasswordRequest;
import com.indekos.model.Account;
import com.indekos.model.User;
import com.indekos.repository.AccountRepository;
import com.indekos.utils.Utils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AccountService {
	
    @Autowired
    ModelMapper modelMapper;
    
    @Autowired
    AccountRepository accountRepository;
    
    public List<Account> getAll(){
        return accountRepository.findAll();
    }
    
    public Account getById(String accountId){
        Account account = accountRepository.findById(accountId)
        		.orElseThrow(() -> new InvalidRequestIdException("Invalid Account ID"));
    
        return account;
    }
    
    public Account getByUsername(String username){
    	Account account = accountRepository.findByUsername(username);
    	if(account == null)
    		throw new InvalidRequestIdException("Invalid Username");
    	
    	return account;
    }
    
    public Account getByUser(User user) {
    	Account account = accountRepository.findByUser(user);
    	if(account == null)
    		throw new InvalidRequestIdException("This user doesn't have an account. Please contact owner/ admin.");
    	
    	return account;
    }
    
    public Account register(User user){
    	String credential = createCredential(user);
        modelMapper.typeMap(User.class, Account.class).addMappings(mapper -> {
           mapper.map(src -> credential, Account::setUsername);
           mapper.map(src -> Utils.passwordHashing(credential), Account::setPassword);
        });
        Account account = modelMapper.map(user, Account.class);
        save(account);
        return account;
    }
    
    public Account changePassword(String accountId, AccountChangePasswordRequest requestData){
    	if(!(requestData.getRequesterIdUser()).equals(getById(accountId).getUser().getId()))
    		throw new UnauthorizedException("You're not authorized to change the password because not account owner");
        if (requestData.getNewPassword().compareTo(requestData.getReTypeNewPassword()) != 0){
            throw new InvalidRequestException("Miss match retype password", new ArrayList<>());
        }
        Account account = getById(accountId);
        if(account.getPassword().compareTo(Utils.passwordHashing(requestData.getOldPassword())) != 0){
            throw new InvalidUserCredentialException("Wrong old password");
        }
        account.setPassword(Utils.passwordHashing(requestData.getNewPassword()));
        accountRepository.save(account);
        return account;
    }
    
    public String createCredential(User user){
    	/* Default: username = password = lowercase(firstname) + 3 digits random number */
    	String[] firstName = (user.getName()).split("\\s+");
    	String credential = "";
    	
    	while (credential.equals("") || accountRepository.findByUsername(credential) != null) {
    		System.out.println("Searching for unique number... ");
    		credential = (firstName[0]).toLowerCase() + String.valueOf((int)(Math.random() * (999 - 100) + 100));
		}
    	return credential;
    }
    
    public boolean comparePasswordTo(Account account, String anotherPassword){
        if(account.getPassword().compareTo(Utils.passwordHashing(anotherPassword)) == 0){
            return true;
        }
        return false;
    }
    
    private void save(Account account){
        try {
            accountRepository.save(account);
        }
        catch (DataIntegrityViolationException e){
            System.out.println(e);
            throw new InvalidRequestException("Duplicate Data");
        }
        catch (Exception e){
            System.out.println(e);
        }
    }
}