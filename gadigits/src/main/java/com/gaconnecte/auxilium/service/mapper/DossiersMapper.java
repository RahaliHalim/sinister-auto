package com.gaconnecte.auxilium.service.mapper;

import org.mapstruct.Mapper;

import com.gaconnecte.auxilium.domain.Dossiers;
import com.gaconnecte.auxilium.service.dto.DossiersDTO;

@Mapper(componentModel = "spring", uses = {})
public interface DossiersMapper extends EntityMapper <DossiersDTO, Dossiers> {
    
	 DossiersDTO toDto(Dossiers dossiers); 

	  Dossiers toEntity(DossiersDTO dossiersDTO); 
	  default Dossiers fromId(Long id) {
	        if (id == null) {
	            return null;
	        }
	        Dossiers dossiers = new Dossiers();
	        dossiers.setId(id);
	        return dossiers;
	    }
}