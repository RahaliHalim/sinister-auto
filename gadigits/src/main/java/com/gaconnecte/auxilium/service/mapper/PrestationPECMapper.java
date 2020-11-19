package com.gaconnecte.auxilium.service.mapper;

import com.gaconnecte.auxilium.domain.*;
import com.gaconnecte.auxilium.service.dto.PrestationPECDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity PrestationPEC and its DTO PrestationPECDTO.
 */
@Mapper(componentModel = "spring", uses = {PrestationMapper.class, UserMapper.class, PointChocMapper.class, RefModeGestionMapper.class, RefBaremeMapper.class, RefPositionGaMapper.class, AgentGeneralMapper.class, ReparateurMapper.class , ExpertMapper.class, TiersMapper.class, DossierMapper.class, PrimaryQuotationMapper.class})
public interface PrestationPECMapper extends EntityMapper <PrestationPECDTO, PrestationPEC> {

	//@Mapping(source = "prestation.id", target = "prestationId")
	@Mapping(source = "dossier.id", target = "dossierId")
	@Mapping(source = "dossier.reference", target = "dossierReference")
	@Mapping(source = "pointChoc.id", target = "pointChocId")

	@Mapping(source = "user.id", target = "userId")
	@Mapping(source = "user.login", target = "userLogin")
	@Mapping(source = "user.firstName", target = "userFirstName")
	@Mapping(source = "user.lastName", target = "userLastName")

	@Mapping(source = "mode.id", target = "modeId")
	@Mapping(source = "mode.libelle", target = "modeLibelle")

	@Mapping(source = "reparateur.id", target = "reparateurId")
	@Mapping(source = "expert.id", target = "expertId")

	@Mapping(source = "bareme.id", target = "baremeId")
	@Mapping(source = "bareme.code", target = "baremeCode")

	@Mapping(source = "posGa.id", target = "posGaId")
	@Mapping(source = "posGa.libelle", target = "posGaLibelle")
	
	@Mapping(source = "primaryQuotation.id", target = "primaryQuotationId")
	//@Mapping(target = "tiers", ignore = true)
	PrestationPECDTO toDto(PrestationPEC prestationPEC); 

	//@Mapping(source = "tiersId", target = "tiers")
	//@Mapping(target = "tiers", ignore = true)

	@Mapping(source = "dossierId", target = "dossier")
	//@Mapping(source = "prestationId", target = "prestation")
	@Mapping(source = "pointChocId", target = "pointChoc")

	@Mapping(source = "modeId", target = "mode")

	@Mapping(source = "userId", target = "user")

	@Mapping(source = "reparateurId", target = "reparateur")
	@Mapping(source = "expertId", target = "expert")


	@Mapping(source = "baremeId", target = "bareme")

	@Mapping(source = "posGaId", target = "posGa")
	@Mapping(source = "primaryQuotationId", target = "primaryQuotation")
	PrestationPEC toEntity(PrestationPECDTO prestationPECDTO); 
	default PrestationPEC fromId(Long id) {
		if (id == null) {
			return null;
		}
		PrestationPEC prestationPEC = new PrestationPEC();
		prestationPEC.setId(id);
		return prestationPEC;
	}
}
