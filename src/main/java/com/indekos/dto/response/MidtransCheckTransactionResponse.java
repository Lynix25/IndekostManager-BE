package com.indekos.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.json.JSONObject;

import java.util.Map;

@Data
public class MidtransCheckTransactionResponse {
    @JsonIgnore
    ObjectMapper mapper = new ObjectMapper();

    private String httpCode;
    private String message;
    private String status;
    private String id;
    private String type;

    public MidtransCheckTransactionResponse(String responseBody){
        try{
            Map<String, String> responseJson = mapper.readValue(responseBody, Map.class);
            httpCode = responseJson.get("status_code");
            message = responseJson.get("status_message");
            id = responseJson.get("id");
            status = "not_selected";
        }catch (Exception e){
            System.out.println(e);
        }
    }

    public MidtransCheckTransactionResponse(JSONObject responseBody){
        try{
            httpCode = responseBody.get("status_code").toString();
            message = responseBody.get("status_message").toString();
            id = responseBody.get("order_id").toString();
            status = responseBody.get("transaction_status").toString();

            String paymentType = responseBody.get("payment_type").toString();
            if(paymentType.equals("bank_transfer")){
                JSONObject jsonObj = new JSONObject(responseBody.getJSONArray("va_numbers").get(0).toString());
                type = jsonObj.get("bank").toString().toUpperCase() +" Virtual Account ("+jsonObj.get("va_number").toString()+")";
            }
            else if(paymentType.equals("echannel")){
                type = "Mandiri Bill ("+responseBody.get("bill_key").toString()+")";
            }
            else{
                type = responseBody.get("payment_type").toString();
            }
        }catch (Exception e){
            System.out.println(e);
        }
    }
}
