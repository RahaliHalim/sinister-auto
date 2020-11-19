package com.gaconnecte.auxilium.service.mapper;

import com.gaconnecte.auxilium.domain.*;
import com.gaconnecte.auxilium.service.dto.RefRemorqueurDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity RefRemorqueur and its DTO RefRemorqueurDTO.
 */
@Mapper(componentModel = "spring", uses = {VisAVisMapper.class, PersonneMoraleMapper.class, DelegationMapper.class, GovernorateMapper.class })
public interface RefRemorqueurMapper extends EntityMapper <RefRemorqueurDTO, RefRemorqueur> {

    @Mapping(source = "societe.id", target = "societeId")
    @Mapping(source = "societe.raisonSociale", target = "societeRaisonSociale")
    @Mapping(source = "societe.adresse", target = "adresse")
    @Mapping(source = "societe.matriculeFiscale", target = "matriculeFiscale")
    @Mapping(source = "societe.codeCategorie", target = "codeCategorie")
    @Mapping(source = "societe.tva.vatRate", target = "vatRate")
    
    @Mapping(source = "societe.ville.governorate.id", target = "gouvernoratId")
    @Mapping(source = "societe.ville.governorate.label", target = "libelleGouvernorat")
    RefRemorqueurDTO toDto(RefRemorqueur refRemorqueur); 

    
    @Mapping(source = "societeId", target = "societe")
    @Mapping(source = "gouvernoratId", target = "societe.ville.governorate")
    RefRemorqueur toEntity(RefRemorqueurDTO refRemorqueurDTO); 
    default RefRemorqueur fromId(Long id) {
        if (id == null) {
            return null;
        }
        RefRemorqueur refRemorqueur = new RefRemorqueur();
        refRemorqueur.setId(id);
        return refRemorqueur;
    }
}
