package com.indekos.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.indekos.common.helper.exception.InvalidRequestException;
import com.indekos.common.helper.exception.InvalidRequestIdException;
import com.indekos.dto.request.PaymentNotificationRequest;
import com.indekos.model.Notification;
import com.indekos.model.SubscriptionClient;
import com.indekos.model.User;
import com.indekos.repository.NotificationRepository;
import nl.martijndwars.webpush.PushService;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.security.GeneralSecurityException;
import java.security.Security;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class NotificationService {
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private NotificationRepository notificationRepository;
    @Autowired
    private SubscriptionClientService subscriptionClientService;

    private static PushService pushService;
    @PostConstruct
    private void init() throws GeneralSecurityException {
        Security.addProvider(new BouncyCastleProvider());
        pushService = new PushService(System.getenv("VAPID_PUBLIC_KEY"),System.getenv("VAPID_PRIVATE_KEY"));
    }

    @Async
    public void notif(Notification message){
        try {
            System.out.println("Start Send Notify");
            SubscriptionClient client = subscriptionClientService.getByUser(message.getUser());
            nl.martijndwars.webpush.Notification notification = new nl.martijndwars.webpush.Notification(
                    client.getEndPoint(),
                    client.getPublicKey(),
                    client.getAuth(),
                    objectMapper.writeValueAsBytes(message));

            pushService.send(notification);
            System.out.println("Finish Send Notify");
        }catch (Exception e){
            System.out.println("Cancel Send Notify");
            System.out.println(e);
        }
    }
    public List<Notification> getAllByUser(User user){
        return notificationRepository.findAllByUserOrderByCreatedDateDesc(user);
    }

    public Notification getByUser(User user){
        try{
            return notificationRepository.findByUser(user).get();
        }catch (NoSuchElementException e){
            throw new InvalidRequestException("Invalid Notification");
        }
    }

    public Notification getById(String id){
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new InvalidRequestIdException("Invalid Notification ID : " + id));

        return notification;
    }

    public Notification createFromMidtrans(PaymentNotificationRequest request, User user){
        Notification notification = new Notification("Category", "Title", "Body", "/home.html", false, user);
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
