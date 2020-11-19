package com.gaconnecte.auxilium.service.mapper;

import org.mapstruct.Mapper;

import com.gaconnecte.auxilium.domain.view.ViewNotification;
import com.gaconnecte.auxilium.service.dto.ViewNotificationDTO;

@Mapper(componentModel = "spring", uses = {})
public interface ViewNotifcationMapper  extends EntityMapper <ViewNotificationDTO, ViewNotification>{

	
	default ViewNotification fromId(Long id) {
        if (id == null) {
            return null;
        }
        ViewNotification viewNotification = new ViewNotification();
        viewNotification.setId(id);
        return viewNotification;
    }
	
	
}
