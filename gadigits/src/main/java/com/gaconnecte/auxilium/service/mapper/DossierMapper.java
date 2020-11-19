package com.gaconnecte.auxilium.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.gaconnecte.auxilium.domain.Dossier;
import com.gaconnecte.auxilium.service.dto.DossierDTO;

/**
 * Mapper for the entity Dossier and its DTO DossierDTO.
 */
@Mapper(componentModel = "spring", uses = {VehiculeAssureMapper.class, UserMapper.class})
public interface DossierMapper extends EntityMapper <DossierDTO, Dossier> {

    DossierDTO toDto(Dossier dossier); 

    Dossier toEntity(DossierDTO dossierDTO); 
    default Dossier fromId(Long id) {
        if (id == null) {
            return null;
        }
        Dossier dossier = new Dossier();
        dossier.setId(id);
        return dossier;
    }
}
