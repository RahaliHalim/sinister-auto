package com.gaconnecte.auxilium.service.mapper;

import com.gaconnecte.auxilium.domain.*;
import com.gaconnecte.auxilium.service.dto.AvisExpertMoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity AvisExpertMo and its DTO AvisExpertMoDTO.
 */
@Mapper(componentModel = "spring", uses = {DetailsMoMapper.class, ValidationDevisMapper.class, })
public interface AvisExpertMoMapper extends EntityMapper <AvisExpertMoDTO, AvisExpertMo> {

    @Mapping(source = "detailsMo.id", target = "detailsMoId")

    @Mapping(source = "validationDevis.id", target = "validationDevisId")
    AvisExpertMoDTO toDto(AvisExpertMo avisExpertMo); 

    @Mapping(source = "detailsMoId", target = "detailsMo")

    @Mapping(source = "validationDevisId", target = "validationDevis")
    AvisExpertMo toEntity(AvisExpertMoDTO avisExpertMoDTO); 
    default AvisExpertMo fromId(Long id) {
        if (id == null) {
            return null;
        }
        AvisExpertMo avisExpertMo = new AvisExpertMo();
        avisExpertMo.setId(id);
        return avisExpertMo;
    }
}
