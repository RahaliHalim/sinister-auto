package com.gaconnecte.auxilium.service.referential.mapper;

import org.mapstruct.Mapper;

import com.gaconnecte.auxilium.domain.referential.RefVehicleUsage;
import com.gaconnecte.auxilium.service.mapper.EntityMapper;
import com.gaconnecte.auxilium.service.referential.dto.RefVehicleUsageDTO;



/**
 * Mapper for the entity RefVehicleUsage and its DTO RefVehicleUsageDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface RefVehicleUsageMapper extends EntityMapper <RefVehicleUsageDTO, RefVehicleUsage> {
    
	RefVehicleUsage toEntity(RefVehicleUsageDTO refVehicleUsageDTO); 
    default RefVehicleUsage fromId(Long id) {
        if (id == null) {
            return null;
        }
        RefVehicleUsage refVehicleUsage = new RefVehicleUsage();
        refVehicleUsage.setId(id);
        return refVehicleUsage;
    }
}
