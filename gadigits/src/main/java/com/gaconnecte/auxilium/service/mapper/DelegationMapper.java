package com.gaconnecte.auxilium.service.mapper;

import com.gaconnecte.auxilium.domain.*;
import com.gaconnecte.auxilium.service.dto.DelegationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Delegation and its DTO DelegationDTO.
 */
@Mapper(componentModel = "spring", uses = {GovernorateMapper.class, })
public interface DelegationMapper extends EntityMapper <DelegationDTO, Delegation> {

    @Mapping(source = "governorate.id", target = "governorateId")
    @Mapping(source = "governorate.label", target = "governorateLabel")
    DelegationDTO toDto(Delegation delegation); 

    @Mapping(source = "governorateId", target = "governorate")
    Delegation toEntity(DelegationDTO delegationDTO); 
    default Delegation fromId(Long id) {
        if (id == null) {
            return null;
        }
        Delegation delegation = new Delegation();
        delegation.setId(id);
        return delegation;
    }
}
