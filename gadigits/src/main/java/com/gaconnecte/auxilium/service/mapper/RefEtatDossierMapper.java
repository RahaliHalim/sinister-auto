package com.gaconnecte.auxilium.service.mapper;

import com.gaconnecte.auxilium.domain.*;
import com.gaconnecte.auxilium.service.dto.RefEtatDossierDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity RefEtatDossier and its DTO RefEtatDossierDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface RefEtatDossierMapper extends EntityMapper <RefEtatDossierDTO, RefEtatDossier> {
    
    
    default RefEtatDossier fromId(Long id) {
        if (id == null) {
            return null;
        }
        RefEtatDossier refEtatDossier = new RefEtatDossier();
        refEtatDossier.setId(id);
        return refEtatDossier;
    }
}
