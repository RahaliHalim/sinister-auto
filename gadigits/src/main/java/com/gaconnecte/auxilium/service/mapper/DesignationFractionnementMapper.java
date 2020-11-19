package com.gaconnecte.auxilium.service.mapper;

import com.gaconnecte.auxilium.domain.*;
import com.gaconnecte.auxilium.service.dto.DesignationFractionnementDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity DesignationFractionnement and its DTO DesignationFractionnementDTO.
 */
@Mapper(componentModel = "spring", uses = {RefFractionnementMapper.class, PartnerMapper.class, })
public interface DesignationFractionnementMapper extends EntityMapper <DesignationFractionnementDTO, DesignationFractionnement> {

    @Mapping(source = "fractionnement.id", target = "fractionnementId")
    @Mapping(source = "fractionnement.libelle", target = "fractionnementLibelle")

    @Mapping(source = "compagnie.id", target = "compagnieId")
    @Mapping(source = "compagnie.companyName", target = "compagnieNom")
    DesignationFractionnementDTO toDto(DesignationFractionnement designationFractionnement); 

    @Mapping(source = "fractionnementId", target = "fractionnement")

    @Mapping(source = "compagnieId", target = "compagnie")
    DesignationFractionnement toEntity(DesignationFractionnementDTO designationFractionnementDTO); 
    default DesignationFractionnement fromId(Long id) {
        if (id == null) {
            return null;
        }
        DesignationFractionnement designationFractionnement = new DesignationFractionnement();
        designationFractionnement.setId(id);
        return designationFractionnement;
    }
}
