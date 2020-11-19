package com.gaconnecte.auxilium.service.mapper;

import com.gaconnecte.auxilium.domain.*;
import com.gaconnecte.auxilium.service.dto.RefModeGestionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity RefModeGestion and its DTO RefModeGestionDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface RefModeGestionMapper extends EntityMapper <RefModeGestionDTO, RefModeGestion> {
    
    RefModeGestion toEntity(RefModeGestionDTO refModeGestionDTO); 
    default RefModeGestion fromId(Long id) {
        if (id == null) {
            return null;
        }
        RefModeGestion refModeGestion = new RefModeGestion();
        refModeGestion.setId(id);
        return refModeGestion;
    }
}
