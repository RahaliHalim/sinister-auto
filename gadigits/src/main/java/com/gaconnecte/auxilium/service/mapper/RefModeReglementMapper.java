package com.gaconnecte.auxilium.service.mapper;

import com.gaconnecte.auxilium.domain.*;
import com.gaconnecte.auxilium.service.dto.RefModeReglementDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity RefModeReglement and its DTO RefModeReglementDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface RefModeReglementMapper extends EntityMapper <RefModeReglementDTO, RefModeReglement> {
    
    
    default RefModeReglement fromId(Long id) {
        if (id == null) {
            return null;
        }
        RefModeReglement refModeReglement = new RefModeReglement();
        refModeReglement.setId(id);
        return refModeReglement;
    }
}
