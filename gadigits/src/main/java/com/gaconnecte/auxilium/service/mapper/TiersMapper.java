package com.gaconnecte.auxilium.service.mapper;

import com.gaconnecte.auxilium.domain.*;
import com.gaconnecte.auxilium.service.dto.TiersDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Tiers and its DTO TiersDTO.
 */
@Mapper(componentModel = "spring", uses = {PartnerMapper.class, AgencyMapper.class, SinisterPecMapper.class})
public interface TiersMapper extends EntityMapper <TiersDTO, Tiers> {
    
    @Mapping(source = "compagnie.id", target = "compagnieId")
    @Mapping(source = "compagnie.companyName", target = "compagnieNom")
    @Mapping(source = "sinisterPec.id", target = "sinisterPecId")
    @Mapping(source = "agence.id", target = "agenceId")
    @Mapping(source = "agence.name", target = "agenceNom")
     TiersDTO toDto(Tiers tiers); 

    
    @Mapping(source = "compagnieId", target = "compagnie")
    @Mapping(source = "sinisterPecId", target = "sinisterPec")
    @Mapping(source = "agenceId", target = "agence")
    Tiers toEntity(TiersDTO tiersDTO); 
    default Tiers fromId(Long id) {
        if (id == null) {
            return null;
        }
        Tiers tiers = new Tiers();
        tiers.setId(id);
        return tiers;
    }
}
