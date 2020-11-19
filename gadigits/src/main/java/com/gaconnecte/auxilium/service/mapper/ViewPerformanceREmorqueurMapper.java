package com.gaconnecte.auxilium.service.mapper;

import org.mapstruct.Mapper;

import com.gaconnecte.auxilium.domain.view.ViewPerformanceRemorqueur;
import com.gaconnecte.auxilium.service.dto.ViewPerformanceRemorqueurDTO;



@Mapper(componentModel = "spring", uses = {})
public interface ViewPerformanceREmorqueurMapper extends EntityMapper <ViewPerformanceRemorqueurDTO, ViewPerformanceRemorqueur> {

	default ViewPerformanceRemorqueur fromId(Long id) {
        if (id == null) {
            return null;
        }
        ViewPerformanceRemorqueur viewPerformanceRemorqueur = new ViewPerformanceRemorqueur();
        viewPerformanceRemorqueur.setId(id);
        return viewPerformanceRemorqueur;
    }
	
}
