package com.indekos.common.helper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.indekos.dto.response.MidtransCheckTransactionResponse;
import com.indekos.services.TransactionService;
import com.midtrans.ConfigBuilder;
import com.midtrans.ConfigFactory;
import com.midtrans.Midtrans;
import com.midtrans.httpclient.error.MidtransError;
import com.midtrans.service.MidtransCoreApi;
import com.midtrans.service.MidtransSnapApi;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

public class SnapAPI {
    @Autowired
    TransactionService transactionService;
    private static MidtransSnapApi snapApi;
    private static MidtransCoreApi coreApi;

    private static MidtransSnapApi initSnap(){
        String serverKey = "SB-Mid-server-55_HHeWsJikvj9F8llLaDNNf";
        String clientKey = "SB-Mid-client-cGMJz_4wOfQNmcWO";

        Midtrans.serverKey = serverKey;
        Midtrans.clientKey = clientKey;

        ConfigFactory configFactory = new ConfigFactory(new ConfigBuilder().setServerKey(serverKey).setClientKey(clientKey).setIsProduction(false).build());
        return configFactory.getSnapApi();
    }

    private static MidtransCoreApi initCore(){
        String serverKey = "SB-Mid-server-55_HHeWsJikvj9F8llLaDNNf";
        String clientKey = "SB-Mid-client-cGMJz_4wOfQNmcWO";

        Midtrans.serverKey = serverKey;
        Midtrans.clientKey = clientKey;

        ConfigFactory configFactory = new ConfigFactory(new ConfigBuilder().setServerKey(serverKey).setClientKey(clientKey).setIsProduction(false).build());
        return configFactory.getCoreApi();
    }

    public static MidtransCoreApi getInstanceCore(){
        if (coreApi == null){
            coreApi = initCore();
        }

        return coreApi;
    }


    public static MidtransSnapApi getInstanceSnap(){
        if (snapApi == null){
            snapApi = initSnap();
        }

        return snapApi;
    }

    public static String createTransaction(String id,Integer amount){
        Map<String, String> transactionDetails = new HashMap<>();
        transactionDetails.put("order_id", id);
        transactionDetails.put("gross_amount", amount.toString());

        Map<String, Object> params = new HashMap<>();
        params.put("transaction_details", transactionDetails);

        try {
            return getInstanceSnap().createTransactionToken(params);
        }catch (Exception e){
            System.out.println(e);
            throw new RuntimeException("Midtrans Create API ERROR");
        }
    }

    public static MidtransCheckTransactionResponse checkTransaction(String orderId){
        MidtransCheckTransactionResponse response;
        try{
            response = new MidtransCheckTransactionResponse(getInstanceCore().checkTransaction(orderId));
        }catch (MidtransError e){
            response = new MidtransCheckTransactionResponse(e.getResponseBody());
            System.out.println("Midtrans Check API ERROR");
        }
        return response;
    }
}
