package com.gaconnecte.auxilium.service.mapper;

import com.gaconnecte.auxilium.domain.*;
import com.gaconnecte.auxilium.service.dto.DetailsPiecesDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity DetailsPieces and its DTO DetailsPiecesDTO.
 */
@Mapper(componentModel = "spring", uses = {FournisseurMapper.class, RefTypeInterventionMapper.class, QuotationMapper.class, VehiclePieceMapper.class, VatRateMapper.class, QuotationMPMapper.class})
public interface DetailsPiecesMapper extends EntityMapper <DetailsPiecesDTO, DetailsPieces> {

    @Mapping(source = "fournisseur.id", target = "fournisseurId")

    @Mapping(source = "quotation.id", target = "quotationId")
    @Mapping(source = "quotationMP.id", target = "quotationMPId")

    @Mapping(source = "designation.id", target = "designationId")
    @Mapping(source = "designation.reference", target = "designationReference")
    @Mapping(source = "designation.code", target = "designationCode")
    @Mapping(source = "designation.source", target = "designationSource")
    @Mapping(source = "designation.label", target = "designation")
    @Mapping(source = "typeIntervention.id", target = "typeInterventionId")
    @Mapping(source = "typeIntervention.libelle", target = "typeInterventionLibelle")
    @Mapping(source = "vatRate.id", target = "vatRateId")
    DetailsPiecesDTO toDto(DetailsPieces detailsPieces); 
    
    @Mapping(target = "facturePieces", ignore = true)
    @Mapping(source = "fournisseurId", target = "fournisseur")
    @Mapping(source = "quotationId", target = "quotation")
    @Mapping(source = "typeInterventionId", target = "typeIntervention")
    @Mapping(source = "designationId", target = "designation")
    @Mapping(source = "quotationMPId", target = "quotationMP")
    DetailsPieces toEntity(DetailsPiecesDTO detailsPiecesDTO); 
    default DetailsPieces fromId(Long id) {
        if (id == null) {
            return null;
        }
        DetailsPieces detailsPieces = new DetailsPieces();
        detailsPieces.setId(id);
        return detailsPieces;
    }
}

