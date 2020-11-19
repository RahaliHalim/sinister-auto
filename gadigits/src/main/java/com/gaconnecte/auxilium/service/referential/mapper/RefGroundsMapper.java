package com.gaconnecte.auxilium.service.referential.mapper;

import com.gaconnecte.auxilium.domain.referential.RefGrounds;
import com.gaconnecte.auxilium.service.mapper.EntityMapper;
import com.gaconnecte.auxilium.service.referential.dto.RefGroundsDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity RefGrounds and its DTO RefGroundsDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface RefGroundsMapper extends EntityMapper <RefGroundsDTO, RefGrounds> {
    
    default RefGrounds fromId(Long id) {
        if (id == null) {
            return null;
        }
        RefGrounds refGrounds = new RefGrounds();
        refGrounds.setId(id);
        return refGrounds;
    }
}
