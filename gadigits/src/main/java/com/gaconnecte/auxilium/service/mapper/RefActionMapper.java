package com.gaconnecte.auxilium.service.mapper;

import org.mapstruct.Mapper;
import com.gaconnecte.auxilium.domain.RefAction;
import com.gaconnecte.auxilium.service.dto.RefActionDTO;

/**
 * Mapper for the entity Operation and its DTO OperationDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface RefActionMapper extends EntityMapper <RefActionDTO, RefAction> {
    
    
    default RefAction fromId(Long id) {
        if (id == null) {
            return null;
        }
        RefAction Action = new RefAction();
        Action.setId(id);
        return Action;
    }
}
