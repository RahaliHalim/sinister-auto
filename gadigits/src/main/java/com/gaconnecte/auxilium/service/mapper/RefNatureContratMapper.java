package com.gaconnecte.auxilium.service.mapper;

import com.gaconnecte.auxilium.domain.*;
import com.gaconnecte.auxilium.service.dto.RefNatureContratDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity RefNatureContrat and its DTO RefNatureContratDTO.
 */
@Mapper(componentModel = "spring", uses = { })
public interface RefNatureContratMapper extends EntityMapper <RefNatureContratDTO, RefNatureContrat> {
    
    
    default RefNatureContrat fromId(Long id) {
        if (id == null) {
            return null;
        }
        RefNatureContrat refNatureContrat = new RefNatureContrat();
        refNatureContrat.setId(id);
        return refNatureContrat;
    }
}
