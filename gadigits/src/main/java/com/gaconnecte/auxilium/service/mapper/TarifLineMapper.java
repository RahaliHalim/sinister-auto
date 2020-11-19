package com.gaconnecte.auxilium.service.mapper;

import com.gaconnecte.auxilium.domain.*;
import com.gaconnecte.auxilium.service.dto.TarifLineDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity TarifLine and its DTO TarifDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TarifLineMapper extends EntityMapper <TarifLineDTO, TarifLine> {
    
    
    default TarifLine fromId(Long id) {
        if (id == null) {
            return null;
        }
        TarifLine tarifLine = new TarifLine();
        tarifLine.setId(id);
        return tarifLine;
    }
}
