package com.gaconnecte.auxilium.service.mapper;

import org.mapstruct.Mapper;

import com.gaconnecte.auxilium.domain.ObservationApec;
import com.gaconnecte.auxilium.service.dto.ObservationApecDTO;


@Mapper(componentModel = "spring", uses = {})
public interface ObservationApecMapper extends EntityMapper <ObservationApecDTO, ObservationApec> {
	
	ObservationApecDTO toDto(ObservationApec observationApec); 

	ObservationApec toEntity(ObservationApecDTO observationApecDTO); 
    default ObservationApec fromId(Long id) {
        if (id == null) {
            return null;
        }
        ObservationApec observationApec = new ObservationApec();
        observationApec.setId(id);
        return observationApec;
    }

}
