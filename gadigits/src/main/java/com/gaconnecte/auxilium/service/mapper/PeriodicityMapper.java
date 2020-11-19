package com.gaconnecte.auxilium.service.mapper;

import com.gaconnecte.auxilium.domain.*;
import com.gaconnecte.auxilium.service.dto.PeriodicityDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Periodicity and its DTO PeriodicityDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PeriodicityMapper extends EntityMapper <PeriodicityDTO, Periodicity> {
    
    
    default Periodicity fromId(Long id) {
        if (id == null) {
            return null;
        }
        Periodicity periodicity = new Periodicity();
        periodicity.setId(id);
        return periodicity;
    }
}
