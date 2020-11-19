package com.gaconnecte.auxilium.service.mapper;

import com.gaconnecte.auxilium.domain.*;
import com.gaconnecte.auxilium.service.dto.ElementMenuDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ElementMenu and its DTO ElementMenuDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ElementMenuMapper extends EntityMapper <ElementMenuDTO, ElementMenu> {
    
    
    default ElementMenu fromId(Long id) {
        if (id == null) {
            return null;
        }
        ElementMenu elementMenu = new ElementMenu();
        elementMenu.setId(id);
        return elementMenu;
    }
}
