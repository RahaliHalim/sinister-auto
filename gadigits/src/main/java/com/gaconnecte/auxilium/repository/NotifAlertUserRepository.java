package com.gaconnecte.auxilium.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.gaconnecte.auxilium.domain.NotifAlertUser;


@Repository
public interface NotifAlertUserRepository extends JpaRepository<NotifAlertUser,Long> {
	
	@Query("SELECT notifAlertUser from NotifAlertUser notifAlertUser where notifAlertUser.read = false ")
	List<NotifAlertUser> findAllNotificationsNotReadWithoutUser();
	
	@Query("SELECT notifAlertUser from NotifAlertUser notifAlertUser where notifAlertUser.destination.id =:userId ")
	List<NotifAlertUser> findNotificationsForUser(@Param("userId") Long userId);
	
	@Query("SELECT notifAlertUser from NotifAlertUser notifAlertUser where notifAlertUser.read = false")
	List<NotifAlertUser> findAllNotificationsNotRead();

}
