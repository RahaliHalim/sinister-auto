package com.gaconnecte.auxilium.service.mapper;

import com.gaconnecte.auxilium.domain.*;
import com.gaconnecte.auxilium.service.dto.ConcessionnaireDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Concessionnaire and its DTO ConcessionnaireDTO.
 */
@Mapper(componentModel = "spring", uses = {VehicleBrandMapper.class, })
public interface ConcessionnaireMapper extends EntityMapper <ConcessionnaireDTO, Concessionnaire> {
    
    
    default Concessionnaire fromId(Long id) {
        if (id == null) {
            return null;
        }
        Concessionnaire concessionnaire = new Concessionnaire();
        concessionnaire.setId(id);
        return concessionnaire;
    }
}
