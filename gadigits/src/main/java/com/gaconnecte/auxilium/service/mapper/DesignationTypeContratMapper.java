package com.gaconnecte.auxilium.service.mapper;

import com.gaconnecte.auxilium.domain.*;
import com.gaconnecte.auxilium.service.dto.DesignationTypeContratDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity DesignationTypeContrat and its DTO DesignationTypeContratDTO.
 */
@Mapper(componentModel = "spring", uses = {PartnerMapper.class, RefTypeContratMapper.class, })
public interface DesignationTypeContratMapper extends EntityMapper <DesignationTypeContratDTO, DesignationTypeContrat> {

    @Mapping(source = "compagnie.id", target = "compagnieId")
    @Mapping(source = "compagnie.companyName", target = "compagnieNom")

    @Mapping(source = "refTypeContrat.id", target = "refTypeContratId")
    @Mapping(source = "refTypeContrat.libelle", target = "refTypeContratLibelle")
    DesignationTypeContratDTO toDto(DesignationTypeContrat designationTypeContrat); 

    @Mapping(source = "compagnieId", target = "compagnie")

    @Mapping(source = "refTypeContratId", target = "refTypeContrat")
    DesignationTypeContrat toEntity(DesignationTypeContratDTO designationTypeContratDTO); 
    default DesignationTypeContrat fromId(Long id) {
        if (id == null) {
            return null;
        }
        DesignationTypeContrat designationTypeContrat = new DesignationTypeContrat();
        designationTypeContrat.setId(id);
        return designationTypeContrat;
    }
}
