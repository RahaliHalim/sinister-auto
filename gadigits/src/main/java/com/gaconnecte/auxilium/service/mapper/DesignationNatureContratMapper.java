package com.gaconnecte.auxilium.service.mapper;

import com.gaconnecte.auxilium.domain.*;
import com.gaconnecte.auxilium.service.dto.DesignationNatureContratDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity DesignationNatureContrat and its DTO DesignationNatureContratDTO.
 */
@Mapper(componentModel = "spring", uses = {PartnerMapper.class, RefNatureContratMapper.class, })
public interface DesignationNatureContratMapper extends EntityMapper <DesignationNatureContratDTO, DesignationNatureContrat> {

    @Mapping(source = "compagnie.id", target = "compagnieId")
    @Mapping(source = "compagnie.companyName", target = "compagnieNom")

    @Mapping(source = "refTypeContrat.id", target = "refTypeContratId")
    @Mapping(source = "refTypeContrat.libelle", target = "refTypeContratLibelle")
    DesignationNatureContratDTO toDto(DesignationNatureContrat designationNatureContrat); 

    @Mapping(source = "compagnieId", target = "compagnie")

    @Mapping(source = "refTypeContratId", target = "refTypeContrat")
    DesignationNatureContrat toEntity(DesignationNatureContratDTO designationNatureContratDTO); 
    default DesignationNatureContrat fromId(Long id) {
        if (id == null) {
            return null;
        }
        DesignationNatureContrat designationNatureContrat = new DesignationNatureContrat();
        designationNatureContrat.setId(id);
        return designationNatureContrat;
    }
}
