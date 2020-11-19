package com.gaconnecte.auxilium.service.mapper;

import com.gaconnecte.auxilium.domain.*;
import com.gaconnecte.auxilium.service.dto.RefExclusionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity RefModeGestion and its DTO RefModeGestionDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface RefExclusionMapper extends EntityMapper <RefExclusionDTO, RefExclusion> {
    

    default RefExclusion fromId(Long id) {
        if (id == null) {
            return null;
        }
        RefExclusion refExclusion = new RefExclusion();
        refExclusion.setId(id);
        return refExclusion;
    }
}
