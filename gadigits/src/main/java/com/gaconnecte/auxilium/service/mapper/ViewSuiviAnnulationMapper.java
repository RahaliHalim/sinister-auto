package com.gaconnecte.auxilium.service.mapper;

import org.mapstruct.Mapper;

import com.gaconnecte.auxilium.domain.view.ViewSuiviAnnulation;
import com.gaconnecte.auxilium.service.dto.ViewSuiviAnnulationDTO;


@Mapper(componentModel = "spring", uses = {})
public interface ViewSuiviAnnulationMapper  extends EntityMapper <ViewSuiviAnnulationDTO, ViewSuiviAnnulation> {



	 default ViewSuiviAnnulation fromId(Long id) {
	        if (id == null) {
	            return null;
	        }
	        ViewSuiviAnnulation viewSuiviAnnulation = new ViewSuiviAnnulation();
	        viewSuiviAnnulation.setId(id);
	        return viewSuiviAnnulation;
	    }

}
