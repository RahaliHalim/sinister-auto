package com.gaconnecte.auxilium.service.mapper;

import com.gaconnecte.auxilium.domain.*;
import com.gaconnecte.auxilium.service.dto.RefTypeContratDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity RefTypeContrat and its DTO RefTypeContratDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface RefTypeContratMapper extends EntityMapper <RefTypeContratDTO, RefTypeContrat> {
    
    
    default RefTypeContrat fromId(Long id) {
        if (id == null) {
            return null;
        }
        RefTypeContrat refTypeContrat = new RefTypeContrat();
        refTypeContrat.setId(id);
        return refTypeContrat;
    }
}
