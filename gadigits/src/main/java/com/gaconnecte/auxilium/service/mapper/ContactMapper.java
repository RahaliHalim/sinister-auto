package com.gaconnecte.auxilium.service.mapper;

import com.gaconnecte.auxilium.domain.*;
import com.gaconnecte.auxilium.service.dto.ContactDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Contact and its DTO ContactDTO.
 */
@Mapper(componentModel = "spring", uses = {PersonnePhysiqueMapper.class, DelegationMapper.class, GovernorateMapper.class, PersonneMoraleMapper.class,})
public interface ContactMapper extends EntityMapper <ContactDTO, Contact> {

    @Mapping(source = "personnePhysique.id", target = "personnePhysiqueId")
    @Mapping(source = "personnePhysique.nom", target = "personnePhysiqueNom")
    @Mapping(source = "personnePhysique.prenom", target = "personnePhysiquePrenom")
    @Mapping(source = "personnePhysique.premTelephone", target = "personnePhysiqueTel")
    @Mapping(source = "personnePhysique.premMail", target = "personnePhysiqueEmail")
    @Mapping(source = "personnePhysique.fax", target = "personnePhysiqueFax")
    @Mapping(source = "personneMorale.id", target = "personneMoraleId")
    ContactDTO toDto(Contact contact); 

  
    
    @Mapping(source = "personnePhysiqueId", target = "personnePhysique")
    @Mapping(source = "personneMoraleId", target = "personneMorale")
    Contact toEntity(ContactDTO contactDTO); 
    default Contact fromId(Long id) {
        if (id == null) {
            return null;
        }
        Contact contact = new Contact();
        contact.setId(id);
        return contact;
    }
}
