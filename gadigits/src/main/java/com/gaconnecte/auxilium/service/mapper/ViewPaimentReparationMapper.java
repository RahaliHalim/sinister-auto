package com.gaconnecte.auxilium.service.mapper;

import org.mapstruct.Mapper;

import com.gaconnecte.auxilium.domain.ViewPaimentReparation;
import com.gaconnecte.auxilium.service.dto.ViewPaimentReparationDTO;

@Mapper(componentModel = "spring", uses = {})
public interface ViewPaimentReparationMapper extends EntityMapper <ViewPaimentReparationDTO, ViewPaimentReparation>{

	
	default ViewPaimentReparation fromId(Long id) {
        if (id == null) {
            return null;
        }
        ViewPaimentReparation viewPaimentReparation = new ViewPaimentReparation();
        viewPaimentReparation.setId(id);
        return viewPaimentReparation;
    }
	
}
