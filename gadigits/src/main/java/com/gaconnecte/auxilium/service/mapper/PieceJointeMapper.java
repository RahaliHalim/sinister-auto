package com.gaconnecte.auxilium.service.mapper;

import com.gaconnecte.auxilium.domain.*;
import com.gaconnecte.auxilium.service.dto.PieceJointeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity PieceJointe and its DTO PieceJointeDTO.
 */
@Mapper(componentModel = "spring", uses = {RefTypePjMapper.class, UserMapper.class, PrestationPECMapper.class, DevisMapper.class })
public interface PieceJointeMapper extends EntityMapper <PieceJointeDTO, PieceJointe> {

    @Mapping(source = "type.id", target = "typeId")
    @Mapping(source = "type.libelle", target = "typeLibelle")

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.login", target = "userLogin")

    @Mapping(source = "prestation.id", target = "prestationId")
    //@Mapping(source = "prestation.reference", target = "prestationReference")

    @Mapping(source = "devis.id", target = "devisId")
    PieceJointeDTO toDto(PieceJointe pieceJointe); 

    @Mapping(source = "typeId", target = "type")

    @Mapping(source = "userId", target = "user")

    @Mapping(source = "prestationId", target = "prestation.id")

    @Mapping(source = "devisId", target = "devis")
   
    PieceJointe toEntity(PieceJointeDTO pieceJointeDTO); 
    default PieceJointe fromId(Long id) {
        if (id == null) {
            return null;
        }
        PieceJointe pieceJointe = new PieceJointe();
        pieceJointe.setId(id);
        return pieceJointe;
    }
}
