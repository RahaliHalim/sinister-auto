package com.gaconnecte.auxilium.service.mapper;

import com.gaconnecte.auxilium.domain.*;
import com.gaconnecte.auxilium.service.dto.RaisonAssistanceDTO;
import com.gaconnecte.auxilium.service.referential.mapper.RefStatusSinisterMapper;

import org.mapstruct.*;

/**
 * Mapper for the entity RaisonAssistance and its DTO RaisonAssistanceDTO.
 */
@Mapper(componentModel = "spring", uses = {OperationMapper.class, RefStatusSinisterMapper.class})
public interface RaisonAssistanceMapper extends EntityMapper <RaisonAssistanceDTO, RaisonAssistance> {

    @Mapping(source = "operation.id", target = "operationId")
    @Mapping(source = "operation.label", target = "operationLabel")
    @Mapping(source = "status.id", target = "statusId")
    @Mapping(source = "status.label", target = "statusLabel")
    RaisonAssistanceDTO toDto(RaisonAssistance raisonAssistance); 

    @Mapping(source = "operationId", target = "operation")
    @Mapping(source = "statusId", target = "status")
    RaisonAssistance toEntity(RaisonAssistanceDTO raisonAssistanceDTO); 
    default RaisonAssistance fromId(Long id) {
        if (id == null) {
            return null;
        }
        RaisonAssistance raisonAssistance = new RaisonAssistance();
        raisonAssistance.setId(id);
        return raisonAssistance;
    }
}
