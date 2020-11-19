package com.gaconnecte.auxilium.service.mapper;

import com.gaconnecte.auxilium.domain.*;
import com.gaconnecte.auxilium.service.dto.VehiclePieceTypeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity VehiclePieceType and its DTO VehiclePieceTypeDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface VehiclePieceTypeMapper extends EntityMapper <VehiclePieceTypeDTO, VehiclePieceType> {
    
    
    default VehiclePieceType fromId(Long id) {
        if (id == null) {
            return null;
        }
        VehiclePieceType vehiclePieceType = new VehiclePieceType();
        vehiclePieceType.setId(id);
        return vehiclePieceType;
    }
}
