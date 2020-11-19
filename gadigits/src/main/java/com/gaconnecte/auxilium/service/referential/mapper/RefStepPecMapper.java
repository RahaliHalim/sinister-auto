package com.gaconnecte.auxilium.service.referential.mapper;

import com.gaconnecte.auxilium.domain.RefStepPec;
import com.gaconnecte.auxilium.service.mapper.EntityMapper;
import com.gaconnecte.auxilium.service.referential.dto.RefStepPecDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity RefStatusSinister and its DTO RefStatusSinisterDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface RefStepPecMapper extends EntityMapper <RefStepPecDTO, RefStepPec> {
    default RefStepPec fromId(Long id) {
        if (id == null) {
            return null;
        }
        RefStepPec refStatusSinister = new RefStepPec();
        refStatusSinister.setId(id);
        return refStatusSinister;
    }
}
