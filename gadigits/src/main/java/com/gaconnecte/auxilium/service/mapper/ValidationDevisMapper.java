package com.gaconnecte.auxilium.service.mapper;

import com.gaconnecte.auxilium.domain.*;
import com.gaconnecte.auxilium.service.dto.ValidationDevisDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ValidationDevis and its DTO ValidationDevisDTO.
 */
@Mapper(componentModel = "spring", uses = {DevisMapper.class, })
public interface ValidationDevisMapper extends EntityMapper <ValidationDevisDTO, ValidationDevis> {

    @Mapping(source = "devis.id", target = "devisId")
    ValidationDevisDTO toDto(ValidationDevis validationDevis); 

    @Mapping(source = "devisId", target = "devis")
    @Mapping(target = "avisExpPieces", ignore = true)
    @Mapping(target = "avisExpMos", ignore = true)
    ValidationDevis toEntity(ValidationDevisDTO validationDevisDTO); 
    default ValidationDevis fromId(Long id) {
        if (id == null) {
            return null;
        }
        ValidationDevis validationDevis = new ValidationDevis();
        validationDevis.setId(id);
        return validationDevis;
    }
}
