package com.gaconnecte.auxilium.service.mapper;

import com.gaconnecte.auxilium.domain.*;
import com.gaconnecte.auxilium.service.dto.VehiclePieceDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity VehiclePiece and its DTO VehiclePieceDTO.
 */
@Mapper(componentModel = "spring", uses = {VehiclePieceTypeMapper.class})
public interface VehiclePieceMapper extends EntityMapper <VehiclePieceDTO, VehiclePiece> {
    
    @Mapping(source = "type.id", target = "typeId")
    @Mapping(source = "type.label", target = "typeLabel")
    VehiclePieceDTO toDto(VehiclePiece vehiclePiece); 

    @Mapping(source = "typeId", target = "type")
    VehiclePiece toEntity(VehiclePieceDTO vehiclePieceDTO); 
    
    default VehiclePiece fromId(Long id) {
        if (id == null) {
            return null;
        }
        VehiclePiece vehiclePiece = new VehiclePiece();
        vehiclePiece.setId(id);
        return vehiclePiece;
    }
}
