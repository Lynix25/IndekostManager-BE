package com.indekos.controller;

import com.indekos.dto.request.PaymentNotificationRequest;
import com.indekos.dto.response.NotificationResponse;
import com.indekos.model.Notification;
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

        Notification notification = notificationService.createFromMidtrans(requestBody);
        switch (requestBody.getTransaction_status()){
            case "capture" :
            case "settlement" : notification.setTitle("Pembayaranmu sudah terverifikasi");
                                notification.setBody("Pembayaranmu sudah kami terima, mohon tunggu konfirmasi selanjutnya.");
            break;

            case "pending" : notification.setTitle("Segera Selesaikan Pembayaranmu");
                            notification.setBody("Segera selesaikan pembayaranmu.");
            break;

            default: notification.setTitle("Transaksi anda dalam status " + requestBody.getTransaction_status());
        }
        notificationService.notif(subscriptionClientService.getByUser(notification.getUser()),notification);
        notificationService.save(notification);
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
