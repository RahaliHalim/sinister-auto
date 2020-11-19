package com.gaconnecte.auxilium.service.mapper;

import com.gaconnecte.auxilium.domain.*;
import com.gaconnecte.auxilium.service.dto.ExpertDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Expert and its DTO ExpertDTO.
 */
@Mapper(componentModel = "spring", uses = {DelegationMapper.class,GovernorateMapper.class,ExpertGarantieImpliqueMapper.class })
public interface ExpertMapper extends EntityMapper <ExpertDTO, Expert> {

    @Mapping(source = "delegation.id", target = "delegationId")
    @Mapping(source = "delegation.label", target = "delegationLabel")
    @Mapping(source = "delegation.governorate.id", target = "gouvernoratId")
    @Mapping(source = "delegation.governorate.label", target = "governorateLabel")
    ExpertDTO toDto(Expert expert); 

    @Mapping(source = "delegationId", target = "delegation.id")
    
    //@Mapping(source = "gouvernoratId", target = "delegation.governorate")
   
    Expert toEntity(ExpertDTO expertDTO); 
    default Expert fromId(Long id) {
        if (id == null) {
            return null;
        }
        Expert expert = new Expert();
        expert.setId(id);
        return expert;
    }
}
