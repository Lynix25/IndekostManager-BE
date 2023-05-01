package com.indekos.controller;

import com.indekos.dto.request.PaymentNotificationRequest;
import com.midtrans.Config;
import com.midtrans.ConfigBuilder;
import com.midtrans.ConfigFactory;
import com.midtrans.Midtrans;
import com.midtrans.httpclient.error.MidtransError;
import com.midtrans.service.MidtransSnapApi;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("payment")
public class PaymentGatewayController {
    @PostMapping("payment")
    public ResponseEntity<?> paymentNotification(@RequestBody PaymentNotificationRequest requestBody){
        System.out.println("===== PAYMENT NOTIFICATION =====");
        System.out.println(requestBody.getStatus_message());
        System.out.println(requestBody.getTransaction_status());
        System.out.println("===== PAYMENT NOTIFICATION =====");
        return new ResponseEntity<>(requestBody, HttpStatus.OK);
    }

    @PostMapping("recurring")
    public void recurringNotification(@RequestBody PaymentNotificationRequest request){
        System.out.println("===== PAYMENT RECURRING =====");
        System.out.println(request.getStatus_message());
        System.out.println(request.getTransaction_status());
        System.out.println("===== PAYMENT RECURRING =====");
    }

    @PostMapping("pay-account")
    public void payAccountNotification(@RequestBody PaymentNotificationRequest request){
        System.out.println("===== PAY ACCOUNT =====");
        System.out.println(request.getStatus_message());
        System.out.println(request.getTransaction_status());
        System.out.println("===== PAY ACCOUNT =====");
    }

    private static MidtransSnapApi snapApi;
    private static boolean flag = true;

    @PostMapping("pay")
    public static ResponseEntity<?> cratePayment(@RequestParam String amount) throws MidtransError {
        if(flag){
            String serverKey = "SB-Mid-server-55_HHeWsJikvj9F8llLaDNNf";
            String clientKey = "SB-Mid-client-cGMJz_4wOfQNmcWO";

            Midtrans.serverKey = serverKey;
            Midtrans.clientKey = clientKey;

            ConfigFactory configFactory = new ConfigFactory(new ConfigBuilder().setServerKey(serverKey).setClientKey(clientKey).setIsProduction(false).build());

            snapApi = configFactory.getSnapApi();
            System.out.println("Config Snap API");
            flag = false;
        }
        String idRand = UUID.randomUUID().toString();
        Map<String, Object> params = new HashMap<>();

        Map<String, String> transactionDetails = new HashMap<>();
        transactionDetails.put("order_id", idRand);
        transactionDetails.put("gross_amount", amount);

        Map<String, String> creditCard = new HashMap<>();
        creditCard.put("secure", "true");

        List<String> enablePayment = new ArrayList<>();
        enablePayment.add("credit_card");
        enablePayment.add("gopay");

        params.put("transaction_details", transactionDetails);
//        params.put("enabled_payments", enablePayment);
//        params.put("credit_card", creditCard);

        String response = snapApi.createTransactionToken(params);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
