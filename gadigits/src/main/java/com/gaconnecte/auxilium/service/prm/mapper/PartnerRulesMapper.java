package com.gaconnecte.auxilium.service.prm.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.gaconnecte.auxilium.domain.prm.PartnerRules;
import com.gaconnecte.auxilium.service.mapper.EntityMapper;
import com.gaconnecte.auxilium.service.mapper.PartnerMapper;
import com.gaconnecte.auxilium.service.mapper.RefModeGestionMapper;
import com.gaconnecte.auxilium.service.prm.dto.PartnerRulesDTO;

@Mapper(componentModel = "spring", uses = {PartnerMapper.class, RefModeGestionMapper.class, RulesMapper.class})
public interface PartnerRulesMapper extends EntityMapper <PartnerRulesDTO, PartnerRules>{

    @Mapping(source = "partner.id", target = "refPartnerId")
    @Mapping(source = "partner.companyName", target = "refPartnerLabel")
    @Mapping(source = "refModeGestion.id", target = "refModeGestionId")
    @Mapping(source = "refModeGestion.libelle", target = "refModeGestionLabel")
    @Mapping(source = "vatSubjectVat.id", target = "vatSubjectVatId")
    @Mapping(source = "stampDutySubjectVat.id", target = "stampDutySubjectVatId")
    @Mapping(source = "vetusteSubjectVat.id", target = "vetusteSubjectVatId")
    @Mapping(source = "shareResponsabilitySubjectvat.id", target = "shareResponsabilitySubjectvatId")
    @Mapping(source = "ceilingOverflowSubjectVat.id", target = "ceilingOverflowSubjectVatId")
    @Mapping(source = "proportionalRuleSubjectVat.id", target = "proportionalRuleSubjectVatId")
    @Mapping(source = "vatNotSubjectVat.id", target = "vatNotSubjectVatId")
    @Mapping(source = "stampDutyNotSubjectVat.id", target = "stampDutyNotSubjectVatId")
    @Mapping(source = "vetusteNotSubjectVat.id", target = "vetusteNotSubjectVatId")
    @Mapping(source = "shareResponsabilityNotSubjectVat.id", target = "shareResponsabilityNotSubjectVatId")
    @Mapping(source = "ceilingOverflowNotSubjectVat.id", target = "ceilingOverflowNotSubjectVatId")
    @Mapping(source = "proportionalRuleNotSubjectVat.id", target = "proportionalRuleNotSubjectVatId")
    @Mapping(source = "advanceInvoice.id", target = "advanceInvoiceId")
    @Mapping(source = "commitmentGaIhcVat.id", target = "commitmentGaIhcVatId")
    @Mapping(source = "commitmentGaIhcNvat.id", target = "commitmentGaIhcNvatId")
    @Mapping(source = "commitmentGaVatInf.id", target = "commitmentGaVatInfId")
    @Mapping(source = "commitmentGaVatEqu.id", target = "commitmentGaVatEquId")
    @Mapping(source = "commitmentGaNvatInf.id", target = "commitmentGaNvatInfId")
    @Mapping(source = "commitmentGaNvatEqu.id", target = "commitmentGaNvatEquId")
    @Mapping(source = "commitmentGaTdevisInf.id", target = "commitmentGaTdevisInfId")
    @Mapping(source = "commitmentGaTdevisSup.id", target = "commitmentGaTdevisSupId")
    @Mapping(source = "commitmentGaNdevisInf.id", target = "commitmentGaNdevisInfId")
    @Mapping(source = "commitmentGaNdevisSup.id", target = "commitmentGaNdevisSupId")
    @Mapping(source = "hidaServiceLower.id", target = "hidaServiceLowerId")
    @Mapping(source = "hidaServiceBetween.id", target = "hidaServiceBetweenId")
    @Mapping(source = "hidaServiceHigher.id", target = "hidaServiceHigherId")
    @Mapping(source = "guaranteeredemption.id", target = "guaranteeredemptionId")
    @Mapping(source = "insuranceDeductibleRule.id", target = "insuranceDeductibleRuleId")

    PartnerRulesDTO toDto(PartnerRules partnerRules);
    
    @Mapping(source = "refPartnerId", target = "partner")
    @Mapping(source = "refModeGestionId", target = "refModeGestion")
    @Mapping(source = "vatSubjectVatId", target = "vatSubjectVat")
    @Mapping(source = "stampDutySubjectVatId", target = "stampDutySubjectVat")
    @Mapping(source = "vetusteSubjectVatId", target = "vetusteSubjectVat")
    @Mapping(source = "shareResponsabilitySubjectvatId", target = "shareResponsabilitySubjectvat")
    @Mapping(source = "ceilingOverflowSubjectVatId", target = "ceilingOverflowSubjectVat")
    @Mapping(source = "proportionalRuleSubjectVatId", target = "proportionalRuleSubjectVat")
    @Mapping(source = "vatNotSubjectVatId", target = "vatNotSubjectVat")
    @Mapping(source = "stampDutyNotSubjectVatId", target = "stampDutyNotSubjectVat")
    @Mapping(source = "vetusteNotSubjectVatId", target = "vetusteNotSubjectVat")
    @Mapping(source = "shareResponsabilityNotSubjectVatId", target = "shareResponsabilityNotSubjectVat")
    @Mapping(source = "ceilingOverflowNotSubjectVatId", target = "ceilingOverflowNotSubjectVat")
    @Mapping(source = "proportionalRuleNotSubjectVatId", target = "proportionalRuleNotSubjectVat")
    @Mapping(source = "advanceInvoiceId", target = "advanceInvoice")
    @Mapping(source = "commitmentGaIhcVatId", target = "commitmentGaIhcVat")
    @Mapping(source = "commitmentGaIhcNvatId", target = "commitmentGaIhcNvat")
    @Mapping(source = "commitmentGaVatInfId", target = "commitmentGaVatInf")
    @Mapping(source = "commitmentGaVatEquId", target = "commitmentGaVatEqu")
    @Mapping(source = "commitmentGaNvatInfId", target = "commitmentGaNvatInf")
    @Mapping(source = "commitmentGaNvatEquId", target = "commitmentGaNvatEqu")
    @Mapping(source = "commitmentGaTdevisInfId", target = "commitmentGaTdevisInf")
    @Mapping(source = "commitmentGaTdevisSupId", target = "commitmentGaTdevisSup")
    @Mapping(source = "commitmentGaNdevisInfId", target = "commitmentGaNdevisInf")
    @Mapping(source = "commitmentGaNdevisSupId", target = "commitmentGaNdevisSup")
    @Mapping(source = "guaranteeredemptionId", target = "guaranteeredemption")
    @Mapping(source = "hidaServiceLowerId", target = "hidaServiceLower")
    @Mapping(source = "hidaServiceBetweenId", target = "hidaServiceBetween")
    @Mapping(source = "hidaServiceHigherId", target = "hidaServiceHigher")
    @Mapping(source = "insuranceDeductibleRuleId", target = "insuranceDeductibleRule")

    PartnerRules toEntity(PartnerRulesDTO partnerRulesDTO);

    default PartnerRules fromId(Long id) {
        if (id == null) {
            return null;
        }
        PartnerRules partnerRules = new PartnerRules();
        partnerRules.setId(id);
        return partnerRules;
    }
}
