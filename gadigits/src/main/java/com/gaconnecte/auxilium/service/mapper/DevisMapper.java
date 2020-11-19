package com.gaconnecte.auxilium.service.mapper;

import com.gaconnecte.auxilium.domain.*;
import com.gaconnecte.auxilium.service.dto.DevisDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Devis and its DTO DevisDTO.
 */
@Mapper(componentModel = "spring", uses = {PrestationPECMapper.class, ReparateurMapper.class, ExpertMapper.class, QuotationStatusMapper.class })
public interface DevisMapper extends EntityMapper <DevisDTO, Devis> {

    @Mapping(source = "prestation.id", target = "prestationId")

    @Mapping(source = "reparateur.id", target = "reparateurId")

    @Mapping(source = "expert.id", target = "expertId")
    
    @Mapping(source = "quotationStatus.id", target = "quotationStatusId")
    
    DevisDTO toDto(Devis devis); 

    @Mapping(source = "prestationId", target = "prestation")

    @Mapping(source = "reparateurId", target = "reparateur")

    @Mapping(source = "expertId", target = "expert")
    
    @Mapping(source = "quotationStatusId", target = "quotationStatus")
    Devis toEntity(DevisDTO devisDTO); 
    default Devis fromId(Long id) {
        if (id == null) {
            return null;
        }
        Devis devis = new Devis();
        devis.setId(id);
        return devis;
    }
}
