package com.gaconnecte.auxilium.service.mapper;

import com.gaconnecte.auxilium.domain.*;

import com.gaconnecte.auxilium.service.dto.GarantieImpliqueDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity orientation and its DTO OrientationDTO.
 */
@Mapper(componentModel = "spring", uses = { PartnerMapper.class, RefModeGestionMapper.class,
		ReparateurMapper.class, })
public interface GarantieImpliqueMapper extends EntityMapper<GarantieImpliqueDTO, GarantieImplique> {
	@Mapping(source = "reparateur.id", target = "reparateurId")
	@Mapping(source = "partner.id", target = "partnerId")
	@Mapping(source = "partner.companyName", target = "partnerName")
	GarantieImpliqueDTO toDto(GarantieImplique garantieImplique);
	
	@Mapping(source = "reparateurId", target = "reparateur")
	@Mapping(source = "partnerId", target = "partner")
	GarantieImplique toEntity(GarantieImpliqueDTO garantieImpliqueDTO);

	default GarantieImplique fromId(Long id) {
		if (id == null) {
			return null;
		}
		GarantieImplique garantieImplique = new GarantieImplique();
		garantieImplique.setId(id);
		return garantieImplique;
	}
}
