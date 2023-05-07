package com.indekos.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.indekos.dto.response.NotificationResponse;
import com.indekos.model.SubscriptionClient;
import nl.martijndwars.webpush.Notification;
import nl.martijndwars.webpush.PushService;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.security.GeneralSecurityException;
import java.security.Security;

@Service
public class NotificationService {
    @Autowired
    private ObjectMapper objectMapper;
    private static PushService pushService;
    @PostConstruct
    private void init() throws GeneralSecurityException {
        Security.addProvider(new BouncyCastleProvider());
        pushService = new PushService(System.getenv("VAPID_PUBLIC_KEY"),System.getenv("VAPID_PRIVATE_KEY"));
    }

    public void notif(SubscriptionClient client, NotificationResponse message){
        try {
            Notification notification = new Notification(
                    client.getEndPoint(),
                    client.getPublicKey(),
                    client.getAuth(),
                    objectMapper.writeValueAsBytes(message));

            pushService.send(notification);
        }catch (Exception e){
            System.out.println(e);
        }
    }
    public void registerUser(){

    }
}
