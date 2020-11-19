package com.gaconnecte.auxilium.service.mapper;
import com.gaconnecte.auxilium.domain.*;

import com.gaconnecte.auxilium.service.dto.OrientationDTO;
import com.gaconnecte.auxilium.service.dto.PartnerModeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity orientation and its DTO PartnerModeDTO.
 */
@Mapper(componentModel = "spring", uses = {RefModeGestionMapper.class, PartnerMapper.class,GarantieImpliqueMapper.class,})
public interface PartnerModeMapper extends EntityMapper <PartnerModeDTO, PartnerMode> {

  
    @Mapping(source = "partner.id", target = "partnerId")
    @Mapping(source = "partner.companyName", target = "labelPartnerMode") 
    @Mapping(source = "mode.id", target = "modeId")
    @Mapping(source = "mode.libelle", target = "partnerMode")
    @Mapping(source = "garantieImplique.id", target = "garantieImpliqueId")

    PartnerModeDTO toDto(PartnerMode partnerMode); 

    @Mapping(source = "partnerId", target = "partner")
    @Mapping(source = "modeId", target = "mode")
    @Mapping(source = "garantieImpliqueId", target = "garantieImplique")
    PartnerMode toEntity(PartnerModeDTO partnerModeDTO); 
    default PartnerMode fromId(Long id) {
        if (id == null) {
            return null;
        }
        PartnerMode partnerMode = new PartnerMode();
        partnerMode.setId(id);
        return partnerMode;
    }
}
