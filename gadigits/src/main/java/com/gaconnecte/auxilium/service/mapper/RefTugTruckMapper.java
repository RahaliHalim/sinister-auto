package com.gaconnecte.auxilium.service.mapper;

import com.gaconnecte.auxilium.domain.*;
import com.gaconnecte.auxilium.service.dto.RefTugTruckDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity RefTugTruck and its DTO TarifDTO.
 */
@Mapper(componentModel = "spring", uses = {RefRemorqueurMapper.class, RefTypeServiceMapper.class})
public interface RefTugTruckMapper extends EntityMapper <RefTugTruckDTO, RefTugTruck> {
    
	 @Mapping(source = "refTug.id", target = "refTugId")
	 RefTugTruckDTO toDto(RefTugTruck truck);
	 
	 @Mapping(source = "refTugId", target = "refTug")
	 RefTugTruck toEntity(RefTugTruckDTO truckDTO); 
	 
    default RefTugTruck fromId(Long id) {
        if (id == null) {
            return null;
        }
        RefTugTruck truck = new RefTugTruck();
        truck.setId(id);
        return truck;
    }
}
