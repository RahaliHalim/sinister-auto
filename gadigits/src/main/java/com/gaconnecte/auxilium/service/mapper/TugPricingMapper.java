package com.gaconnecte.auxilium.service.mapper;

import com.gaconnecte.auxilium.domain.*;

import com.gaconnecte.auxilium.service.dto.TugPricingDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity TugPricing and its DTO TugPricingDTO.
 */
@Mapper(componentModel = "spring", uses = {RefRemorqueurMapper.class,})
public interface TugPricingMapper extends EntityMapper <TugPricingDTO, TugPricing> {
	@Mapping(source = "tug.id", target = "tugId")
	TugPricingDTO toDto(TugPricing tugPricing); 
	 @Mapping(source = "tugId", target = "tug")
	 TugPricing toEntity(TugPricingDTO tugPricingDTO); 
    default TugPricing fromId(Long id) {
        if (id == null) {
            return null;
        }
        TugPricing tugPricing = new TugPricing();
        tugPricing.setId(id);
        return tugPricing;
    }
}