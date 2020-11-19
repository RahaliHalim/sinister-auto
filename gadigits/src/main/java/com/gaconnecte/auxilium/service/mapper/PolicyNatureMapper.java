package com.gaconnecte.auxilium.service.mapper;

import com.gaconnecte.auxilium.domain.*;
import com.gaconnecte.auxilium.service.dto.PolicyNatureDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity PolicyNature and its DTO PolicyNatureDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PolicyNatureMapper extends EntityMapper <PolicyNatureDTO, PolicyNature> {
    
    
    default PolicyNature fromId(Long id) {
        if (id == null) {
            return null;
        }
        PolicyNature policyNature = new PolicyNature();
        policyNature.setId(id);
        return policyNature;
    }
}
