package com.gaconnecte.auxilium.service.mapper;
import com.gaconnecte.auxilium.domain.*;
import com.gaconnecte.auxilium.service.dto.RefTarifDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity RefTarif and its DTO RefTarifDTO.
 */

@Mapper(componentModel = "spring", uses = {TarifLineMapper.class})
public interface RefTarifMapper extends EntityMapper<RefTarifDTO, RefTarif> {
    
	@Mapping(source = "line.id", target = "lineId")
	@Mapping(source = "line.libelle", target = "libelleTarif")
	RefTarifDTO toDto(RefTarif refTarif); 
	
	@Mapping(source = "lineId", target = "line")
	RefTarif toEntity(RefTarifDTO refTarifDTO); 
   
	 
	 default RefTarif fromId(Long id) {
        if (id == null) {
            return null;
        }
        RefTarif refTarif = new RefTarif();
        refTarif.setId(id);
        return refTarif;
    }
}
