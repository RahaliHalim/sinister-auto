package com.gaconnecte.auxilium.service.mapper;

import com.gaconnecte.auxilium.domain.*;
import com.gaconnecte.auxilium.service.dto.ObservationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Observation and its DTO ObservationDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, SinisterPecMapper.class, DevisMapper.class, SinisterPrestationMapper.class})
public interface ObservationMapper extends EntityMapper <ObservationDTO, Observation> {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.login", target = "userLogin")
    @Mapping(source = "user.firstName", target = "firstName")
    @Mapping(source = "user.lastName", target = "lastName")

    @Mapping(source = "sinisterPec.id", target = "sinisterPecId")
    @Mapping(source = "sinisterPrestation.id", target = "sinisterPrestationId")
    //@Mapping(source = "prestation.reference", target = "prestationReference")

    @Mapping(source = "devis.id", target = "devisId")
     
     ObservationDTO toDto(Observation observation); 

     @Mapping(source = "userId", target = "user")
     @Mapping(source = "sinisterPecId", target = "sinisterPec")
     @Mapping(source = "sinisterPrestationId", target = "sinisterPrestation")

     @Mapping(source = "devisId", target = "devis")

     Observation toEntity(ObservationDTO observationDTO); 
    
    default Observation fromId(Long id) {
        if (id == null) {
            return null;
        }
        Observation observation = new Observation();
        observation.setId(id);
        return observation;
    }
}
