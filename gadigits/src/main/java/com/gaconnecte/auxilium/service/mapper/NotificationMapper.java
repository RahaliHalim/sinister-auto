package com.gaconnecte.auxilium.service.mapper;


import org.mapstruct.Mapper;

import com.gaconnecte.auxilium.domain.Notification;

import com.gaconnecte.auxilium.service.dto.NotificationDTO;
@Mapper(componentModel = "spring", uses = {})
public interface NotificationMapper extends EntityMapper <NotificationDTO, Notification> {
	
	
	Notification toEntity(NotificationDTO NotificationDTO); 
    default Notification fromId(Long id) {
        if (id == null) {
            return null;
        }
        Notification notification = new Notification();
        notification.setId(id);
        return notification;
    }

}
