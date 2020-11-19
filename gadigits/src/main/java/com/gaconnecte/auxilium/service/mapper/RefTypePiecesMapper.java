package com.gaconnecte.auxilium.service.mapper;

import com.gaconnecte.auxilium.domain.*;
import com.gaconnecte.auxilium.service.dto.RefTypePiecesDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity RefTypePieces and its DTO RefTypePiecesDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface RefTypePiecesMapper extends EntityMapper <RefTypePiecesDTO, RefTypePieces> {
    
    
    default RefTypePieces fromId(Long id) {
        if (id == null) {
            return null;
        }
        RefTypePieces refTypePieces = new RefTypePieces();
        refTypePieces.setId(id);
        return refTypePieces;
    }
}
