package com.gaconnecte.auxilium.service.mapper;

import com.gaconnecte.auxilium.domain.*;
import com.gaconnecte.auxilium.service.dto.PrestationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Prestation and its DTO PrestationDTO.
 */
@Mapper(componentModel = "spring", uses = {DossierMapper.class, UserMapper.class, PieceJointeMapper.class, })
public interface PrestationMapper extends EntityMapper <PrestationDTO, Prestation> {

    @Mapping(source = "dossier.id", target = "dossierId")
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "dossier.reference", target = "dossierReference")
    PrestationDTO toDto(Prestation prestation); 
    @Mapping(target = "journals", ignore = true)
    @Mapping(source = "dossierId", target = "dossier")
    @Mapping(source = "userId", target = "user")
   
    Prestation toEntity(PrestationDTO prestationDTO); 
    default Prestation fromId(Long id) {
        if (id == null) {
            return null;
        }
        Prestation prestation = new Prestation();
        prestation.setId(id);
        return prestation;
    }
}
