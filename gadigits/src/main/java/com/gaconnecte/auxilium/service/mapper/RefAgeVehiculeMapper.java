package com.gaconnecte.auxilium.service.mapper;

import com.gaconnecte.auxilium.domain.*;
import com.gaconnecte.auxilium.service.dto.RefAgeVehiculeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity RefTva and its DTO RefTvaDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface RefAgeVehiculeMapper extends EntityMapper <RefAgeVehiculeDTO, RefAgeVehicule> {
    
    
    default RefAgeVehicule fromId(Long id) {
        if (id == null) {
            return null;
        }
        RefAgeVehicule refAgeVehicule = new RefAgeVehicule();
        refAgeVehicule.setId(id);
        return refAgeVehicule;
    }
}
