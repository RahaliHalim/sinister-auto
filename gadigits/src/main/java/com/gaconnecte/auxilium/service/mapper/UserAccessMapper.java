package com.gaconnecte.auxilium.service.mapper;

import com.gaconnecte.auxilium.domain.*;
import com.gaconnecte.auxilium.service.dto.UserAccessDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity UserAccess and its DTO UserAccessDTO.
 */
@Mapper(componentModel = "spring", uses = {BusinessEntityMapper.class, FunctionalityMapper.class, ElementMenuMapper.class, UserMapper.class, UserProfileMapper.class, })
public interface UserAccessMapper extends EntityMapper <UserAccessDTO, UserAccess> {

    @Mapping(source = "businessEntity.id", target = "businessEntityId")

    @Mapping(source = "functionality.id", target = "functionalityId")

    @Mapping(source = "elementMenu.id", target = "elementMenuId")
    
    @Mapping(source = "elementMenu.label", target = "elementMenuLabel")

    @Mapping(source = "profile.id", target = "profileId")
    
    @Mapping(source = "profile.label", target = "profileLabel")
            
    //@Mapping(source = "user.id", target = "userId")
    //@Mapping(source = "user.login", target = "userLogin")
    UserAccessDTO toDto(UserAccess userAccess); 

    @Mapping(source = "businessEntityId", target = "businessEntity")

    @Mapping(source = "functionalityId", target = "functionality")

    @Mapping(source = "elementMenuId", target = "elementMenu")
            
    @Mapping(source = "profileId", target = "profile")

    //@Mapping(source = "userId", target = "user")
    UserAccess toEntity(UserAccessDTO userAccessDTO); 
    default UserAccess fromId(Long id) {
        if (id == null) {
            return null;
        }
        UserAccess userAccess = new UserAccess();
        userAccess.setId(id);
        return userAccess;
    }
}
