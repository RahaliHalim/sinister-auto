package com.gaconnecte.auxilium.service.mapper;

import com.gaconnecte.auxilium.domain.*;
import com.gaconnecte.auxilium.service.dto.VehicleBrandModelDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity VehicleBrandModel and its DTO VehicleBrandModelDTO.
 */
@Mapper(componentModel = "spring", uses = {VehicleBrandMapper.class, })
public interface VehicleBrandModelMapper extends EntityMapper <VehicleBrandModelDTO, VehicleBrandModel> {

    @Mapping(source = "brand.id", target = "brandId")
    @Mapping(source = "brand.label", target = "brandLabel")
    VehicleBrandModelDTO toDto(VehicleBrandModel vehicleBrandModel); 

    @Mapping(source = "brandId", target = "brand")
    VehicleBrandModel toEntity(VehicleBrandModelDTO vehicleBrandModelDTO); 
    default VehicleBrandModel fromId(Long id) {
        if (id == null) {
            return null;
        }
        VehicleBrandModel vehicleBrandModel = new VehicleBrandModel();
        vehicleBrandModel.setId(id);
        return vehicleBrandModel;
    }
}
