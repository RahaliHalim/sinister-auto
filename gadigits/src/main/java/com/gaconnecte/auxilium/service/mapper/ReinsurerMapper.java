package com.gaconnecte.auxilium.service.mapper;

import com.gaconnecte.auxilium.domain.*;
import com.gaconnecte.auxilium.service.dto.ReinsurerDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Reinsurer and its DTO ReinsurerDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ReinsurerMapper extends EntityMapper <ReinsurerDTO, Reinsurer> {
    
    
    default Reinsurer fromId(Long id) {
        if (id == null) {
            return null;
        }
        Reinsurer reinsurer = new Reinsurer();
        reinsurer.setId(id);
        return reinsurer;
    }
}
