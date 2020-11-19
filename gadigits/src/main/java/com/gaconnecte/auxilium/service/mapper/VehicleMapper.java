package com.gaconnecte.auxilium.service.mapper;

import com.gaconnecte.auxilium.domain.*;
import com.gaconnecte.auxilium.service.dto.VehicleDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Vehicle and its DTO VehicleDTO.
 */
@Mapper(componentModel = "spring", uses = {VehicleBrandMapper.class, VehicleBrandModelMapper.class, VehicleEnergyMapper.class, VehicleUsageMapper.class, })
public interface VehicleMapper extends EntityMapper <VehicleDTO, Vehicle> {

    @Mapping(source = "brand.id", target = "brandId")
    @Mapping(source = "brand.label", target = "brandLabel")

    @Mapping(source = "model.id", target = "modelId")
    @Mapping(source = "model.label", target = "modelLabel")

    @Mapping(source = "energy.id", target = "energyId")
    @Mapping(source = "energy.label", target = "energyLabel")

    @Mapping(source = "usage.id", target = "usageId")
    @Mapping(source = "usage.label", target = "usageLabel")
    VehicleDTO toDto(Vehicle vehicle); 

    @Mapping(source = "brandId", target = "brand")

    @Mapping(source = "modelId", target = "model")

    @Mapping(source = "energyId", target = "energy")

    @Mapping(source = "usageId", target = "usage")
    Vehicle toEntity(VehicleDTO vehicleDTO); 
    default Vehicle fromId(Long id) {
        if (id == null) {
            return null;
        }
        Vehicle vehicle = new Vehicle();
        vehicle.setId(id);
        return vehicle;
    }
}
