/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gaconnecte.auxilium.service.mapper;

import com.gaconnecte.auxilium.domain.view.ViewVehiclePieces;
import com.gaconnecte.auxilium.service.dto.ViewVehiclePiecesDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity ViewVehiclePieces and its DTO ViewVehiclePiecesDTO.
 * @author hannibaal
 */
@Mapper(componentModel = "spring", uses = {})
public interface ViewVehiclePiecesMapper extends EntityMapper <ViewVehiclePiecesDTO, ViewVehiclePieces> {
    
    
    default ViewVehiclePieces fromId(Long id) {
        if (id == null) {
            return null;
        }
        ViewVehiclePieces viewVehiclePiece = new ViewVehiclePieces();
        viewVehiclePiece.setId(id);
        return viewVehiclePiece;
    }
}
