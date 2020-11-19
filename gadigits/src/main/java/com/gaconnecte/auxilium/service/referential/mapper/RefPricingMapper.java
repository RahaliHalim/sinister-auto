package com.gaconnecte.auxilium.service.referential.mapper;

import org.mapstruct.Mapper;

import com.gaconnecte.auxilium.domain.referential.RefPricing;
import com.gaconnecte.auxilium.service.mapper.EntityMapper;
import com.gaconnecte.auxilium.service.referential.dto.RefPricingDTO;



/**
 * Mapper for the entity RefPricing and its DTO RefPricingDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface RefPricingMapper extends EntityMapper <RefPricingDTO, RefPricing> {
    
    RefPricing toEntity(RefPricingDTO refPricingDTO); 
    default RefPricing fromId(Long id) {
        if (id == null) {
            return null;
        }
        RefPricing refPricing = new RefPricing();
        refPricing.setId(id);
        return refPricing;
    }
}
