package com.gaconnecte.auxilium.service.mapper;


import org.mapstruct.Mapper;

import com.gaconnecte.auxilium.domain.RefNotifAlert;

import com.gaconnecte.auxilium.service.dto.RefNotifAlertDTO;
@Mapper(componentModel = "spring", uses = {NotifAlertUserMapper.class})
public interface RefNotifAlertMapper extends EntityMapper <RefNotifAlertDTO, RefNotifAlert> {
	
	
	RefNotifAlert toEntity(RefNotifAlertDTO refNotifAlertDTO); 
    default RefNotifAlert fromId(Long id) {
        if (id == null) {
            return null;
        }
        RefNotifAlert refNotifAlert = new RefNotifAlert();
        refNotifAlert.setId(id);
        return refNotifAlert;
    }

}
