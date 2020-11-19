package com.gaconnecte.auxilium.repository;

import java.time.LocalDate;
import java.util.Set;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.gaconnecte.auxilium.domain.view.ViewNotification;
import com.gaconnecte.auxilium.service.dto.ViewNotificationDTO;
@SuppressWarnings("unused")
@Repository
public interface ViewNotificationRepository extends JpaRepository<ViewNotification,Long>{
	
	
	@Query("SELECT Vnotif from ViewNotification Vnotif where Vnotif.read = false AND Vnotif.destination =:userId AND Vnotif.type = :type")
	Set<ViewNotification> findAllNotificationByUser(@Param("userId") Long userId,@Param("type") String type);
	

}
