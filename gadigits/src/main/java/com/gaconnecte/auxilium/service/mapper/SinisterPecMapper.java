package com.gaconnecte.auxilium.service.mapper;

import com.gaconnecte.auxilium.domain.*;
import com.gaconnecte.auxilium.service.dto.SinisterPecDTO;
import com.gaconnecte.auxilium.service.referential.mapper.RefStepPecMapper;

import org.mapstruct.*;

/**
 * Mapper for the entity SinisterPec and its DTO SinisterPecDTO.
 */


@Mapper(componentModel = "spring", uses = {ObservationMapper.class,RefStepPecMapper.class,ReparateurMapper.class,ReasonMapper.class,UserMapper.class,PointChocMapper.class,RefBaremeMapper.class,TiersMapper.class,RefModeGestionMapper.class,
		GovernorateMapper.class, DelegationMapper.class, SinisterMapper.class,ExpertMapper.class,RaisonPecMapper.class,SinisterMapper.class, QuotationMapper.class, PrimaryQuotationMapper.class, ComplementaryQuotationMapper.class, RefPositionGaMapper.class})

public interface SinisterPecMapper extends EntityMapper <SinisterPecDTO, SinisterPec> {

    @Mapping(source = "governorate.id", target = "governorateId")
    @Mapping(source = "governorate.label", target = "governorateLabel")
    @Mapping(source = "governorateRep.id", target = "gouvernoratRepId")
    @Mapping(source = "governorateRep.label", target = "governorateRepLabel")
    @Mapping(source = "bareme.id", target = "baremeId")
    @Mapping(source = "mode.id", target = "modeId")
    @Mapping(source = "bareme.code", target = "codeBareme")
    @Mapping(source = "bareme.responsabiliteX", target = "responsabiliteX")
    @Mapping(source = "delegation.id", target = "delegationId")
    @Mapping(source = "delegationRep.id", target = "villeRepId")
    @Mapping(source = "sinister.id", target = "sinisterId")
    @Mapping(source = "sinister.partner.companyName", target = "companyName")
    @Mapping(source = "sinister.contract.client.id", target = "partnerId")
    @Mapping(source = "sinister.contract.agence.id", target = "agencyId")
    @Mapping(source = "sinister.creationUser.firstName", target = "agentName")
    @Mapping(source = "sinister.contract.numeroContrat", target = "contractNumber")
    //@Mapping(source = "sinister.contract.isAssujettieTva", target = "isAssujettieTva")
    @Mapping(source = "sinister.vehicle.immatriculationVehicule", target = "immatriculationVehicle")
    @Mapping(source = "sinister.incidentDate", target = "incidentDate")
    @Mapping(source = "sinister.phone", target = "phone")
    @Mapping(source = "sinister.insured.prenom", target = "insuredSurName")
    @Mapping(source = "sinister.insured.nom", target = "insuredName")
    @Mapping(source = "sinister.insured.isAssujettieTva", target = "isAssujettieTva")
    @Mapping(source = "sinister.vehicle.id", target = "vehiculeId")
    @Mapping(source = "sinister.vehicle.dcCapital", target = "insuredCapital")
    @Mapping(source = "sinister.vehicle.dcCapitalFranchise", target = "dcCapitalFranchise")
    @Mapping(source = "sinister.vehicle.franchiseTypeDcCapital", target = "franchiseTypeDcCapital")
    @Mapping(source = "sinister.vehicle.newValueVehicle", target = "valeurNeuf")
    @Mapping(source = "sinister.vehicle.newValueVehicleFarnchise", target = "newValueVehicleFarnchise")
    @Mapping(source = "sinister.vehicle.franchiseTypeNewValue", target = "franchiseTypeNewValue")
    @Mapping(source = "sinister.vehicle.bgCapital", target = "bgCapital")
    @Mapping(source = "sinister.vehicle.bgCapitalFranchise", target = "bgCapitalFranchise")
    @Mapping(source = "sinister.vehicle.franchiseTypeBgCapital", target = "franchiseTypeBgCapital")
    @Mapping(source = "sinister.vehicle.marketValue", target = "marketValue")
    @Mapping(source = "sinister.vehicle.marketValueFranchise", target = "marketValueFranchise")
    @Mapping(source = "sinister.vehicle.franchiseTypeMarketValue", target = "franchiseTypeMarketValue")
    @Mapping(source = "mode.libelle", target = "labelModeGestion")
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "step.id", target = "stepId")
    @Mapping(source = "step.label", target = "stepLabel")
    @Mapping(source = "assignedTo.id", target = "assignedToId")
    @Mapping(source = "assignedTo.firstName", target = "assignedTofirstName")
    @Mapping(source = "assignedTo.lastName", target = "assignedTolastName")
    @Mapping(source = "reasonDecision.id", target = "motifsDecisionId")
    @Mapping(source = "reasonDecision.label", target = "motifsDecisionLabel")
    @Mapping(source = "expert.id", target = "expertId")
    @Mapping(source = "expert.raisonSociale", target = "expertName")
    @Mapping(source = "sinister.reference", target = "reference")
    @Mapping(source = "sinister.reference", target = "referenceSinister")
    @Mapping(source = "reasonCancelExpert.id", target = "reasonCancelExpertId")
    @Mapping(source = "sinister.contract.agence.name", target = "agenceName")
    @Mapping(source = "reparateur.id", target = "reparateurId")
    @Mapping(source = "reparateur.raisonSociale", target = "reparateurName")
    @Mapping(source = "modeModif.id", target = "modeModifId")
    @Mapping(source = "reasonReopened.id", target = "motifsReopenedId")
    @Mapping(source = "reasonReopened.label", target = "motifsReopenedLabel")
    @Mapping(source = "reasonCancelAffectedRep.id", target = "reasonCancelAffectedRepId")
    @Mapping(source = "primaryQuotation.id", target = "primaryQuotationId")
    @Mapping(source = "primaryQuotation.conditionVehicle", target = "conditionVehicle")
    @Mapping(source = "primaryQuotation.repairableVehicle", target = "repairableVehicle")
    @Mapping(source = "primaryQuotation.preliminaryReport", target = "preliminaryReport")
    @Mapping(source = "primaryQuotation.concordanceReport", target = "concordanceReport")
    @Mapping(source = "primaryQuotation.immobilizedVehicle", target = "immobilizedVehicle")
    
    
    @Mapping(source = "posGa.id", target = "posGaId")
    @Mapping(source = "posGa.libelle", target = "labelPosGa")
    @Mapping(source = "reasonCanceled.id", target = "reasonCanceledId")
    @Mapping(source = "reasonRefused.id", target = "reasonRefusedId")
    @Mapping(source = "reasonCanceled.label", target = "reasonCanceledLabel")
    @Mapping(source = "reasonRefused.label", target = "reasonRefusedLabel")


    SinisterPecDTO toDto(SinisterPec sinisterPec); 

    @Mapping(source = "governorateId", target = "governorate")
    @Mapping(source = "gouvernoratRepId", target = "governorateRep")
    @Mapping(source = "delegationId", target = "delegation")
    @Mapping(source = "villeRepId", target = "delegationRep")
    @Mapping(source = "sinisterId", target = "sinister")
    @Mapping(source = "modeId", target = "mode")
    @Mapping(source = "stepId", target = "step")
    @Mapping(source = "baremeId", target = "bareme")
    @Mapping(source = "userId", target = "user")
    @Mapping(source = "assignedToId", target = "assignedTo")
    @Mapping(source = "motifsDecisionId", target = "reasonDecision")
    @Mapping(source = "expertId", target = "expert")
    @Mapping(source = "reasonCancelExpertId", target = "reasonCancelExpert")
    @Mapping(source = "reparateurId", target = "reparateur")
    @Mapping(source = "motifsReopenedId", target = "reasonReopened")
    @Mapping(source = "reasonCancelAffectedRepId", target = "reasonCancelAffectedRep")
    @Mapping(source = "posGaId", target = "posGa")
    @Mapping(source = "modeModifId", target = "modeModif")
    @Mapping(source = "reasonCanceledId", target = "reasonCanceled")
    @Mapping(source = "reasonRefusedId", target = "reasonRefused")


    SinisterPec toEntity(SinisterPecDTO sinisterPecDTO); 
    default SinisterPec fromId(Long id) {
        if (id == null) {
            return null;
        }
        SinisterPec sinisterPec = new SinisterPec();
        sinisterPec.setId(id);
        return sinisterPec;
    }
}
