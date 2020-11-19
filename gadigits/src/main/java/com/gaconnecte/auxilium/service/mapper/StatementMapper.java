package com.gaconnecte.auxilium.service.mapper;

import com.gaconnecte.auxilium.domain.*;
import com.gaconnecte.auxilium.service.dto.StatementDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Statement and its DTO StatementDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface StatementMapper extends EntityMapper <StatementDTO, Statement> {
    
    
    default Statement fromId(Long id) {
        if (id == null) {
            return null;
        }
        Statement statement = new Statement();
        statement.setId(id);
        return statement;
    }
}
