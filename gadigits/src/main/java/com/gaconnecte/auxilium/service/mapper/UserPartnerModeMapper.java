package com.gaconnecte.auxilium.service.mapper;

import com.gaconnecte.auxilium.domain.*;
import com.gaconnecte.auxilium.service.dto.UserPartnerModeDTO;


import org.mapstruct.*;

/**
 * Mapper for the entity UserPartnerMode and its DTO UserPartnerModeDTO.
 */
@Mapper(componentModel = "spring", uses = {PartnerMapper.class, RefModeGestionMapper.class, UserMapper.class, UserExtraMapper.class, AgencyMapper.class})
public interface UserPartnerModeMapper extends EntityMapper <UserPartnerModeDTO, UserPartnerMode> {


    @Mapping(source = "partner.id", target = "partnerId")
    @Mapping(source = "modeGestion.id", target = "modeId")
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "userExtra.id", target = "userExtraId")
    @Mapping(source = "agency.id", target = "courtierId")

    UserPartnerModeDTO toDto(UserPartnerMode userPartnerMode); 
    
    @Mapping(source = "partnerId", target = "partner")
    @Mapping(source = "modeId", target = "modeGestion")
    @Mapping(source = "userId", target = "user")
    @Mapping(source = "userExtraId", target = "userExtra")
    @Mapping(source = "courtierId", target = "agency")

    UserPartnerMode toEntity(UserPartnerModeDTO userPartnerModeDTO); 
    default UserPartnerMode fromId(Long id) {
        if (id == null) {
            return null;
        }
        UserPartnerMode userPartnerMode = new UserPartnerMode();
        userPartnerMode.setId(id);
        return userPartnerMode;
    }
}
