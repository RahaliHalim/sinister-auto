/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gaconnecte.auxilium.service.mapper;

import com.gaconnecte.auxilium.domain.view.ViewSinisterPrestation;
import com.gaconnecte.auxilium.service.dto.ViewSinisterPrestationDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity ViewSinisterPrestation and its DTO ViewSinisterPrestationDTO.
 * @author hannibaal
 */
@Mapper(componentModel = "spring", uses = {})
public interface ViewSinisterPrestationMapper extends EntityMapper <ViewSinisterPrestationDTO, ViewSinisterPrestation> {
    
    
    default ViewSinisterPrestation fromId(Long id) {
        if (id == null) {
            return null;
        }
        ViewSinisterPrestation viewSinisterPrestation = new ViewSinisterPrestation();
        viewSinisterPrestation.setId(id);
        return viewSinisterPrestation;
    }
}
