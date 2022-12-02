package com.indekosservice.utils;

import org.apache.commons.codec.digest.DigestUtils;

public class Utils {
    public static String passwordHashing(String plainPassword){
        String hastedPassword = DigestUtils.sha256Hex(plainPassword);
        return hastedPassword;
    }
}
