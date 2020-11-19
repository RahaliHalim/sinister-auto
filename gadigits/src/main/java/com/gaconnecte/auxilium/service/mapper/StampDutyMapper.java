package com.gaconnecte.auxilium.service.mapper;

import com.gaconnecte.auxilium.domain.*;
import com.gaconnecte.auxilium.service.dto.StampDutyDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity StampDuty and its DTO StampDutyDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface StampDutyMapper extends EntityMapper <StampDutyDTO, StampDuty> {
    
    
    default StampDuty fromId(Long id) {
        if (id == null) {
            return null;
        }
        StampDuty stampDuty = new StampDuty();
        stampDuty.setId(id);
        return stampDuty;
    }
}
