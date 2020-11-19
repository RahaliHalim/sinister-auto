package com.gaconnecte.auxilium.service.mapper;

import com.gaconnecte.auxilium.domain.*;
import com.gaconnecte.auxilium.service.dto.ReparateurDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Reparateur and its DTO ReparateurDTO.
 */
@Mapper(componentModel = "spring", uses = {  VisAVisMapper.class,DelegationMapper.class,GovernorateMapper.class,RefMaterielMapper.class,OrientationMapper.class, RefModeReglementMapper.class,GarantieImpliqueMapper.class, VehicleBrandMapper.class,RaisonPecMapper.class })
public interface ReparateurMapper extends EntityMapper <ReparateurDTO, Reparateur> {

 
    @Mapping(source = "reglement.id", target = "reglementId")
    @Mapping(source = "ville.id", target = "villeId")
    @Mapping(source = "ville.label", target = "villeLibelle")
    @Mapping(source = "ville.governorate.id", target = "gouvernoratId")
    @Mapping(source = "ville.governorate.label", target = "libelleGouvernorat")
    @Mapping(source = "raisonBloquage.id", target = "raisonBloquageId")
    @Mapping(source = "raisonBloquage.label", target = "raisonBloquageLabel")
    ReparateurDTO toDto(Reparateur reparateur); 
    @Mapping(source = "reglementId", target = "reglement")
    @Mapping(source = "villeId", target = "ville")
    @Mapping(source = "gouvernoratId", target = "ville.governorate")
    @Mapping(source = "raisonBloquageId", target = "raisonBloquage")
    Reparateur toEntity(ReparateurDTO reparateurDTO); 
    default Reparateur fromId(Long id) {
        if (id == null) {
            return null;
        }
        Reparateur reparateur = new Reparateur();
        reparateur.setId(id);
        return reparateur;
    }
}
