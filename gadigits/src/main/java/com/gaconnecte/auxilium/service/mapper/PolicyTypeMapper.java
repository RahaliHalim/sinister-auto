package com.gaconnecte.auxilium.service.mapper;

import com.gaconnecte.auxilium.domain.*;
import com.gaconnecte.auxilium.service.dto.PolicyTypeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity PolicyType and its DTO PolicyTypeDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PolicyTypeMapper extends EntityMapper <PolicyTypeDTO, PolicyType> {
    
    
    default PolicyType fromId(Long id) {
        if (id == null) {
            return null;
        }
        PolicyType policyType = new PolicyType();
        policyType.setId(id);
        return policyType;
    }
}
