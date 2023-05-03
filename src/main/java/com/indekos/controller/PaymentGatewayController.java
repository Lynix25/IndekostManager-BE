package com.indekos.controller;

import com.indekos.dto.request.PaymentNotificationRequest;
import com.indekos.services.ServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("payment")
public class PaymentGatewayController {
    @Autowired
    private ServiceService serviceService;

    @PostMapping("payment")
    public ResponseEntity<?> paymentNotification(@RequestBody PaymentNotificationRequest requestBody){
        System.out.println("===== PAYMENT NOTIFICATION START=====");
        System.out.println(requestBody.getStatus_message());
        System.out.println(requestBody.getTransaction_status());
        System.out.println("===== PAYMENT NOTIFICATION END=====");
        return new ResponseEntity<>(requestBody, HttpStatus.OK);
    }

    @PostMapping("recurring")
    public void recurringNotification(@RequestBody PaymentNotificationRequest request){
        System.out.println("===== PAYMENT RECURRING START=====");
        System.out.println(request.getStatus_message());
        System.out.println(request.getTransaction_status());
        System.out.println("===== PAYMENT RECURRING END=====");
    }

    @PostMapping("pay-account")
    public void payAccountNotification(@RequestBody PaymentNotificationRequest request){
        System.out.println("===== PAY ACCOUNT START=====");
        System.out.println(request.getStatus_message());
        System.out.println(request.getTransaction_status());
        System.out.println("===== PAY ACCOUNT END=====");
    }

}
