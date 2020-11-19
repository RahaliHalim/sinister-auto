package com.gaconnecte.auxilium.service.mapper;

import org.mapstruct.Mapper;

import com.gaconnecte.auxilium.domain.Assitances;
import com.gaconnecte.auxilium.service.dto.AssitancesDTO;


@Mapper(componentModel = "spring", uses = {})
public interface AssitancesMapper extends EntityMapper <AssitancesDTO, Assitances> {

	AssitancesDTO toDto(Assitances assitances); 
	Assitances toEntity(AssitancesDTO assitancesDTO); 
    default Assitances fromId(Long id) {
        if (id == null) {
            return null;
        }
        Assitances Assitances = new Assitances();
        Assitances.setId(id);
        return Assitances;
    }
}
