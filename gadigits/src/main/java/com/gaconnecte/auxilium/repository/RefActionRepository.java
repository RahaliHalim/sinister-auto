package com.gaconnecte.auxilium.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.gaconnecte.auxilium.domain.NotifAlertUser;
import com.gaconnecte.auxilium.domain.RefAction;


@Repository
public interface RefActionRepository extends JpaRepository<RefAction,Long> {
	
	//@Query("SELECT notifAlertUser from NotifAlertUser notifAlertUser where notifAlertUser.read = false ")
	//List<NotifAlertUser> findAllNotificationsNotRead();

}
