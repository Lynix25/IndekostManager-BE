package com.indekos.controller;

import com.indekos.common.helper.GlobalAcceptions;
import com.indekos.dto.request.NotificationCrateRequest;
import com.indekos.model.Account;
import com.indekos.model.SubscriptionClient;
import com.indekos.model.User;
import com.indekos.services.NotificationService;
import com.indekos.dto.response.NotificationResponse;
import com.indekos.dto.request.SubscriptionClientRequest;
import com.indekos.services.SubscriptionClientService;
import com.indekos.services.UserService;
import nl.martijndwars.webpush.Notification;
import org.jose4j.lang.JoseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping(value = "notification")
public class NotificationController {

    @Autowired
    NotificationService notificationService;

    @Autowired
    SubscriptionClientService subscriptionClientService;

    @Autowired
    UserService userService;

    @GetMapping("key")
    public ResponseEntity<?> getPublicKey(){
        return GlobalAcceptions.data("BK6EnS65Dluc5V0n8FnMm0_cXzENJEqUPqTfNZZcyxP4GQrnotBjUe7GuKnb7k39zabI7KM6_vmo0iLeXPa2jbw","Public Access Key");
    }

    @PostMapping("subscribe")
    public ResponseEntity<?> subscribe(@RequestBody SubscriptionClientRequest request) {
        System.out.println(request);
        subscriptionClientService.register(request, userService.getById(request.getRequesterId()));
        return GlobalAcceptions.emptyData("Berhasil Mengaktifkan Notifikasi");
    }

    @PostMapping("unsubscribe/{userId}")
    public ResponseEntity<?> unsubscribe(@PathVariable String userId) {
        User user = userService.getById(userId);
        subscriptionClientService.deleteByUser(user);
        return GlobalAcceptions.emptyData("Berhasil Mematikan Notifikasi");
    }

    @PostMapping("notify")
    public ResponseEntity<?> notify(@RequestBody NotificationCrateRequest request){
        User user = userService.getById(request.getTargetedUserId());
        SubscriptionClient subscriptionClient = subscriptionClientService.getByUser(user);
        notificationService.notif(subscriptionClient, new NotificationResponse(request.getTitle(), request.getRedirect(), request.getMessage()));
        return GlobalAcceptions.data(subscriptionClient,"Memberikan notifikasi ke user");
    }

//    @PostMapping("notify-all")
//    public ResponseEntity<?> notifyAll(@RequestBody NotificationResponse message) throws GeneralSecurityException, IOException, JoseException, ExecutionException, InterruptedException {
//        System.out.println("Notify-all");
//        for (SubscriptionClientRequest subscription: subscriptions.values()) {
//            System.out.println(subscription);
//            nl.martijndwars.webpush.Notification notification = new Notification(
//                    subscription.getEndPoint(),
//                    subscription.getKey(),
//                    subscription.getAuth(),
//                    objectMapper.writeValueAsBytes(message));
//
//            pushService.send(notification);
//        }
//
//        return message;
//    }

}
