package com.gaconnecte.auxilium.service.mapper;

import com.gaconnecte.auxilium.domain.*;
import com.gaconnecte.auxilium.service.dto.PersonnePhysiqueDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity PersonnePhysique and its DTO PersonnePhysiqueDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, DelegationMapper.class, })
public interface PersonnePhysiqueMapper extends EntityMapper <PersonnePhysiqueDTO, PersonnePhysique> {

    @Mapping(source = "user.id", target = "userId")

    @Mapping(source = "ville.id", target = "villeId")
    @Mapping(source = "ville.label", target = "villeLibelle")
    PersonnePhysiqueDTO toDto(PersonnePhysique personnePhysique); 

    @Mapping(source = "userId", target = "user")

    @Mapping(source = "villeId", target = "ville")
    PersonnePhysique toEntity(PersonnePhysiqueDTO personnePhysiqueDTO); 
    default PersonnePhysique fromId(Long id) {
        if (id == null) {
            return null;
        }
        PersonnePhysique personnePhysique = new PersonnePhysique();
        personnePhysique.setId(id);
        return personnePhysique;
    }
}