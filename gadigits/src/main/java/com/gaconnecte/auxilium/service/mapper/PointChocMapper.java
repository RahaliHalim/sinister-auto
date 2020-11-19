package com.gaconnecte.auxilium.service.mapper;

import com.gaconnecte.auxilium.domain.*;
import com.gaconnecte.auxilium.service.dto.PointChocDTO;
import com.gaconnecte.auxilium.service.dto.SinisterPecDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity PointChoc and its DTO PointChocDTO.
 */
@Mapper(componentModel = "spring", uses = {SinisterPecMapper.class,})
public interface PointChocMapper extends EntityMapper <PointChocDTO, PointChoc> {
    
	
	@Mapping(source = "sinisterPec.id", target = "sinisterPecId")
    PointChocDTO toDto(PointChoc pointChoc);
    
    
    @Mapping(source = "sinisterPecId", target = "sinisterPec")

    PointChoc toEntity(PointChocDTO pointChocDTO); 
    
    default PointChoc fromId(Long id) {
        if (id == null) {
            return null;
        }
        PointChoc pointChoc = new PointChoc();
        pointChoc.setId(id);
        return pointChoc;
    }
}
