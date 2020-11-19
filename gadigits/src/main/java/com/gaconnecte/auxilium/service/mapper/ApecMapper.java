package com.gaconnecte.auxilium.service.mapper;

import com.gaconnecte.auxilium.domain.*;
import com.gaconnecte.auxilium.service.dto.ApecDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Apec and its DTO ApecDTO.
 */
@Mapper(componentModel = "spring", uses = {SinisterPecMapper.class, RaisonPecMapper.class})
public interface ApecMapper extends EntityMapper <ApecDTO, Apec> {

    @Mapping(source = "sinisterPec.id", target = "sinisterPecId")
    @Mapping(source = "accordRaison.id", target = "accordRaisonId")
    ApecDTO toDto(Apec apec); 

    @Mapping(source = "sinisterPecId", target = "sinisterPec")
    @Mapping(source = "accordRaisonId", target = "accordRaison")
    Apec toEntity(ApecDTO apecDTO); 
    default Apec fromId(Long id) {
        if (id == null) {
            return null;
        }
        Apec apec = new Apec();
        apec.setId(id);
        return apec;
    }
}
