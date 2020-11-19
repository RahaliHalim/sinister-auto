package com.gaconnecte.auxilium.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.gaconnecte.auxilium.domain.GroupDetails;
import com.gaconnecte.auxilium.service.dto.GroupDetailDTO;

@Mapper(componentModel = "spring", uses = {GroupMapper.class, RefModeGestionMapper.class, PartnerMapper.class})
public interface GroupDetailsMapper extends EntityMapper <GroupDetailDTO, GroupDetails>{

 @Mapping(source = "group.id", target = "groupId")
 @Mapping(source = "group.name", target = "grouName")
 @Mapping(source = "refModeGestion.id", target = "refModeGestionId")
 @Mapping(source = "refModeGestion.libelle", target = "refModeGestionName")
 @Mapping(source = "client.id", target = "clientId")
 @Mapping(source = "client.companyName", target = "clientName")

 GroupDetailDTO toDto(GroupDetails groupDetails);

 @Mapping(source = "groupId", target = "group")
 
 @Mapping(source = "refModeGestionId", target = "refModeGestion")
 
 @Mapping(source = "clientId", target = "client")
 

 GroupDetails toEntity(GroupDetailDTO groupDetailsDTO); 
    default GroupDetails fromId(Long id) {
        if (id == null) {
            return null;
        }
        GroupDetails groupDetails = new GroupDetails();
        groupDetails.setId(id);
        return groupDetails;
    }
 
}