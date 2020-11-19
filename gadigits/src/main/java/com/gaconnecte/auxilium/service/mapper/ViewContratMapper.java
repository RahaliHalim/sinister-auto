package com.gaconnecte.auxilium.service.mapper;

import com.gaconnecte.auxilium.service.dto.ViewContratDTO;
import com.gaconnecte.auxilium.domain.view.ViewContrat;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring", uses = {})
public interface ViewContratMapper extends EntityMapper <ViewContratDTO, ViewContrat>{

	
	 
    default ViewContrat fromId(Long id) {
        if (id == null) {
            return null;
        }
        ViewContrat viewContrat = new ViewContrat();
        viewContrat.setId(id);
        return viewContrat;
    }
	
	
}
