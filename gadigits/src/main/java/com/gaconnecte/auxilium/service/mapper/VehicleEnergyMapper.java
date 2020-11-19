package com.gaconnecte.auxilium.service.mapper;

import com.gaconnecte.auxilium.domain.*;
import com.gaconnecte.auxilium.service.dto.VehicleEnergyDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity VehicleEnergy and its DTO VehicleEnergyDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface VehicleEnergyMapper extends EntityMapper <VehicleEnergyDTO, VehicleEnergy> {
    
    
    default VehicleEnergy fromId(Long id) {
        if (id == null) {
            return null;
        }
        VehicleEnergy vehicleEnergy = new VehicleEnergy();
        vehicleEnergy.setId(id);
        return vehicleEnergy;
    }
}
