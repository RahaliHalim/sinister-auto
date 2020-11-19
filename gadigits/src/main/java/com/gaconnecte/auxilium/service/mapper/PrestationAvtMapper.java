package com.gaconnecte.auxilium.service.mapper;

import com.gaconnecte.auxilium.domain.*;
import com.gaconnecte.auxilium.service.dto.PrestationAvtDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity PrestationAvt and its DTO PrestationAvtDTO.
 */
@Mapper(componentModel = "spring", uses = {PrestationMapper.class, RefTypeServiceMapper.class, })
public interface PrestationAvtMapper extends EntityMapper <PrestationAvtDTO, PrestationAvt> {

    @Mapping(source = "dossier.id", target = "dossierId")
	@Mapping(source = "dossier.reference", target = "dossierReference")
   
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "typeService.id", target = "typeServiceId")
    @Mapping(source = "typeService.nom", target = "typeServiceNom")
    PrestationAvtDTO toDto(PrestationAvt prestationAvt); 

    @Mapping(source = "dossierId", target = "dossier.id")
   
    @Mapping(source = "userId", target = "user.id")
    @Mapping(source = "typeServiceId", target = "typeService")
    PrestationAvt toEntity(PrestationAvtDTO prestationAvtDTO); 
    default PrestationAvt fromId(Long id) {
        if (id == null) {
            return null;
        }
        PrestationAvt prestationAvt = new PrestationAvt();
        prestationAvt.setId(id);
        return prestationAvt;
    }
}
