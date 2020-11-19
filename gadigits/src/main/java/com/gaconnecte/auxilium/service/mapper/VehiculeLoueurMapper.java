package com.gaconnecte.auxilium.service.mapper;

import com.gaconnecte.auxilium.domain.*;
import com.gaconnecte.auxilium.service.dto.LoueurDTO;
import com.gaconnecte.auxilium.service.dto.VehiculeLoueurDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity VehiculeLoueur and its DTO VehiculeLoueurDTO.
 */
@Mapper(componentModel = "spring", uses = {VehicleBrandMapper.class, VehicleBrandModelMapper.class, LoueurMapper.class })

public interface VehiculeLoueurMapper extends EntityMapper <VehiculeLoueurDTO, VehiculeLoueur> {
    
    @Mapping(source = "modele.id", target = "modeleId")
    @Mapping(source = "modele.label", target = "modeleLabel")

    @Mapping(source = "marque.id", target = "marqueId")
    @Mapping(source = "marque.label", target = "marqueLabel")

    @Mapping(source = "loueur.id", target = "loueurId")
    @Mapping(source = "loueur.raisonSociale", target = "loueurLabel")


    VehiculeLoueurDTO toDto(VehiculeLoueur vehiculeLoueur); 
    
    @Mapping(source = "modeleId", target = "modele")
    @Mapping(source = "marqueId", target = "marque")
    @Mapping(source = "loueurId", target = "loueur")

    VehiculeLoueur toEntity(VehiculeLoueurDTO vehiculeLoueurDTO);

    default VehiculeLoueur fromId(Long id) {
        if (id == null) {
            return null;
        }
        VehiculeLoueur vehiculeLoueur = new VehiculeLoueur();
        vehiculeLoueur.setId(id);
        return vehiculeLoueur;
    }
}
