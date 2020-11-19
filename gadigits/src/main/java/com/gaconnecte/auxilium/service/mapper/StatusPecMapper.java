package com.gaconnecte.auxilium.service.mapper;

import com.gaconnecte.auxilium.domain.*;
import com.gaconnecte.auxilium.service.dto.StatusPecDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity StatusPec and its DTO StatusPecDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface StatusPecMapper extends EntityMapper <StatusPecDTO, StatusPec> {
    
    
    default StatusPec fromId(Long id) {
        if (id == null) {
            return null;
        }
        StatusPec statusPec = new StatusPec();
        statusPec.setId(id);
        return statusPec;
    }
}
