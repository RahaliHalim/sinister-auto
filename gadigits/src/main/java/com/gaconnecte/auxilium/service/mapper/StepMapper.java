package com.gaconnecte.auxilium.service.mapper;

import com.gaconnecte.auxilium.domain.*;
import com.gaconnecte.auxilium.service.dto.StepDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Step and its DTO StepDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface StepMapper extends EntityMapper <StepDTO, Step> {
    
    
    default Step fromId(Long id) {
        if (id == null) {
            return null;
        }
        Step step = new Step();
        step.setId(id);
        return step;
    }
}
