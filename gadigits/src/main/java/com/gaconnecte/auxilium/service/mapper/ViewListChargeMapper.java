package com.gaconnecte.auxilium.service.mapper;

import com.gaconnecte.auxilium.service.dto.ViewListChargeDTO;
import com.gaconnecte.auxilium.domain.view.ViewListCharge;
import org.mapstruct.Mapper;



@Mapper(componentModel = "spring", uses = {})
public interface ViewListChargeMapper extends EntityMapper <ViewListChargeDTO, ViewListCharge> {
	
	
	 
    default ViewListCharge fromId(Long id) {
        if (id == null) {
            return null;
        }
        ViewListCharge viewListCharge = new ViewListCharge();
        viewListCharge.setId(id);
        return viewListCharge;
    }

}
