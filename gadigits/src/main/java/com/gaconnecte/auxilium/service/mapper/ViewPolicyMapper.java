package com.gaconnecte.auxilium.service.mapper;

import com.gaconnecte.auxilium.domain.view.ViewPolicy;
import com.gaconnecte.auxilium.domain.*;
import com.gaconnecte.auxilium.service.dto.ViewPolicyDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ViewPolicy and its DTO ViewPolicyDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ViewPolicyMapper extends EntityMapper <ViewPolicyDTO, ViewPolicy> {
    
    
    default ViewPolicy fromId(Long id) {
        if (id == null) {
            return null;
        }
        ViewPolicy viewPolicy = new ViewPolicy();
        viewPolicy.setId(id);
        return viewPolicy;
    }
}
