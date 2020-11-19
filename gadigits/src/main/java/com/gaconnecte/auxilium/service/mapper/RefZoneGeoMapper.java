package com.gaconnecte.auxilium.service.mapper;

import com.gaconnecte.auxilium.domain.*;
import com.gaconnecte.auxilium.service.dto.RefZoneGeoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity RefZoneGeo and its DTO RefZoneGeoDTO.
 */
@Mapper(componentModel = "spring", uses = {GovernorateMapper.class, })
public interface RefZoneGeoMapper extends EntityMapper <RefZoneGeoDTO, RefZoneGeo> {
    
    
    default RefZoneGeo fromId(Long id) {
        if (id == null) {
            return null;
        }
        RefZoneGeo refZoneGeo = new RefZoneGeo();
        refZoneGeo.setId(id);
        return refZoneGeo;
    }
}
