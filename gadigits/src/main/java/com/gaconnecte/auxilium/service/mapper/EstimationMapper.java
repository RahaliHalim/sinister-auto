package com.gaconnecte.auxilium.service.mapper;

import com.gaconnecte.auxilium.domain.*;
import com.gaconnecte.auxilium.service.dto.EstimationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Tarif and its DTO TarifDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface EstimationMapper extends EntityMapper <EstimationDTO, Estimation> {
    
    
    default Estimation fromId(Long id) {
        if (id == null) {
            return null;
        }
        Estimation estimation = new Estimation();
        estimation.setId(id);
        return estimation;
    }
}
