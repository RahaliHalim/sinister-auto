package com.gaconnecte.auxilium.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gaconnecte.auxilium.domain.Notification;


//@SuppressWarnings("unused")
@Repository
public interface NotificationRepository extends JpaRepository<Notification,Integer> {

}
