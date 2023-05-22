package com.indekos.repository;

import com.indekos.model.Notification;
import com.indekos.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, String> {
    List<Notification> findAllByUserOrderByCreatedDateDesc(User user);

    Optional<Notification> findByUser(User user);
}
