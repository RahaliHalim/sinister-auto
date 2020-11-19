package com.gaconnecte.auxilium.service.mapper;

import com.gaconnecte.auxilium.domain.*;
import com.gaconnecte.auxilium.service.dto.RefMaterielDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity RefMateriel and its DTO RefMaterielDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface RefMaterielMapper extends EntityMapper <RefMaterielDTO, RefMateriel> {
    
    
    RefMateriel toEntity(RefMaterielDTO refMaterielDTO); 
    default RefMateriel fromId(Long id) {
        if (id == null) {
            return null;
        }
        RefMateriel refMateriel = new RefMateriel();
        refMateriel.setId(id);
        return refMateriel;
    }
}
