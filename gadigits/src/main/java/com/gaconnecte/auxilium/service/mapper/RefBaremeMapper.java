package com.gaconnecte.auxilium.service.mapper;

import com.gaconnecte.auxilium.domain.*;
import com.gaconnecte.auxilium.service.dto.RefBaremeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity RefBareme and its DTO RefBaremeDTO.
 */
@Mapper(componentModel = "spring", uses = {AttachmentMapper.class})
public interface RefBaremeMapper extends EntityMapper <RefBaremeDTO, RefBareme> {
    
    RefBaremeDTO toDto(RefBareme refBareme); 

    RefBareme toEntity(RefBaremeDTO refBaremeDTO); 
    default RefBareme fromId(Long id) {
        if (id == null) {
            return null;
        }
        RefBareme refBareme = new RefBareme();
        refBareme.setId(id);
        return refBareme;
    }
}
