package com.gaconnecte.auxilium.service.mapper;

import com.gaconnecte.auxilium.domain.*;
import com.gaconnecte.auxilium.service.dto.VehicleUsageDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity VehicleUsage and its DTO VehicleUsageDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface VehicleUsageMapper extends EntityMapper <VehicleUsageDTO, VehicleUsage> {
    
    
    default VehicleUsage fromId(Long id) {
        if (id == null) {
            return null;
        }
        VehicleUsage vehicleUsage = new VehicleUsage();
        vehicleUsage.setId(id);
        return vehicleUsage;
    }
}
