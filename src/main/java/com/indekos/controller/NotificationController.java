package com.indekos.controller;

import com.indekos.common.helper.GlobalAcceptions;
import com.indekos.dto.request.NotificationCrateRequest;
import com.indekos.model.Account;
import com.indekos.model.Notification;
import com.indekos.model.SubscriptionClient;
import com.indekos.model.User;
import com.indekos.services.NotificationService;
import com.indekos.dto.response.NotificationResponse;
import com.indekos.dto.request.SubscriptionClientRequest;
import com.indekos.services.SubscriptionClientService;
import com.indekos.services.UserService;
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

    @GetMapping("{userId}")
    public ResponseEntity<?> getAll(@PathVariable String userId){
        User user = userService.getById(userId).getUser();
        return GlobalAcceptions.listData(notificationService.getAllByUser(user), "Notifikasi");
    }

    @PostMapping("subscribe")
    public ResponseEntity<?> subscribe(@RequestBody SubscriptionClientRequest request) {
        User user = userService.getById(request.getRequesterId()).getUser();
        user.getSetting().setEnableNotification(true);
        userService.save(request.getRequesterId(),user);

        subscriptionClientService.register(request, user);
        return GlobalAcceptions.emptyData("Berhasil Mengaktifkan Notifikasi");
    }

    @PostMapping("notify")
    public ResponseEntity<?> notify(@RequestParam String requesterId, @RequestBody NotificationCrateRequest request){
        User user = userService.getById(request.getTargetedUserId()).getUser();

        Notification notification = new Notification(request.getCategory(), request.getTitle(), request.getMessage(), request.getRedirect(), false,user);
        notification.create(requesterId);
        notificationService.save(notification);

        notificationService.notif(notification);
        return GlobalAcceptions.data(notification.getUser(),"Berhasil menotifikasi ke user");
    }

    @PutMapping("{notificationId}")
    public ResponseEntity<?> readNotification(@PathVariable String notificationId, @RequestBody String requesterId){
        Notification notification = notificationService.getById(notificationId);
        notification.update(requesterId);
        notification.setReaded(Boolean.TRUE);
        notificationService.save(notification);

        return GlobalAcceptions.data(notification, "Berhasil membaca notifikasi");
    }

    @DeleteMapping("unsubscribe/{userId}")
    public ResponseEntity<?> unsubscribe(@PathVariable String userId) {
        User user = userService.getById(userId).getUser();
        user.getSetting().setEnableNotification(false);
        userService.save(userId,user);

        subscriptionClientService.deleteByUser(user);
        return GlobalAcceptions.emptyData("Berhasil Mematikan Notifikasi");
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
