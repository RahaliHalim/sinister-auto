package com.gaconnecte.auxilium.service.mapper;

import com.gaconnecte.auxilium.domain.*;
import com.gaconnecte.auxilium.service.dto.FactureDetailsMoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity FactureDetailsMo and its DTO FactureDetailsMoDTO.
 */
@Mapper(componentModel = "spring", uses = {DetailsMoMapper.class, FactureMapper.class, })
public interface FactureDetailsMoMapper extends EntityMapper <FactureDetailsMoDTO, FactureDetailsMo> {

    @Mapping(source = "detailsMo.id", target = "detailsMoId")

    @Mapping(source = "facture.id", target = "factureId")
    FactureDetailsMoDTO toDto(FactureDetailsMo factureDetailsMo); 

    @Mapping(source = "detailsMoId", target = "detailsMo")

    @Mapping(source = "factureId", target = "facture")
    FactureDetailsMo toEntity(FactureDetailsMoDTO factureDetailsMoDTO); 
    default FactureDetailsMo fromId(Long id) {
        if (id == null) {
            return null;
        }
        FactureDetailsMo factureDetailsMo = new FactureDetailsMo();
        factureDetailsMo.setId(id);
        return factureDetailsMo;
    }
}
