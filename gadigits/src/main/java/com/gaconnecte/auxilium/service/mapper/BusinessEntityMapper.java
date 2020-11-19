package com.gaconnecte.auxilium.service.mapper;

import com.gaconnecte.auxilium.domain.*;
import com.gaconnecte.auxilium.service.dto.BusinessEntityDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity BusinessEntity and its DTO BusinessEntityDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface BusinessEntityMapper extends EntityMapper <BusinessEntityDTO, BusinessEntity> {
    
    BusinessEntity toEntity(BusinessEntityDTO businessEntityDTO); 
    default BusinessEntity fromId(Long id) {
        if (id == null) {
            return null;
        }
        BusinessEntity businessEntity = new BusinessEntity();
        businessEntity.setId(id);
        return businessEntity;
    }
}
