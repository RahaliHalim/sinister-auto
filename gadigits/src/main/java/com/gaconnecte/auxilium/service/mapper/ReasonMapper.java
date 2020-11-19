package com.gaconnecte.auxilium.service.mapper;

import com.gaconnecte.auxilium.domain.*;
import com.gaconnecte.auxilium.service.dto.ReasonDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Reason and its DTO ReasonDTO.
 */
@Mapper(componentModel = "spring", uses = {StepMapper.class, })
public interface ReasonMapper extends EntityMapper <ReasonDTO, Reason> {

    @Mapping(source = "step.id", target = "stepId")
    @Mapping(source = "step.label", target = "stepLabel")
    ReasonDTO toDto(Reason reason); 

    @Mapping(source = "stepId", target = "step")
    Reason toEntity(ReasonDTO reasonDTO); 
    default Reason fromId(Long id) {
        if (id == null) {
            return null;
        }
        Reason reason = new Reason();
        reason.setId(id);
        return reason;
    }
}
