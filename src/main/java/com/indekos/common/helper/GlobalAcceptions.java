package com.indekos.common.helper;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.indekos.common.helper.exception.InvalidUserCredentialException;
import com.indekos.dto.response.LoginResponse;
import com.indekos.dto.response.Response;
import com.indekos.dto.response.TokenSessionResponse;
import com.indekos.model.User;
import com.indekos.utils.Constant;

import java.util.List;

public class GlobalAcceptions {
	
	private static <D> ResponseEntity<?> successResponse(D data, String message){
        
    	Response<D> response = new Response<>();
        response.setMessage(message);
        response.setData(data);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public static ResponseEntity<?> loginAllowed(User user, String message){
    	if(user == null || user.isDeleted()) {
        	throw new InvalidUserCredentialException("User not registered");
        }
        TokenSessionResponse token = new TokenSessionResponse(Constant.SECRET + user.getId(), Constant.EXPIRES_IN);
        LoginResponse.DataResponseDto<User> data = new LoginResponse.DataResponseDto<>();
        data.setStatus("Authenticated");
        data.setLoginTime(System.currentTimeMillis());
        data.setUser(user);
        data.setToken(token);
        return successResponse(data, message);
    }

    public static <D> ResponseEntity<?> listData(List<D> datas, String message){
        return successResponse(datas, message);
    }

    public static <D> ResponseEntity<?> data(D data, String message){
        return successResponse(data, message);
    }

    public static ResponseEntity<?> emptyData(String message){
        return successResponse("No Data", message);
    }
}