package com.gaconnecte.auxilium.service.mapper;

import com.gaconnecte.auxilium.domain.*;
import com.gaconnecte.auxilium.service.dto.RefFractionnementDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity RefFractionnement and its DTO RefFractionnementDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface RefFractionnementMapper extends EntityMapper <RefFractionnementDTO, RefFractionnement> {
    
    
    default RefFractionnement fromId(Long id) {
        if (id == null) {
            return null;
        }
        RefFractionnement refFractionnement = new RefFractionnement();
        refFractionnement.setId(id);
        return refFractionnement;
    }
}
