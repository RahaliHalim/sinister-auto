/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gaconnecte.auxilium.service.mapper;

import com.gaconnecte.auxilium.domain.view.ViewDetailQuotation;
import com.gaconnecte.auxilium.service.dto.ViewDetailQuotationDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity ViewDetailQuotation and its DTO ViewDetailQuotationDTO.
 * 
 */
@Mapper(componentModel = "spring", uses = {})
public interface ViewDetailQuotationMapper extends EntityMapper <ViewDetailQuotationDTO, ViewDetailQuotation> {
    
    
    default ViewDetailQuotation fromId(Long id) {
        if (id == null) {
            return null;
        }
        ViewDetailQuotation viewDetailQuotation = new ViewDetailQuotation();
        viewDetailQuotation.setId(id);
        return viewDetailQuotation;
    }
}
