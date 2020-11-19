package com.gaconnecte.auxilium.service.mapper;

import com.gaconnecte.auxilium.domain.*;
import com.gaconnecte.auxilium.service.dto.PolicyStatusDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity PolicyStatus and its DTO PolicyStatusDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PolicyStatusMapper extends EntityMapper <PolicyStatusDTO, PolicyStatus> {
    
    
    default PolicyStatus fromId(Long id) {
        if (id == null) {
            return null;
        }
        PolicyStatus policyStatus = new PolicyStatus();
        policyStatus.setId(id);
        return policyStatus;
    }
}
