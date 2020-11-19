package com.gaconnecte.auxilium.service.mapper;

import com.gaconnecte.auxilium.domain.*;
import com.gaconnecte.auxilium.service.dto.PersonneMoraleDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity PersonneMorale and its DTO PersonneMoraleDTO.
 */
@Mapper(componentModel = "spring", uses = {DelegationMapper.class,VatRateMapper.class, RefModeReglementMapper.class })
public interface PersonneMoraleMapper extends EntityMapper <PersonneMoraleDTO, PersonneMorale> {

    @Mapping(source = "ville.id", target = "villeId")
    @Mapping(source = "reglement.id", target = "reglementId")
    @Mapping(source = "tva.id", target = "tvaId")
    PersonneMoraleDTO toDto(PersonneMorale personneMorale); 

    @Mapping(source = "villeId", target = "ville")
    @Mapping(source = "reglementId", target = "reglement")
    @Mapping(source = "tvaId", target = "tva")
    PersonneMorale toEntity(PersonneMoraleDTO personneMoraleDTO); 
    default PersonneMorale fromId(Long id) {
        if (id == null) {
            return null;
        }
        PersonneMorale personneMorale = new PersonneMorale();
        personneMorale.setId(id);
        return personneMorale;
    }
}
