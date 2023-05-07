package com.indekos.services;

import com.indekos.common.helper.exception.InvalidRequestIdException;
import com.indekos.dto.request.SubscriptionClientRequest;
import com.indekos.model.SubscriptionClient;
import com.indekos.model.User;
import com.indekos.model.UserSetting;
import com.indekos.repository.SubscriptionClientRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class SubscriptionClientService {
    @Autowired
    ModelMapper modelMapper;

    @Autowired
    SubscriptionClientRepository subscriptionClientRepository;
    public void register(SubscriptionClientRequest client, User user){
        SubscriptionClient subscriptionClient = modelMapper.map(client, SubscriptionClient.class);
        subscriptionClient.create(client.getRequesterId());
        subscriptionClient.setUser(user);

        save(client.getRequesterId(), subscriptionClient);
    }

    public boolean deleteByUser(User user){
        try {
            SubscriptionClient subscriptionClient = getByUser(user);
            subscriptionClientRepository.delete(subscriptionClient);
        }catch (Exception e){
            System.out.println(e);
            System.out.println("Fail to delete subscriptionClient");
            return false;
        }

        return true;
    }

    public SubscriptionClient getByUser(User user){
        SubscriptionClient subscriptionClient = subscriptionClientRepository.findByUser(user);
        if(subscriptionClient == null)
            throw new InvalidRequestIdException("This user doesn't have subscription.");

        return subscriptionClient;
    }

    private SubscriptionClient save(String modifierId, SubscriptionClient client){
        try {
            client.update(modifierId);
            return subscriptionClientRepository.save(client);
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
