package com.gaconnecte.auxilium.service.mapper;

import com.gaconnecte.auxilium.domain.*;
import com.gaconnecte.auxilium.service.dto.VehicleBrandDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity VehicleBrand and its DTO VehicleBrandDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface VehicleBrandMapper extends EntityMapper <VehicleBrandDTO, VehicleBrand> {
    
    
    default VehicleBrand fromId(Long id) {
        if (id == null) {
            return null;
        }
        VehicleBrand vehicleBrand = new VehicleBrand();
        vehicleBrand.setId(id);
        return vehicleBrand;
    }
}
