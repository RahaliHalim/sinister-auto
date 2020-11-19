package com.gaconnecte.auxilium.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.gaconnecte.auxilium.domain.ContratAssurance;
import com.gaconnecte.auxilium.service.dto.ContratAssuranceDTO;
import com.gaconnecte.auxilium.service.referential.mapper.RefPackMapper;

/**
 * Mapper for the entity ContratAssurance and its DTO ContratAssuranceDTO.
 */
@Mapper(componentModel = "spring", uses = {RefTypeContratMapper.class, RefNatureContratMapper.class, AgencyMapper.class, VehicleUsageMapper.class, 
    VehiculeAssureMapper.class, RefFractionnementMapper.class, RefPackMapper.class, PartnerMapper.class, 
    PolicyStatusMapper.class, PolicyReceiptStatusMapper.class, AmendmentTypeMapper.class})
public interface ContratAssuranceMapper extends EntityMapper<ContratAssuranceDTO, ContratAssurance> {

    @Mapping(source = "type.id", target = "typeId")
    @Mapping(source = "type.libelle", target = "typeLabel")


    @Mapping(source = "nature.id", target = "natureId")
    @Mapping(source = "nature.libelle", target = "natureLabel")

    @Mapping(source = "agence.id", target = "agenceId")
    @Mapping(source = "agence.name", target = "agenceNom")

    @Mapping(source = "usage.id", target = "usageId")
    @Mapping(source = "usage.label", target = "usageLibelle")

    @Mapping(source = "client.id", target = "clientId")
    @Mapping(source = "client.companyName", target = "nomCompagnie")

    @Mapping(source = "fractionnement.id", target = "fractionnementId")
    @Mapping(source = "fractionnement.libelle", target = "fractionnementLibelle")

    @Mapping(source = "pack.id", target = "packId")
    @Mapping(source = "pack.label", target = "packNom")

    @Mapping(source = "status.id", target = "statusId")
    @Mapping(source = "status.label", target = "statusLabel")

    @Mapping(source = "receiptStatus.id", target = "receiptStatusId")
    @Mapping(source = "receiptStatus.label", target = "receiptStatusLabel")

    @Mapping(source = "amendmentType.id", target = "amendmentTypeId")
    @Mapping(source = "amendmentType.label", target = "amendmentTypeLabel")
            
    ContratAssuranceDTO toDto(ContratAssurance contratAssurance);

    @Mapping(source = "typeId", target = "type")

    @Mapping(source = "natureId", target = "nature")

    @Mapping(source = "agenceId", target = "agence")

    @Mapping(source = "usageId", target = "usage")

    @Mapping(source = "fractionnementId", target = "fractionnement")

    @Mapping(source = "packId", target = "pack")

    @Mapping(source = "clientId", target = "client")
            
    @Mapping(source = "statusId", target = "status")
    
    @Mapping(source = "receiptStatusId", target = "receiptStatus")
    
    @Mapping(source = "amendmentTypeId", target = "amendmentType")
            
    ContratAssurance toEntity(ContratAssuranceDTO contratAssuranceDTO);

    default ContratAssurance fromId(Long id) {
        if (id == null) {
            return null;
        }
        ContratAssurance contratAssurance = new ContratAssurance();
        contratAssurance.setId(id);
        return contratAssurance;
    }
}
