package com.gaconnecte.auxilium.service.mapper;

import com.gaconnecte.auxilium.service.dto.ViewSouscriptionGaDTO;
import com.gaconnecte.auxilium.domain.view.ViewSouscriptionGa;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring", uses = {})
public interface ViewSouscriptionGaMapper extends EntityMapper <ViewSouscriptionGaDTO, ViewSouscriptionGa>{

	
	

	 
    default ViewSouscriptionGa fromId(Long id) {
        if (id == null) {
            return null;
        }
        ViewSouscriptionGa viewSouscriptionGa = new ViewSouscriptionGa();
        viewSouscriptionGa.setId(id);
        return viewSouscriptionGa;
    }
	
	
}
