package com.gaconnecte.auxilium.service.mapper;

import com.gaconnecte.auxilium.domain.*;
import com.gaconnecte.auxilium.service.dto.PecStatusChangeMatrixDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity PecStatusChangeMatrix and its DTO PecStatusChangeMatrixDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PecStatusChangeMatrixMapper extends EntityMapper <PecStatusChangeMatrixDTO, PecStatusChangeMatrix> {
    
    
    default PecStatusChangeMatrix fromId(Long id) {
        if (id == null) {
            return null;
        }
        PecStatusChangeMatrix pecStatusChangeMatrix = new PecStatusChangeMatrix();
        pecStatusChangeMatrix.setId(id);
        return pecStatusChangeMatrix;
    }
}
