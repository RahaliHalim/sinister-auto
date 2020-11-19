package com.gaconnecte.auxilium.service.mapper;

import com.gaconnecte.auxilium.domain.*;
import com.gaconnecte.auxilium.service.dto.UserCelluleDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity UserCellule and its DTO UserCelluleDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, CelluleMapper.class, AuthorityMapper.class})
public interface UserCelluleMapper extends EntityMapper <UserCelluleDTO, UserCellule> {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.login", target = "userLogin")
    @Mapping(source = "user.activated", target = "userActivated")
    @Mapping(source = "user.langKey", target = "userLangKey")
    @Mapping(source = "user.createdBy", target = "userCreatedBy")
    @Mapping(source = "user.createdDate", target = "userCreatedDate")
    @Mapping(source = "user.lastModifiedBy", target = "userLastModifiedBy")
    @Mapping(source = "user.lastModifiedDate", target = "userLastModifiedDate")

    @Mapping(source = "cellule.id", target = "celluleId")
    @Mapping(source = "cellule.name", target = "celluleName")
    UserCelluleDTO toDto(UserCellule userCellule); 

    @Mapping(source = "userId", target = "user")

    @Mapping(source = "celluleId", target = "cellule")
    UserCellule toEntity(UserCelluleDTO userCelluleDTO); 
    default UserCellule fromId(Long id) {
        if (id == null) {
            return null;
        }
        UserCellule userCellule = new UserCellule();
        userCellule.setId(id);
        return userCellule;
    }
}
