package com.gaconnecte.auxilium.service.mapper;

import com.gaconnecte.auxilium.domain.*;
import com.gaconnecte.auxilium.service.dto.DesignationUsageContratDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity DesignationUsageContrat and its DTO DesignationUsageContratDTO.
 */
@Mapper(componentModel = "spring", uses = {PartnerMapper.class, VehicleUsageMapper.class, })
public interface DesignationUsageContratMapper extends EntityMapper <DesignationUsageContratDTO, DesignationUsageContrat> {

    @Mapping(source = "compagnie.id", target = "compagnieId")
    @Mapping(source = "compagnie.companyName", target = "compagnieNom")

    @Mapping(source = "refUsageContrat.id", target = "refUsageContratId")
    @Mapping(source = "refUsageContrat.label", target = "refUsageContratLibelle")
    DesignationUsageContratDTO toDto(DesignationUsageContrat designationUsageContrat); 

    @Mapping(source = "compagnieId", target = "compagnie")

    @Mapping(source = "refUsageContratId", target = "refUsageContrat")
    DesignationUsageContrat toEntity(DesignationUsageContratDTO designationUsageContratDTO); 
    default DesignationUsageContrat fromId(Long id) {
        if (id == null) {
            return null;
        }
        DesignationUsageContrat designationUsageContrat = new DesignationUsageContrat();
        designationUsageContrat.setId(id);
        return designationUsageContrat;
    }
}
