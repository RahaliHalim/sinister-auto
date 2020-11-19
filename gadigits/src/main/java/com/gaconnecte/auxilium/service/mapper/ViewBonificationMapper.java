package com.gaconnecte.auxilium.service.mapper;


import com.gaconnecte.auxilium.domain.view.ViewBonification;
import com.gaconnecte.auxilium.service.dto.ViewBonificationDTO;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring", uses = {})
public interface ViewBonificationMapper extends EntityMapper <ViewBonificationDTO, ViewBonification> {

	
	 default ViewBonification fromId(Long id) {
	        if (id == null) {
	            return null;
	        }
	        ViewBonification viewBonification = new ViewBonification();
	        viewBonification.setId(id);
	        return viewBonification;
	    }
}
