/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gaconnecte.auxilium.service.mapper;

import com.gaconnecte.auxilium.domain.view.ViewExpert;
import com.gaconnecte.auxilium.service.dto.ViewExpertDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity ViewExpert and its DTO ViewExpertDTO.
 * @author hannibaal
 */
@Mapper(componentModel = "spring", uses = {})
public interface ViewExpertMapper extends EntityMapper <ViewExpertDTO, ViewExpert> {
    
    
    default ViewExpert fromId(Long id) {
        if (id == null) {
            return null;
        }
        ViewExpert viewExpert = new ViewExpert();
        viewExpert.setId(id);
        return viewExpert;
    }
}
