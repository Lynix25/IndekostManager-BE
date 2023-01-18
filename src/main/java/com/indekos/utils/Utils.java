package com.indekos.utils;

import org.apache.commons.codec.digest.DigestUtils;

import java.util.UUID;

public class Utils {
    public static String passwordHashing(String plainPassword){
        return DigestUtils.sha256Hex(plainPassword);
    }

    public static String UUID4(){
        return UUID.randomUUID().toString();
    }

}
