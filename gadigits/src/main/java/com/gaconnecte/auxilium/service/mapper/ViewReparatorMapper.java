/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gaconnecte.auxilium.service.mapper;

import com.gaconnecte.auxilium.domain.view.ViewReparator;
import com.gaconnecte.auxilium.service.dto.ViewReparatorDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity ViewReparator and its DTO ViewReparatorDTO.
 * @author hannibaal
 */
@Mapper(componentModel = "spring", uses = {})
public interface ViewReparatorMapper extends EntityMapper <ViewReparatorDTO, ViewReparator> {
    
    
    default ViewReparator fromId(Long id) {
        if (id == null) {
            return null;
        }
        ViewReparator viewReparator = new ViewReparator();
        viewReparator.setId(id);
        return viewReparator;
    }
}
