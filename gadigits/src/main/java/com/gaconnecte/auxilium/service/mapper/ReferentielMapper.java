package com.gaconnecte.auxilium.service.mapper;

import org.mapstruct.Mapper;

import com.gaconnecte.auxilium.domain.Referentiel;
import com.gaconnecte.auxilium.service.dto.ReferentielDTO;


@Mapper(componentModel = "spring", uses = {})
public interface ReferentielMapper extends EntityMapper <ReferentielDTO, Referentiel> {
    
	
	ReferentielDTO toDto(Referentiel referentiel); 
	
	Referentiel toEntity(ReferentielDTO referentielDTO); 
    default Referentiel fromId(Long id) {
        if (id == null) {
            return null;
        }
        Referentiel referentiel = new Referentiel();
        referentiel.setId(id);
        return referentiel;
    }
}