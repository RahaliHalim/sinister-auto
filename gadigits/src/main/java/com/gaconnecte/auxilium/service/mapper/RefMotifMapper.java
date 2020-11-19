package com.gaconnecte.auxilium.service.mapper;

import com.gaconnecte.auxilium.domain.*;
import com.gaconnecte.auxilium.service.dto.RefMotifDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity RefMotif and its DTO RefMotifDTO.
 */
@Mapper(componentModel = "spring", uses = {AuthorityMapper.class,})
public interface RefMotifMapper extends EntityMapper <RefMotifDTO, RefMotif> {

    @Mapping(source = "authority.name", target = "authorityName")
    RefMotifDTO toDto(RefMotif refMotif); 
    @Mapping(target = "journals", ignore = true)
    //@Mapping(target = "actionUtilisateurs", ignore = true)
    @Mapping(source = "authorityName", target = "authority")
    RefMotif toEntity(RefMotifDTO refMotifDTO); 

    default RefMotif fromId(Long id) {
        if (id == null) {
            return null;
        }
        RefMotif refMotif = new RefMotif();
        refMotif.setId(id);
        return refMotif;
    }
}
