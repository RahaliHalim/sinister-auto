package com.gaconnecte.auxilium.service.mapper;

import com.gaconnecte.auxilium.domain.*;
import com.gaconnecte.auxilium.service.dto.ViewSinisterPecDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ViewSinisterPec and its DTO ViewSinisterPecDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ViewSinisterPecMapper extends EntityMapper <ViewSinisterPecDTO, ViewSinisterPec> {
    
    
    default ViewSinisterPec fromId(Long id) {
        if (id == null) {
            return null;
        }
        ViewSinisterPec viewSinisterPec = new ViewSinisterPec();
        viewSinisterPec.setId(id);
        return viewSinisterPec;
    }
}
