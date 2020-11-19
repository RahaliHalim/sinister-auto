package com.gaconnecte.auxilium.service;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import com.gaconnecte.auxilium.service.dto.NotificationDTO;

public interface NotificationService {
	
	NotificationDTO save(NotificationDTO notificationDTO);
	
	Page<NotificationDTO> findAll(Pageable pageable);
	
	NotificationDTO findOne(Integer id);
	
	void delete(Integer id);

}
