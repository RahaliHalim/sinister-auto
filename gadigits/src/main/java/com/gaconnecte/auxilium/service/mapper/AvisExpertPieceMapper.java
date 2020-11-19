package com.gaconnecte.auxilium.service.mapper;

import com.gaconnecte.auxilium.domain.*;
import com.gaconnecte.auxilium.service.dto.AvisExpertPieceDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity AvisExpertPiece and its DTO AvisExpertPieceDTO.
 */
@Mapper(componentModel = "spring", uses = {DetailsPiecesMapper.class, ValidationDevisMapper.class, })
public interface AvisExpertPieceMapper extends EntityMapper <AvisExpertPieceDTO, AvisExpertPiece> {

    @Mapping(source = "detailsPieces.id", target = "detailsPiecesId")

    @Mapping(source = "validationDevis.id", target = "validationDevisId")
    AvisExpertPieceDTO toDto(AvisExpertPiece avisExpertPiece); 

    @Mapping(source = "detailsPiecesId", target = "detailsPieces")

    @Mapping(source = "validationDevisId", target = "validationDevis")
    AvisExpertPiece toEntity(AvisExpertPieceDTO avisExpertPieceDTO); 
    default AvisExpertPiece fromId(Long id) {
        if (id == null) {
            return null;
        }
        AvisExpertPiece avisExpertPiece = new AvisExpertPiece();
        avisExpertPiece.setId(id);
        return avisExpertPiece;
    }
}
