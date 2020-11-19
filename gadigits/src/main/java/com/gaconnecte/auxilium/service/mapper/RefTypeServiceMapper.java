package com.gaconnecte.auxilium.service.mapper;

import com.gaconnecte.auxilium.domain.*;
import com.gaconnecte.auxilium.service.dto.RefTypeServiceDTO;
import com.gaconnecte.auxilium.service.dto.RefTypeServicePackDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity RefTypeService and its DTO RefTypeServiceDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface RefTypeServiceMapper extends EntityMapper <RefTypeServiceDTO, RefTypeService> {

	RefTypeServiceDTO toDto(RefTypeService RefTypeService);
    
	
	RefTypeService toEntity(RefTypeServiceDTO refTypeServiceDTO); 
    
    
    
    
    
    
    
    default RefTypeService fromId(Long id) {
        if (id == null) {
            return null;
        }
        RefTypeService refTypeService = new RefTypeService();
        refTypeService.setId(id);
        return refTypeService;
    }
}
