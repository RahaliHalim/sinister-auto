package com.gaconnecte.auxilium.service.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.gaconnecte.auxilium.domain.NotifAlertUser;

import com.gaconnecte.auxilium.service.dto.NotifAlertUserDTO;
@Mapper(componentModel = "spring", uses = {UserProfileMapper.class, UserExtraMapper.class, RefNotifAlertMapper.class})
public interface NotifAlertUserMapper extends EntityMapper <NotifAlertUserDTO, NotifAlertUser> {
	
	@Mapping(source = "source.id", target = "source")
    @Mapping(source = "destination.id", target = "destination")
    @Mapping(source = "profile.id", target = "profil")
    //@Mapping(source = "notification.id", target = "notification")

    NotifAlertUserDTO toDto(NotifAlertUser notifAlertUser); 

    @Mapping(source = "source", target = "source")
    @Mapping(source = "destination", target = "destination")
    @Mapping(source = "profil", target = "profile")
    //@Mapping(source = "notification", target = "notification")

	NotifAlertUser toEntity(NotifAlertUserDTO notifAlertUserDTO); 
    default NotifAlertUser fromId(Long id) {
        if (id == null) {
            return null;
        }
        NotifAlertUser notifAlertUser = new NotifAlertUser();
        notifAlertUser.setId(id);
        return notifAlertUser;
    }

}
