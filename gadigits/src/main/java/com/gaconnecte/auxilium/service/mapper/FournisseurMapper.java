package com.gaconnecte.auxilium.service.mapper;

import com.gaconnecte.auxilium.domain.*;
import com.gaconnecte.auxilium.service.dto.FournisseurDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Fournisseur and its DTO FournisseurDTO.
 */
@Mapper(componentModel = "spring", uses = {PersonneMoraleMapper.class, })
public interface FournisseurMapper extends EntityMapper <FournisseurDTO, Fournisseur> {

    @Mapping(source = "personneMorale.id", target = "personneMoraleId")
    @Mapping(source = "personneMorale.raisonSociale", target = "personneMoraleRaisonSociale")

    FournisseurDTO toDto(Fournisseur fournisseur); 

    @Mapping(source = "personneMoraleId", target = "personneMorale")

    Fournisseur toEntity(FournisseurDTO fournisseurDTO); 
    default Fournisseur fromId(Long id) {
        if (id == null) {
            return null;
        }
        Fournisseur fournisseur = new Fournisseur();
        fournisseur.setId(id);
        return fournisseur;
    }
}
