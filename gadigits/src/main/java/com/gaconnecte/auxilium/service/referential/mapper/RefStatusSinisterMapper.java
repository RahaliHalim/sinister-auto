package com.gaconnecte.auxilium.service.referential.mapper;

import com.gaconnecte.auxilium.domain.referential.RefStatusSinister;
import com.gaconnecte.auxilium.service.mapper.EntityMapper;
import com.gaconnecte.auxilium.service.referential.dto.RefStatusSinisterDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity RefStatusSinister and its DTO RefStatusSinisterDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface RefStatusSinisterMapper extends EntityMapper <RefStatusSinisterDTO, RefStatusSinister> {
    
    
    default RefStatusSinister fromId(Long id) {
        if (id == null) {
            return null;
        }
        RefStatusSinister refStatusSinister = new RefStatusSinister();
        refStatusSinister.setId(id);
        return refStatusSinister;
    }
}
