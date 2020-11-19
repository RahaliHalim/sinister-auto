package com.gaconnecte.auxilium.service.mapper;

import com.gaconnecte.auxilium.domain.*;
import com.gaconnecte.auxilium.service.dto.AmendmentTypeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity AmendmentType and its DTO AmendmentTypeDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AmendmentTypeMapper extends EntityMapper <AmendmentTypeDTO, AmendmentType> {
    
    
    default AmendmentType fromId(Long id) {
        if (id == null) {
            return null;
        }
        AmendmentType amendmentType = new AmendmentType();
        amendmentType.setId(id);
        return amendmentType;
    }
}
