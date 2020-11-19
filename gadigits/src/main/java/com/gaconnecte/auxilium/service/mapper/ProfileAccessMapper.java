package com.gaconnecte.auxilium.service.mapper;

import com.gaconnecte.auxilium.domain.*;
import com.gaconnecte.auxilium.service.dto.ProfileAccessDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ProfileAccess and its DTO ProfileAccessDTO.
 */
@Mapper(componentModel = "spring", uses = {BusinessEntityMapper.class, FunctionalityMapper.class, ElementMenuMapper.class, UserProfileMapper.class, })
public interface ProfileAccessMapper extends EntityMapper <ProfileAccessDTO, ProfileAccess> {

    @Mapping(source = "businessEntity.id", target = "businessEntityId")
    @Mapping(source = "businessEntity.label", target = "businessEntityLabel")

    @Mapping(source = "functionality.id", target = "functionalityId")
    @Mapping(source = "functionality.label", target = "functionalityLabel")

    @Mapping(source = "elementMenu.id", target = "elementMenuId")
    @Mapping(source = "elementMenu.label", target = "elementMenuLabel")

    @Mapping(source = "profile.id", target = "profileId")
    @Mapping(source = "profile.label", target = "profileLabel")
    ProfileAccessDTO toDto(ProfileAccess profileAccess); 

    @Mapping(source = "businessEntityId", target = "businessEntity")

    @Mapping(source = "functionalityId", target = "functionality")

    @Mapping(source = "elementMenuId", target = "elementMenu")

    @Mapping(source = "profileId", target = "profile")
    ProfileAccess toEntity(ProfileAccessDTO profileAccessDTO); 
    default ProfileAccess fromId(Long id) {
        if (id == null) {
            return null;
        }
        ProfileAccess profileAccess = new ProfileAccess();
        profileAccess.setId(id);
        return profileAccess;
    }
}
