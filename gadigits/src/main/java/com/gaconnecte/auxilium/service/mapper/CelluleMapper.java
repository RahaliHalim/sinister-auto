package com.gaconnecte.auxilium.service.mapper;

import com.gaconnecte.auxilium.domain.*;
import com.gaconnecte.auxilium.service.dto.CelluleDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Cellule and its DTO CelluleDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CelluleMapper extends EntityMapper <CelluleDTO, Cellule> {
    
    @Mapping(target = "liens", ignore = true)
    Cellule toEntity(CelluleDTO celluleDTO); 
    default Cellule fromId(Long id) {
        if (id == null) {
            return null;
        }
        Cellule cellule = new Cellule();
        cellule.setId(id);
        return cellule;
    }
}