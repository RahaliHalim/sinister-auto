package com.gaconnecte.auxilium.service.mapper;

import com.gaconnecte.auxilium.domain.*;

import com.gaconnecte.auxilium.service.dto.ExpertGarantieImpliqueDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity orientation and its DTO OrientationDTO.
 */
@Mapper(componentModel = "spring", uses = { PartnerMapper.class, RefModeGestionMapper.class,
		ExpertMapper.class, })
public interface ExpertGarantieImpliqueMapper extends EntityMapper<ExpertGarantieImpliqueDTO, ExpertGarantieImplique> {
	@Mapping(source = "expert.id", target = "expertId")
	@Mapping(source = "partner.id", target = "partnerId")
	@Mapping(source = "partner.companyName", target = "partnerName")
	ExpertGarantieImpliqueDTO toDto(ExpertGarantieImplique expertGarantieImplique);
	@Mapping(source = "expertId", target = "expert")
	@Mapping(source = "partnerId", target = "partner")
	ExpertGarantieImplique toEntity(ExpertGarantieImpliqueDTO expertGarantieImpliqueDTO);
	

	default ExpertGarantieImplique fromId(Long id) {
		if (id == null) {
			return null;
		}
		ExpertGarantieImplique expertGarantieImplique = new ExpertGarantieImplique();
		expertGarantieImplique.setId(id);
		return expertGarantieImplique;
	}
}
