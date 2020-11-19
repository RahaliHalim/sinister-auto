package com.gaconnecte.auxilium.service.mapper;

import com.gaconnecte.auxilium.domain.*;
import com.gaconnecte.auxilium.service.dto.GovernorateDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Governorate and its DTO GovernorateDTO.
 */
@Mapper(componentModel = "spring", uses = {RegionMapper.class})
public interface GovernorateMapper extends EntityMapper <GovernorateDTO, Governorate> {
    
	@Mapping(source = "region.id", target = "regionId")
    @Mapping(source = "region.label", target = "regionLabel")
	GovernorateDTO toDto(Governorate Governorate);
    default Governorate fromId(Long id) {
        if (id == null) {
            return null;
        }
        Governorate governorate = new Governorate();
        governorate.setId(id);
        return governorate;
    }
}
