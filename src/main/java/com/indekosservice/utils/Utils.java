package com.indekosservice.utils;

import org.apache.commons.codec.digest.DigestUtils;

public class Utils {
    public static String passwordHashing(String plainPassword){
        String hastedPassword = DigestUtils.sha256Hex(plainPassword);
        return hastedPassword;
    }

//    public static ResponseEntity requestValidation(Errors errors){
//
//
//        throw new InvalidRequestException("Request Body Not Satified");
//    }
}
