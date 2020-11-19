package com.gaconnecte.auxilium.service.mapper;

import com.gaconnecte.auxilium.domain.*;

import com.gaconnecte.auxilium.service.dto.VisAVisDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Contact and its DTO ContactDTO.
 */
@Mapper(componentModel = "spring", uses = {  RefRemorqueurMapper.class,ReparateurMapper.class,PartnerMapper.class, LoueurMapper.class })
public interface VisAVisMapper extends EntityMapper<VisAVisDTO, VisAVis> {
	@Mapping(source = "reparateur.id", target = "reparateurId")
	@Mapping(source = "remorqueur.id", target = "remorqueurId")
	@Mapping(source = "loueur.id", target = "loueurId")

	VisAVisDTO toDto(VisAVis visAVis);
	@Mapping(source = "remorqueurId", target = "remorqueur.id")
	@Mapping(source = "reparateurId", target = "reparateur.id")
	@Mapping(source = "loueurId", target = "loueur.id")

	VisAVis toEntity(VisAVisDTO visAVisDTO);

	default VisAVis fromId(Long id) {
		if (id == null) {
			return null;
		}
		VisAVis visAVis = new VisAVis();
		visAVis.setId(id);
		return visAVis;
	}
}
