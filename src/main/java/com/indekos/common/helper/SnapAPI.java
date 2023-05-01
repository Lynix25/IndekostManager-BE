package com.indekos.common.helper;

import com.indekos.services.TransactionService;
import com.midtrans.ConfigBuilder;
import com.midtrans.ConfigFactory;
import com.midtrans.Midtrans;
import com.midtrans.proxy.ProxyConfig;
import com.midtrans.service.MidtransSnapApi;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

public class SnapAPI {
    @Autowired
    TransactionService transactionService;
    private static MidtransSnapApi snapApi;

    private static MidtransSnapApi init(){
        String serverKey = "SB-Mid-server-55_HHeWsJikvj9F8llLaDNNf";
        String clientKey = "SB-Mid-client-cGMJz_4wOfQNmcWO";

        Midtrans.serverKey = serverKey;
        Midtrans.clientKey = clientKey;

        ConfigFactory configFactory = new ConfigFactory(new ConfigBuilder().setServerKey(serverKey).setClientKey(clientKey).setIsProduction(false).build());

        return configFactory.getSnapApi();
    }

    public static MidtransSnapApi getInstance(){
        if (snapApi == null){
            snapApi = init();
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
            return getInstance().createTransactionToken(params);
        }catch (Exception e){
            System.out.println(e);
            throw new RuntimeException("Midtrans API ERROR");
        }
    }
}
