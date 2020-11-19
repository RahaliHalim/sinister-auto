package com.gaconnecte.auxilium.service.mapper;

import com.gaconnecte.auxilium.domain.*;
import com.gaconnecte.auxilium.service.dto.DetailsMoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity DetailsMo and its DTO DetailsMoDTO.
 */
@Mapper(componentModel = "spring", uses = {DevisMapper.class, RefTypeInterventionMapper.class, PieceMapper.class,  })
public interface DetailsMoMapper extends EntityMapper <DetailsMoDTO, DetailsMo> {

    @Mapping(source = "devis.id", target = "devisId")

    @Mapping(source = "designation.id", target = "designationId")
    @Mapping(source = "designation.reference", target = "designationReference")

    @Mapping(source = "typeIntervention.id", target = "typeInterventionId")
    @Mapping(source = "typeIntervention.libelle", target = "typeInterventionLibelle")
    DetailsMoDTO toDto(DetailsMo detailsMo); 
    @Mapping(target = "factureMos", ignore = true)

    @Mapping(source = "designationId", target = "designation")

    @Mapping(source = "devisId", target = "devis")

    @Mapping(source = "typeInterventionId", target = "typeIntervention")
    DetailsMo toEntity(DetailsMoDTO detailsMoDTO); 
    default DetailsMo fromId(Long id) {
        if (id == null) {
            return null;
        }
        DetailsMo detailsMo = new DetailsMo();
        detailsMo.setId(id);
        return detailsMo;
    }
}
