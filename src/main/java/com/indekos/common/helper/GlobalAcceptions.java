package com.indekos.common.helper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.indekos.model.Account;
import com.indekos.model.RememberMeToken;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.indekos.dto.response.LoginResponse;
import com.indekos.dto.response.Response;
import com.indekos.dto.response.TokenSessionResponse;
import com.indekos.model.User;
import com.indekos.utils.Constant;

import java.util.ArrayList;
import java.util.List;

public class GlobalAcceptions {

    static ObjectMapper objectMapper = new ObjectMapper();
	
	private static <D> ResponseEntity<?> response(D data, String message){
        
    	Response<D> response = new Response<>();
        response.setMessage(message);
        response.setData(data);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public static ResponseEntity<?> loginAllowed(Account account, String message, RememberMeToken rememberMeToken){
        TokenSessionResponse token = new TokenSessionResponse(Constant.SECRET + account.getUser().getId(), Constant.EXPIRES_IN);
        LoginResponse.DataResponseDto<User> data = new LoginResponse.DataResponseDto<>();

        data.setStatus("Authenticated");
        data.setLoginTime(System.currentTimeMillis());
        data.setUser(account.getUser());
        data.setToken(token);

        ObjectNode cachedItem = objectMapper.createObjectNode();
        cachedItem.put("id", account.getUser().getId());
        cachedItem.put("role", account.getUser().getRole().getName());
        if(rememberMeToken != null) cachedItem.putPOJO("token", rememberMeToken.getId());

        ObjectNode responseBody = objectMapper.createObjectNode();
        responseBody.put("status", "Authenticated");
        responseBody.put("loginTime", System.currentTimeMillis());
        responseBody.putPOJO("user", account.getUser());
        responseBody.putPOJO("cached",cachedItem);
        return response(responseBody, message);
    }

    public static ResponseEntity<?> logoutSuccess(String message){
        ObjectNode responseBody = objectMapper.createObjectNode();
        ArrayNode deletedCache = objectMapper.createArrayNode();
//        List<String> deletedCache = new ArrayList<>();
        deletedCache.add("id");
        deletedCache.add("role");
        deletedCache.add("token");

        responseBody.putArray("deletedCache").addAll(deletedCache);
        return response(responseBody, message);
    }
    public static <D> ResponseEntity<?> listData(List<D> datas, String message){
        return response(datas, message);
    }

    public static <D> ResponseEntity<?> data(D data, String message){
        return response(data, message);
    }

    public static ResponseEntity<?> emptyData(String message){
        return response("No Data", message);
    }
}