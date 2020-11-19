package com.gaconnecte.auxilium.service.mapper;

import com.gaconnecte.auxilium.domain.*;
import com.gaconnecte.auxilium.service.dto.RefEtatBsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity RefEtatBs and its DTO RefEtatBsDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface RefEtatBsMapper extends EntityMapper <RefEtatBsDTO, RefEtatBs> {
    
    @Mapping(target = "bonSorties", ignore = true)
    RefEtatBs toEntity(RefEtatBsDTO refEtatBsDTO); 
    default RefEtatBs fromId(Long id) {
        if (id == null) {
            return null;
        }
        RefEtatBs refEtatBs = new RefEtatBs();
        refEtatBs.setId(id);
        return refEtatBs;
    }
}
