package com.gaconnecte.auxilium.service.mapper;

import com.gaconnecte.auxilium.domain.*;
import com.gaconnecte.auxilium.service.dto.RefNatureExpertiseDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity RefNatureExpertise and its DTO RefNatureExpertiseDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface RefNatureExpertiseMapper extends EntityMapper <RefNatureExpertiseDTO, RefNatureExpertise> {
    
    
    default RefNatureExpertise fromId(Long id) {
        if (id == null) {
            return null;
        }
        RefNatureExpertise refNatureExpertise = new RefNatureExpertise();
        refNatureExpertise.setId(id);
        return refNatureExpertise;
    }
}
