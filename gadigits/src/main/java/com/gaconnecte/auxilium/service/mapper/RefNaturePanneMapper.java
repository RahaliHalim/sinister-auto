package com.gaconnecte.auxilium.service.mapper;

import org.mapstruct.Mapper;

import com.gaconnecte.auxilium.domain.RefNaturePanne;
import com.gaconnecte.auxilium.service.dto.RefNaturePanneDTO;

@Mapper(componentModel = "spring", uses = {})
public interface RefNaturePanneMapper extends EntityMapper <RefNaturePanneDTO, RefNaturePanne> {
	
	RefNaturePanneDTO toDto(RefNaturePanne refNaturePanne);
    
	
	RefNaturePanne toEntity(RefNaturePanneDTO refNaturePanneDTO); 
    
    
    
    
    
    
    
    default RefNaturePanne fromId(Long id) {
        if (id == null) {
            return null;
        }
        RefNaturePanne refNaturePanne = new RefNaturePanne();
        refNaturePanne.setId(id);
        return refNaturePanne;
    }

}
