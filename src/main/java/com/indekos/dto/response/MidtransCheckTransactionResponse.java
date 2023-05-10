package com.indekos.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.indekos.model.Transaction;
import lombok.Data;
import lombok.Getter;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;

import java.util.Map;

@Data
public class MidtransCheckTransactionResponse {
    @JsonIgnore
    ObjectMapper mapper = new ObjectMapper();

    private String statusCode;
    private String statusMessage;
    private String id;
    private Transaction transaction;

    public MidtransCheckTransactionResponse(String responseBody){
        try{
            Map<String, String> responseJson = mapper.readValue(responseBody, Map.class);
            statusCode = responseJson.get("status_code");
            statusMessage = responseJson.get("status_message");
            id = responseJson.get("id");
        }catch (Exception e){
            System.out.println(e);
        }
    }

    public MidtransCheckTransactionResponse(JSONObject responseBody){
        try{
            statusCode = responseBody.get("status_code").toString();
            statusMessage = responseBody.get("status_message").toString();
            id = responseBody.get("order_id").toString();
        }catch (Exception e){
            System.out.println(e);
        }
    }
}
