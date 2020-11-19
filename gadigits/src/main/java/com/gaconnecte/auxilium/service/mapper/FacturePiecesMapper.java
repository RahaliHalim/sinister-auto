package com.gaconnecte.auxilium.service.mapper;

import com.gaconnecte.auxilium.domain.*;
import com.gaconnecte.auxilium.service.dto.FacturePiecesDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity FacturePieces and its DTO FacturePiecesDTO.
 */
@Mapper(componentModel = "spring", uses = {DetailsPiecesMapper.class, FactureMapper.class, })
public interface FacturePiecesMapper extends EntityMapper <FacturePiecesDTO, FacturePieces> {

    @Mapping(source = "detailsPieces.id", target = "detailsPiecesId")

    @Mapping(source = "facture.id", target = "factureId")
    FacturePiecesDTO toDto(FacturePieces facturePieces); 

    @Mapping(source = "detailsPiecesId", target = "detailsPieces")

    @Mapping(source = "factureId", target = "facture")
    FacturePieces toEntity(FacturePiecesDTO facturePiecesDTO); 
    default FacturePieces fromId(Long id) {
        if (id == null) {
            return null;
        }
        FacturePieces facturePieces = new FacturePieces();
        facturePieces.setId(id);
        return facturePieces;
    }
}
