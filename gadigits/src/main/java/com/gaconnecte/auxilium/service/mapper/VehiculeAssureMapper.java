package com.gaconnecte.auxilium.service.mapper;

import com.gaconnecte.auxilium.domain.*;
import com.gaconnecte.auxilium.service.dto.VehiculeAssureDTO;
import com.gaconnecte.auxilium.service.referential.mapper.RefPackMapper;

import org.mapstruct.*;

/**
 * Mapper for the entity VehiculeAssure and its DTO VehiculeAssureDTO.
 */
@Mapper(componentModel = "spring", uses = { AssureMapper.class, VehicleBrandModelMapper.class, VehicleBrandMapper.class,
        VehicleEnergyMapper.class, VehicleUsageMapper.class, ContratAssuranceMapper.class, RefPackMapper.class })
public interface VehiculeAssureMapper extends EntityMapper<VehiculeAssureDTO, VehiculeAssure> {

    @Mapping(source = "modele.id", target = "modeleId")
    @Mapping(source = "modele.label", target = "modeleLibelle")

    @Mapping(source = "energie.id", target = "energieId")
    @Mapping(source = "energie.label", target = "energieLibelle")

    @Mapping(source = "usage.id", target = "usageId")
    @Mapping(source = "usage.label", target = "usageLibelle")

    @Mapping(source = "contrat.id", target = "contratId")
    
    @Mapping(source = "contrat.client.companyName", target = "compagnyName")

    @Mapping(source = "insured.id", target = "insuredId")

    @Mapping(source = "marque.id", target = "marqueId")
    @Mapping(source = "marque.label", target = "marqueLibelle")

    @Mapping(source = "pack.id", target = "packId")
    @Mapping(source = "pack.label", target = "packLabel")

    @Mapping(source = "contrat.client.id", target = "partnerId")

    VehiculeAssureDTO toDto(VehiculeAssure vehiculeAssure);

    @Mapping(source = "modeleId", target = "modele")

    @Mapping(source = "marqueId", target = "marque")

    @Mapping(source = "energieId", target = "energie")

    @Mapping(source = "usageId", target = "usage")

    @Mapping(source = "contratId", target = "contrat")

    @Mapping(source = "insuredId", target = "insured")

    @Mapping(source = "packId", target = "pack")

    VehiculeAssure toEntity(VehiculeAssureDTO vehiculeAssureDTO);

    default VehiculeAssure fromId(Long id) {
        if (id == null) {
            return null;
        }
        VehiculeAssure vehiculeAssure = new VehiculeAssure();
        vehiculeAssure.setId(id);
        return vehiculeAssure;
    }
}
