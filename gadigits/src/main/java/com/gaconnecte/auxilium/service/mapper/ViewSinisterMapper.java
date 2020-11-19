/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gaconnecte.auxilium.service.mapper;

import com.gaconnecte.auxilium.domain.view.ViewSinister;
import com.gaconnecte.auxilium.service.dto.ViewSinisterDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity ViewSinister and its DTO ViewSinisterDTO.
 * @author hannibaal
 */
@Mapper(componentModel = "spring", uses = {})
public interface ViewSinisterMapper extends EntityMapper <ViewSinisterDTO, ViewSinister> {
    
    
    default ViewSinister fromId(Long id) {
        if (id == null) {
            return null;
        }
        ViewSinister viewSinister = new ViewSinister();
        viewSinister.setId(id);
        return viewSinister;
    }
}
