package com.gaconnecte.auxilium.service.mapper;

import com.gaconnecte.auxilium.domain.*;
import com.gaconnecte.auxilium.service.dto.UserExtraDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity UserExtra and its DTO UserExtraDTO.
 */

@Mapper(componentModel = "spring", uses = {UserMapper.class, UserProfileMapper.class, UserAccessMapper.class, UserPartnerModeMapper.class})

public interface UserExtraMapper extends EntityMapper <UserExtraDTO, UserExtra> {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.login", target = "userLogin")
     @Mapping(source = "user.firstName", target = "firstName")
    @Mapping(source = "user.lastName", target = "lastName")
    @Mapping(source = "user.email", target = "email")
    @Mapping(source = "user.activated", target = "activated")
    
    @Mapping(source = "profile.id", target = "profileId")
    @Mapping(source = "profile.label", target = "profileName")
    UserExtraDTO toDto(UserExtra userExtra); 

    @Mapping(source = "userId", target = "user")
    @Mapping(source = "profileId", target = "profile")
    @Mapping(target = "accesses", ignore = true)
    UserExtra toEntity(UserExtraDTO userExtraDTO); 
    default UserExtra fromId(Long id) {
        if (id == null) {
            return null;
        }
        UserExtra userExtra = new UserExtra();
        userExtra.setId(id);
        return userExtra;
    }
}
