package com.gaconnecte.auxilium.service.mapper;

import org.mapstruct.Mapper;

import com.gaconnecte.auxilium.domain.view.ViewDelaiMoyImmobilisation;
import com.gaconnecte.auxilium.service.dto.ViewDelaiMoyImmobilisationDTO;

@Mapper(componentModel = "spring", uses = {})
public interface ViewDelaiMoyImmobilisationMapper  extends EntityMapper <ViewDelaiMoyImmobilisationDTO, ViewDelaiMoyImmobilisation>{

	  default ViewDelaiMoyImmobilisation fromId(Long id) {
	        if (id == null) {
	            return null;
	        }
	        ViewDelaiMoyImmobilisation viewDelaiMoyImmobilisation = new ViewDelaiMoyImmobilisation();
	        viewDelaiMoyImmobilisation.setId(id);
	        return viewDelaiMoyImmobilisation;
	    }
	
	
}
