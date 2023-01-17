package com.indekos.utils;

import com.indekos.common.helper.exception.InvalidRequestException;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;

import java.util.ArrayList;
import java.util.List;

public class Validated {
    public static void request (Errors errors){
        if(errors.hasErrors()){
            List<String> errorList = new ArrayList<>();
            for (ObjectError error:errors.getAllErrors()) {
                errorList.add(error.getDefaultMessage());
            }
            throw new InvalidRequestException("Invalid Request Body", errorList);
        }
    }
}
