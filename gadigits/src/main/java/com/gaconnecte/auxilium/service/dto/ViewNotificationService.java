package com.gaconnecte.auxilium.service.dto;

import java.util.List;
import java.util.Set;

public interface ViewNotificationService {
	
	Set<ViewNotificationDTO> findAllNotificationByUser(String type);
	

}
