package com.gaconnecte.auxilium.service.mapper;

import com.gaconnecte.auxilium.domain.*;
import com.gaconnecte.auxilium.service.dto.RefTypePjDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity RefTypePj and its DTO RefTypePjDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface RefTypePjMapper extends EntityMapper <RefTypePjDTO, RefTypePj> {
    
    
    default RefTypePj fromId(Long id) {
        if (id == null) {
            return null;
        }
        RefTypePj refTypePj = new RefTypePj();
        refTypePj.setId(id);
        return refTypePj;
    }
}
