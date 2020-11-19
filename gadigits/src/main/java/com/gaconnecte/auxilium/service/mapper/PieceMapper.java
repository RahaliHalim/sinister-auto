package com.gaconnecte.auxilium.service.mapper;

import com.gaconnecte.auxilium.domain.*;
import com.gaconnecte.auxilium.service.dto.PieceDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Piece and its DTO PieceDTO.
 */
@Mapper(componentModel = "spring", uses = {RefTypePiecesMapper.class, })
public interface PieceMapper extends EntityMapper <PieceDTO, Piece> {

    @Mapping(source = "typePiece.id", target = "typePieceId")
    @Mapping(source = "typePiece.libelle", target = "typePieceLibelle")
    PieceDTO toDto(Piece piece); 

    @Mapping(source = "typePieceId", target = "typePiece")
    @Mapping(target = "detailMos", ignore = true)
    @Mapping(target = "detailsPieces", ignore = true)
    
    Piece toEntity(PieceDTO pieceDTO); 
    default Piece fromId(Long id) {
        if (id == null) {
            return null;
        }
        Piece piece = new Piece();
        piece.setId(id);
        return piece;
    }
}
