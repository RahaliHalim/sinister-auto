package com.gaconnecte.auxilium.service.mapper;

import com.gaconnecte.auxilium.service.dto.ViewSuiviDossiersDTO;
import com.gaconnecte.auxilium.domain.view.ViewSuiviDossiers;
import org.mapstruct.Mapper;



@Mapper(componentModel = "spring", uses = {})
public interface ViewSuiviDossiersMapper extends EntityMapper <ViewSuiviDossiersDTO, ViewSuiviDossiers>{

	

	 
    default ViewSuiviDossiers fromId(Long id) {
        if (id == null) {
            return null;
        }
        ViewSuiviDossiers viewSuiviDossiers = new ViewSuiviDossiers();
        viewSuiviDossiers.setId(id);
        return viewSuiviDossiers;
    }
	
	
	
}
