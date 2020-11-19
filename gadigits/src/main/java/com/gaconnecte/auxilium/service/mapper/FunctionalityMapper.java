package com.gaconnecte.auxilium.service.mapper;

import com.gaconnecte.auxilium.domain.*;
import com.gaconnecte.auxilium.service.dto.FunctionalityDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Functionality and its DTO FunctionalityDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface FunctionalityMapper extends EntityMapper <FunctionalityDTO, Functionality> {
    
    
    default Functionality fromId(Long id) {
        if (id == null) {
            return null;
        }
        Functionality functionality = new Functionality();
        functionality.setId(id);
        return functionality;
    }
}
