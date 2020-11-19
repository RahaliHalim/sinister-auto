package com.gaconnecte.auxilium.service.mapper;

import com.gaconnecte.auxilium.domain.*;
import com.gaconnecte.auxilium.service.dto.RaisonPecDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity RaisonPec and its DTO RaisonPecDTO.
 */
@Mapper(componentModel = "spring", uses = {OperationMapper.class, PecStatusChangeMatrixMapper.class, StatusPecMapper.class,})
public interface RaisonPecMapper extends EntityMapper <RaisonPecDTO, RaisonPec> {

    @Mapping(source = "operation.id", target = "operationId")
    @Mapping(source = "operation.label", target = "operationLabel")
    @Mapping(source = "statusPec.id", target = "statusPecId")
    @Mapping(source = "statusPec.label", target = "statusPecLabel")

    
    @Mapping(source = "pecStatusChangeMatrix.id", target = "pecStatusChangeMatrixId")
    @Mapping(source = "pecStatusChangeMatrix.label", target = "pecStatusChangeMatrixLabel")
            
    RaisonPecDTO toDto(RaisonPec raisonPec); 

    @Mapping(source = "operationId", target = "operation")



    @Mapping(source = "statusPecId", target = "statusPec")

    
    @Mapping(source = "pecStatusChangeMatrixId", target = "pecStatusChangeMatrix")

    RaisonPec toEntity(RaisonPecDTO raisonPecDTO); 
    default RaisonPec fromId(Long id) {
        if (id == null) {
            return null;
        }
        RaisonPec raisonPec = new RaisonPec();
        raisonPec.setId(id);
        return raisonPec;
    }
}
