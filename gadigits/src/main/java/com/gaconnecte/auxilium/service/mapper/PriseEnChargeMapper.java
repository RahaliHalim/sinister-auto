package com.gaconnecte.auxilium.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.gaconnecte.auxilium.domain.PriseEnCharge;
import com.gaconnecte.auxilium.service.dto.PriseEnChargeDTO;


@Mapper(componentModel = "spring", uses = {})
public interface PriseEnChargeMapper extends EntityMapper<PriseEnChargeDTO,PriseEnCharge>{
	
	PriseEnChargeDTO toDto(PriseEnCharge priseEnCharge); 
	PriseEnCharge toEntity(PriseEnChargeDTO priseEnChargeDTO); 
    default PriseEnCharge fromId(Long id) {
        if (id == null) {
            return null;
        }
        PriseEnCharge priseEnCharge = new PriseEnCharge();
        priseEnCharge.setId(id);
        return priseEnCharge;
    }
}