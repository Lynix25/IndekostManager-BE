package com.indekos.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.indekos.dto.request.PaymentNotificationRequest;
import com.indekos.model.Notification;
import com.indekos.model.SubscriptionClient;
import com.indekos.model.User;
import com.indekos.repository.NotificationRepository;
import nl.martijndwars.webpush.PushService;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.security.GeneralSecurityException;
import java.security.Security;

@Service
public class NotificationService {
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private NotificationRepository notificationRepository;
    @Autowired
    private TransactionService transactionService;
    private static PushService pushService;
    @PostConstruct
    private void init() throws GeneralSecurityException {
        Security.addProvider(new BouncyCastleProvider());
        pushService = new PushService(System.getenv("VAPID_PUBLIC_KEY"),System.getenv("VAPID_PRIVATE_KEY"));
    }

    public void notif(SubscriptionClient client, Notification message){
        try {
            nl.martijndwars.webpush.Notification notification = new nl.martijndwars.webpush.Notification(
                    client.getEndPoint(),
                    client.getPublicKey(),
                    client.getAuth(),
                    objectMapper.writeValueAsBytes(message));
            System.out.println("Push Notif ");
            pushService.send(notification);
        }catch (Exception e){
            System.out.println(e);
        }
    }
    public void registerUser(){

    }

    public Notification createFromMidtrans(PaymentNotificationRequest request){
        User user = transactionService.getByID(request.getTransaction_id()).getUser();
        Notification notification = new Notification("Category", "Title", "Body", "/home.html", user);
        notification.create("Midtrans");
        return notification;
    }

    public Notification save(Notification notification){
        try {
            return notificationRepository.save(notification);
        }
        catch (DataIntegrityViolationException e){
            System.out.println(e);
        }
        catch (Exception e){
            System.out.println(e);
            throw new RuntimeException();
        }
        return null;
    }
}
