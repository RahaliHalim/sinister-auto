package com.gaconnecte.auxilium.service.mapper;

import com.gaconnecte.auxilium.domain.*;
import com.gaconnecte.auxilium.service.dto.RefTypeInterventionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity RefTypeIntervention and its DTO RefTypeInterventionDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface RefTypeInterventionMapper extends EntityMapper <RefTypeInterventionDTO, RefTypeIntervention> {
    
    @Mapping(target = "detailMos", ignore = true)
    RefTypeIntervention toEntity(RefTypeInterventionDTO refTypeInterventionDTO); 
    default RefTypeIntervention fromId(Long id) {
        if (id == null) {
            return null;
        }
        RefTypeIntervention refTypeIntervention = new RefTypeIntervention();
        refTypeIntervention.setId(id);
        return refTypeIntervention;
    }
}
