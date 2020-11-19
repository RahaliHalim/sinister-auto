package com.gaconnecte.auxilium.service.mapper;

import com.gaconnecte.auxilium.domain.*;
import com.gaconnecte.auxilium.service.dto.PolicyReceiptStatusDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity PolicyReceiptStatus and its DTO PolicyReceiptStatusDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PolicyReceiptStatusMapper extends EntityMapper <PolicyReceiptStatusDTO, PolicyReceiptStatus> {
    
    
    default PolicyReceiptStatus fromId(Long id) {
        if (id == null) {
            return null;
        }
        PolicyReceiptStatus policyReceiptStatus = new PolicyReceiptStatus();
        policyReceiptStatus.setId(id);
        return policyReceiptStatus;
    }
}
