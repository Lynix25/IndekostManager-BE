package com.indekos.controller;

import com.indekos.dto.request.PaymentNotificationRequest;
import com.indekos.dto.response.NotificationResponse;
import com.indekos.services.NotificationService;
import com.indekos.services.ServiceService;
import com.indekos.services.SubscriptionClientService;
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

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private SubscriptionClientService subscriptionClientService;

    @PostMapping("payment")
    public ResponseEntity<?> paymentNotification(@RequestBody PaymentNotificationRequest requestBody){
        System.out.println("===== PAYMENT NOTIFICATION START=====");
        System.out.println(requestBody.getStatus_message());
        System.out.println(requestBody.getTransaction_status());
        System.out.println("===== PAYMENT NOTIFICATION END=====");

        NotificationResponse notificationResponse;
        switch (requestBody.getTransaction_status()){
            case "capture" :
            case "settlement" : notificationResponse = new NotificationResponse("Pembayaranmu sudah terverifikasi", "","Pembayaranmu sudah kami terima, mohon tunggu konfirmasi selanjutnya.");
            break;

            case "pending" : notificationResponse = new NotificationResponse("Segera Selesaikan Pembayaranmu", "","Segera selesaikan pembayaranmu.");
            break;

            default: notificationResponse = new NotificationResponse("Transaksi anda dalam status " + requestBody.getTransaction_status(), "", "");
        }

        notificationService.notif(subscriptionClientService.getById("4a534fb8-f32d-4cdf-ba5e-3fd72d74dcb9"),notificationResponse);
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
