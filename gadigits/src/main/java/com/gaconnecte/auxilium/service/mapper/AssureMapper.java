package com.gaconnecte.auxilium.service.mapper;

import com.gaconnecte.auxilium.domain.*;
import com.gaconnecte.auxilium.service.dto.AssureDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Assure and its DTO AssureDTO.
 */
@Mapper(componentModel = "spring", uses = {DelegationMapper.class, UserMapper.class, })
public interface AssureMapper extends EntityMapper <AssureDTO, Assure> {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "delegation.id", target = "delegationId")
    @Mapping(source = "delegation.label", target = "delegationLabel")
    AssureDTO toDto(Assure assure); 

    @Mapping(source = "userId", target = "user")

    @Mapping(source = "delegationId", target = "delegation")
    Assure toEntity(AssureDTO assureDTO); 
    default Assure fromId(Long id) {
        if (id == null) {
            return null;
        }
        Assure assure = new Assure();
        assure.setId(id);
        return assure;
    }
}
