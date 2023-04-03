package com.indekos.services;

import com.indekos.common.helper.exception.InvalidRequestException;
import com.indekos.common.helper.exception.InvalidRequestIdException;
import com.indekos.common.helper.exception.InvalidUserCredentialException;
import com.indekos.dto.request.AccountChangePasswordRequest;
import com.indekos.dto.request.AccountForgotPasswordRequest;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    
    public Account changePassword(User user, AccountChangePasswordRequest requestData){
    	Account account = getByUser(user);
        if(account.getPassword().compareTo(Utils.passwordHashing(requestData.getOldPassword())) != 0){
            throw new InvalidUserCredentialException("Wrong old password");
        }
        if (requestData.getNewPassword().compareTo(requestData.getReTypeNewPassword()) != 0){
            throw new InvalidRequestException("Miss match retype password", new ArrayList<>());
        }
        if(requestData.getNewPassword().length() < 8 || !isAlphaNumeric(requestData.getNewPassword()))
        	throw new InvalidRequestException("Password length must contain at least 8 characters in alphanumeric");
        account.setPassword(Utils.passwordHashing(requestData.getNewPassword()));
        accountRepository.save(account);
        return account;
    }
    
    public Account forgotPassword(AccountForgotPasswordRequest requestData){
    	Account account = accountRepository.findByUsername(requestData.getUsername());
    	if(account == null)
    		throw new InvalidRequestException("Invalid username");
    	
    	if (requestData.getNewPassword().compareTo(requestData.getReTypeNewPassword()) != 0){
            throw new InvalidRequestException("Miss match retype password", new ArrayList<>());
        }
    	 if(requestData.getNewPassword().length() < 8 || !isAlphaNumeric(requestData.getNewPassword()))
         	throw new InvalidRequestException("Password length must contain at least 8 characters in alphanumeric");
        account.setPassword(Utils.passwordHashing(requestData.getNewPassword()));
        accountRepository.save(account);
        return account;
    }
    
    public String createCredential(User user){
    	/* Default: username = password = alphanumeric generated lowercase(firstname) + 3 digits random number */
    	/*  */
    	String[] splittedName = (user.getName()).split("\\s+");
    	String chosenName = "";
    	for (String name : splittedName) {
			if(name.length() > 3) {
				chosenName = name;
				break;
			}
		}
    	
    	if(chosenName.equals("")) chosenName = "user";
    	
    	String credential = "";
    	while (credential.equals("") || accountRepository.findByUsername(credential) != null) {
    		credential = (chosenName).toLowerCase() + String.valueOf((int)(Math.random() * (999 - 100) + 100));
		}
    	return credential;
    }
    
    public static boolean isAlphaNumeric(String str) {
    	if (str == null || str.equals("")) return false;
        
    	// Regex to check string is alphanumeric or not.
        String regex = "^(?=.*[a-zA-Z])(?=.*[0-9])[A-Za-z0-9]+$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(str);
        
        Integer countAlpha = 0;
        Integer countNumber = 0;
        Integer len = str.length();
        if(m.matches()) {
        	for (int i = 0; i < len; i++) {
				if(Character.isAlphabetic(str.charAt(i))) countAlpha++;
				else countNumber++;
			}
        }
        if(countAlpha > 0 && countNumber > 0) return true;
        return false;
    }
    
    public boolean comparePasswordTo(Account account, String anotherPassword){
        if(account.getPassword().compareTo(Utils.passwordHashing(anotherPassword)) == 0){
            return true;
        }
        return false;
    }
    
    public void save(Account account){
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