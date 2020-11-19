package com.gaconnecte.auxilium.service.mapper;

import com.gaconnecte.auxilium.domain.*;
import com.gaconnecte.auxilium.service.dto.ReparateurGrilleDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ReparateurGrille and its DTO ReparateurGrilleDTO.
 */
@Mapper(componentModel = "spring", uses = {GrilleMapper.class, ReparateurMapper.class, RefTypeInterventionMapper.class})
public interface ReparateurGrilleMapper extends EntityMapper <ReparateurGrilleDTO, ReparateurGrille> {

    @Mapping(source = "reparateur.id", target = "reparateurId")

    @Mapping(source = "grille.id", target = "grilleId")
    @Mapping(source = "refTypeIntervention.id", target = "refTypeInterventionId")
    @Mapping(source = "refTypeIntervention.libelle", target = "refTypeInterventionLibelle")
    ReparateurGrilleDTO toDto(ReparateurGrille reparateurGrille); 

    @Mapping(source = "reparateurId", target = "reparateur")

    @Mapping(source = "grilleId", target = "grille")

    @Mapping(source = "refTypeInterventionId", target = "refTypeIntervention")

    ReparateurGrille toEntity(ReparateurGrilleDTO reparateurGrilleDTO); 
    default ReparateurGrille fromId(Long id) {
        if (id == null) {
            return null;
        }
        ReparateurGrille reparateurGrille = new ReparateurGrille();
        reparateurGrille.setId(id);
        return reparateurGrille;
    }
}
