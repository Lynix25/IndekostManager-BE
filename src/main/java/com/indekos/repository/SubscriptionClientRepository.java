package com.indekos.repository;

import com.indekos.model.SubscriptionClient;
import com.indekos.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SubscriptionClientRepository extends JpaRepository<SubscriptionClient, String> {
    SubscriptionClient findByUser(User user);

}
