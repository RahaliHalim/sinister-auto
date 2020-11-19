package com.gaconnecte.auxilium.service.mapper;

import com.gaconnecte.auxilium.domain.*;
import com.gaconnecte.auxilium.service.dto.LoueurDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Loueur and its DTO LoueurDTO.
 */
@Mapper(componentModel = "spring", uses = { DelegationMapper.class,GovernorateMapper.class,RaisonAssistanceMapper.class,
		RefModeReglementMapper.class,VisAVisMapper.class, VehiculeLoueurMapper.class })
public interface LoueurMapper extends EntityMapper <LoueurDTO, Loueur> {
    
	    @Mapping(source = "reglement.id", target = "reglementId")
	    @Mapping(source = "delegation.id", target = "delegationId")
	    @Mapping(source = "delegation.label", target = "delegationLabel")
	    @Mapping(source = "governorate.id", target = "governorateId")
	    @Mapping(source = "governorate.label", target = "governorateLabel")
	    @Mapping(source = "motif.id", target = "motifId")
	    
	    LoueurDTO toDto(Loueur loueur); 
	    
	    @Mapping(source = "reglementId", target = "reglement")
	    @Mapping(source = "delegationId", target = "delegation")
	    @Mapping(source = "governorateId", target = "governorate")
	    @Mapping(source = "motifId", target = "motif")
	    Loueur toEntity(LoueurDTO loueurDTO);


    default Loueur fromId(Long id) {
        if (id == null) {
            return null;
        }
        Loueur loueur = new Loueur();
        loueur.setId(id);
        return loueur;
    }
}
