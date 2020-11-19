package com.gaconnecte.auxilium.service.referential.mapper;

import com.gaconnecte.auxilium.domain.referential.RefHolidays;
import com.gaconnecte.auxilium.service.mapper.EntityMapper;
import com.gaconnecte.auxilium.service.referential.dto.RefHolidaysDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity RefHolidays and its DTO RefHolidaysDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface RefHolidaysMapper extends EntityMapper <RefHolidaysDTO, RefHolidays> {
    
    
    default RefHolidays fromId(Long id) {
        if (id == null) {
            return null;
        }
        RefHolidays refHolidays = new RefHolidays();
        refHolidays.setId(id);
        return refHolidays;
    }
}
