package com.gaconnecte.auxilium.service.mapper;

import com.gaconnecte.auxilium.domain.*;
import com.gaconnecte.auxilium.service.dto.RefPositionGaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity RefPositionGa and its DTO RefPositionGaDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface RefPositionGaMapper extends EntityMapper <RefPositionGaDTO, RefPositionGa> {
    
    
    default RefPositionGa fromId(Long id) {
        if (id == null) {
            return null;
        }
        RefPositionGa refPositionGa = new RefPositionGa();
        refPositionGa.setId(id);
        return refPositionGa;
    }
}
