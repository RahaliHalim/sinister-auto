package com.gaconnecte.auxilium.service.mapper;

import com.gaconnecte.auxilium.domain.*;
import com.gaconnecte.auxilium.service.dto.GrilleDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Grille and its DTO GrilleDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface GrilleMapper extends EntityMapper <GrilleDTO, Grille> {
    
    Grille toEntity(GrilleDTO grilleDTO); 
    default Grille fromId(Long id) {
        if (id == null) {
            return null;
        }
        Grille grille = new Grille();
        grille.setId(id);
        return grille;
    }
}
