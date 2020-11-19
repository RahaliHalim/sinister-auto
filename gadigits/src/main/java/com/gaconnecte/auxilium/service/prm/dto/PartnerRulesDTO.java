package com.gaconnecte.auxilium.service.prm.dto;

import java.io.Serializable;
import java.util.Date;
import java.lang.Cloneable;

public class PartnerRulesDTO implements Serializable{

    private static final long serialVersionUID = 1L;
    
    private Long id;
	private Long refPartnerId;
	private String refPartnerLabel;
	private Long refModeGestionId;
	private String refModeGestionLabel;
    private Boolean stampDuty; 
    private Boolean vetuste;
    private Boolean advanceOnInvoice;
    private Boolean insuranceDeductible;
    private Boolean hidaServiceCharges;
    private Boolean proportionalRule;
    private Boolean ceilingOverflow;
    private Boolean redemptionGuarantee;
    private Boolean shareResponsibility;
	private Long vatSubjectVatId;
    private Long stampDutySubjectVatId;
    private Long vetusteSubjectVatId;
    private Long shareResponsabilitySubjectvatId;
    private Long ceilingOverflowSubjectVatId;
    private Long proportionalRuleSubjectVatId;
    private Long vatNotSubjectVatId;
    private Long stampDutyNotSubjectVatId;
    private Long vetusteNotSubjectVatId;
    private Long shareResponsabilityNotSubjectVatId;
    private Long ceilingOverflowNotSubjectVatId;
    private Long proportionalRuleNotSubjectVatId;
	private Long advanceInvoiceId;
    private Long commitmentGaIhcVatId;
    private Long commitmentGaIhcNvatId;
    private Long commitmentGaVatInfId;
    private Long commitmentGaVatEquId;
    private Long commitmentGaNvatInfId;
    private Long commitmentGaNvatEquId;
    private Long commitmentGaTdevisInfId;
    private Long commitmentGaTdevisSupId;
    private Long commitmentGaNdevisInfId;
    private Long commitmentGaNdevisSupId;
	private Long hidaServiceLowerId;
    private Long hidaServiceBetweenId;
    private Long hidaServiceHigherId;
    private Double hidaServiceChargesLower;
    private Double hidaServiceChargesHigher;
    private Double hidaServiceChargesBetween;
    private Long guaranteeredemptionId;
    private Double advanceInvoiceHigher;
    private Long insuranceDeductibleRuleId;
    private Integer insuranceDeductiblePercentage;
    private Double insuranceDeductibleMin;
    private Double insuranceDeductibleFixed;
	private Date startDateConvention;
    private Date effectiveDate;
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getRefPartnerId() {
		return refPartnerId;
	}
	public void setRefPartnerId(Long refPartnerId) {
		this.refPartnerId = refPartnerId;
	}
	public String getRefPartnerLabel() {
		return refPartnerLabel;
	}
	public void setRefPartnerLabel(String refPartnerLabel) {
		this.refPartnerLabel = refPartnerLabel;
	}
	public Long getRefModeGestionId() {
		return refModeGestionId;
	}
	public void setRefModeGestionId(Long refModeGestionId) {
		this.refModeGestionId = refModeGestionId;
	}
	public String getRefModeGestionLabel() {
		return refModeGestionLabel;
	}
	public void setRefModeGestionLabel(String refModeGestionLabel) {
		this.refModeGestionLabel = refModeGestionLabel;
	}
	public Boolean getStampDuty() {
		return stampDuty;
	}
	public void setStampDuty(Boolean stampDuty) {
		this.stampDuty = stampDuty;
	}
	public Boolean getVetuste() {
		return vetuste;
	}
	public void setVetuste(Boolean vetuste) {
		this.vetuste = vetuste;
	}
	public Boolean getAdvanceOnInvoice() {
		return advanceOnInvoice;
	}
	public void setAdvanceOnInvoice(Boolean advanceOnInvoice) {
		this.advanceOnInvoice = advanceOnInvoice;
	}
	public Boolean getInsuranceDeductible() {
		return insuranceDeductible;
	}
	public void setInsuranceDeductible(Boolean insuranceDeductible) {
		this.insuranceDeductible = insuranceDeductible;
	}
	public Boolean getHidaServiceCharges() {
		return hidaServiceCharges;
	}
	public void setHidaServiceCharges(Boolean hidaServiceCharges) {
		this.hidaServiceCharges = hidaServiceCharges;
	}
	public Boolean getProportionalRule() {
		return proportionalRule;
	}
	public void setProportionalRule(Boolean proportionalRule) {
		this.proportionalRule = proportionalRule;
	}
	public Boolean getCeilingOverflow() {
		return ceilingOverflow;
	}
	public void setCeilingOverflow(Boolean ceilingOverflow) {
		this.ceilingOverflow = ceilingOverflow;
	}
	public Boolean getRedemptionGuarantee() {
		return redemptionGuarantee;
	}
	public void setRedemptionGuarantee(Boolean redemptionGuarantee) {
		this.redemptionGuarantee = redemptionGuarantee;
	}
	public Boolean getShareResponsibility() {
		return shareResponsibility;
	}
	public void setShareResponsibility(Boolean shareResponsibility) {
		this.shareResponsibility = shareResponsibility;
	}
	public Long getVatSubjectVatId() {
		return vatSubjectVatId;
	}
	public void setVatSubjectVatId(Long vatSubjectVatId) {
		this.vatSubjectVatId = vatSubjectVatId;
	}
	public Long getStampDutySubjectVatId() {
		return stampDutySubjectVatId;
	}
	public void setStampDutySubjectVatId(Long stampDutySubjectVatId) {
		this.stampDutySubjectVatId = stampDutySubjectVatId;
	}
	public Long getVetusteSubjectVatId() {
		return vetusteSubjectVatId;
	}
	public void setVetusteSubjectVatId(Long vetusteSubjectVatId) {
		this.vetusteSubjectVatId = vetusteSubjectVatId;
	}
	public Long getShareResponsabilitySubjectvatId() {
		return shareResponsabilitySubjectvatId;
	}
	public void setShareResponsabilitySubjectvatId(Long shareResponsabilitySubjectvatId) {
		this.shareResponsabilitySubjectvatId = shareResponsabilitySubjectvatId;
	}
	public Long getCeilingOverflowSubjectVatId() {
		return ceilingOverflowSubjectVatId;
	}
	public void setCeilingOverflowSubjectVatId(Long ceilingOverflowSubjectVatId) {
		this.ceilingOverflowSubjectVatId = ceilingOverflowSubjectVatId;
	}
	public Long getProportionalRuleSubjectVatId() {
		return proportionalRuleSubjectVatId;
	}
	public void setProportionalRuleSubjectVatId(Long proportionalRuleSubjectVatId) {
		this.proportionalRuleSubjectVatId = proportionalRuleSubjectVatId;
	}
	public Long getVatNotSubjectVatId() {
		return vatNotSubjectVatId;
	}
	public void setVatNotSubjectVatId(Long vatNotSubjectVatId) {
		this.vatNotSubjectVatId = vatNotSubjectVatId;
	}
	public Long getStampDutyNotSubjectVatId() {
		return stampDutyNotSubjectVatId;
	}
	public void setStampDutyNotSubjectVatId(Long stampDutyNotSubjectVatId) {
		this.stampDutyNotSubjectVatId = stampDutyNotSubjectVatId;
	}
	public Long getVetusteNotSubjectVatId() {
		return vetusteNotSubjectVatId;
	}
	public void setVetusteNotSubjectVatId(Long vetusteNotSubjectVatId) {
		this.vetusteNotSubjectVatId = vetusteNotSubjectVatId;
	}
	public Long getShareResponsabilityNotSubjectVatId() {
		return shareResponsabilityNotSubjectVatId;
	}
	public void setShareResponsabilityNotSubjectVatId(Long shareResponsabilityNotSubjectVatId) {
		this.shareResponsabilityNotSubjectVatId = shareResponsabilityNotSubjectVatId;
	}
	public Long getCeilingOverflowNotSubjectVatId() {
		return ceilingOverflowNotSubjectVatId;
	}
	public void setCeilingOverflowNotSubjectVatId(Long ceilingOverflowNotSubjectVatId) {
		this.ceilingOverflowNotSubjectVatId = ceilingOverflowNotSubjectVatId;
	}
	public Long getProportionalRuleNotSubjectVatId() {
		return proportionalRuleNotSubjectVatId;
	}
	public void setProportionalRuleNotSubjectVatId(Long proportionalRuleNotSubjectVatId) {
		this.proportionalRuleNotSubjectVatId = proportionalRuleNotSubjectVatId;
	}
	public Long getAdvanceInvoiceId() {
		return advanceInvoiceId;
	}
	public void setAdvanceInvoiceId(Long advanceInvoiceId) {
		this.advanceInvoiceId = advanceInvoiceId;
	}
	public Long getCommitmentGaIhcVatId() {
		return commitmentGaIhcVatId;
	}
	public void setCommitmentGaIhcVatId(Long commitmentGaIhcVatId) {
		this.commitmentGaIhcVatId = commitmentGaIhcVatId;
	}
	public Long getCommitmentGaIhcNvatId() {
		return commitmentGaIhcNvatId;
	}
	public void setCommitmentGaIhcNvatId(Long commitmentGaIhcNvatId) {
		this.commitmentGaIhcNvatId = commitmentGaIhcNvatId;
	}
	public Long getCommitmentGaVatInfId() {
		return commitmentGaVatInfId;
	}
	public void setCommitmentGaVatInfId(Long commitmentGaVatInfId) {
		this.commitmentGaVatInfId = commitmentGaVatInfId;
	}
	public Long getCommitmentGaVatEquId() {
		return commitmentGaVatEquId;
	}
	public void setCommitmentGaVatEquId(Long commitmentGaVatEquId) {
		this.commitmentGaVatEquId = commitmentGaVatEquId;
	}
	public Long getCommitmentGaNvatInfId() {
		return commitmentGaNvatInfId;
	}
	public void setCommitmentGaNvatInfId(Long commitmentGaNvatInfId) {
		this.commitmentGaNvatInfId = commitmentGaNvatInfId;
	}
	public Long getCommitmentGaNvatEquId() {
		return commitmentGaNvatEquId;
	}
	public void setCommitmentGaNvatEquId(Long commitmentGaNvatEquId) {
		this.commitmentGaNvatEquId = commitmentGaNvatEquId;
	}
	public Long getCommitmentGaTdevisInfId() {
		return commitmentGaTdevisInfId;
	}
	public void setCommitmentGaTdevisInfId(Long commitmentGaTdevisInfId) {
		this.commitmentGaTdevisInfId = commitmentGaTdevisInfId;
	}
	public Long getCommitmentGaTdevisSupId() {
		return commitmentGaTdevisSupId;
	}
	public void setCommitmentGaTdevisSupId(Long commitmentGaTdevisSupId) {
		this.commitmentGaTdevisSupId = commitmentGaTdevisSupId;
	}
	public Long getCommitmentGaNdevisInfId() {
		return commitmentGaNdevisInfId;
	}
	public void setCommitmentGaNdevisInfId(Long commitmentGaNdevisInfId) {
		this.commitmentGaNdevisInfId = commitmentGaNdevisInfId;
	}
	public Long getCommitmentGaNdevisSupId() {
		return commitmentGaNdevisSupId;
	}
	public void setCommitmentGaNdevisSupId(Long commitmentGaNdevisSupId) {
		this.commitmentGaNdevisSupId = commitmentGaNdevisSupId;
	}
	public Long getHidaServiceLowerId() {
		return hidaServiceLowerId;
	}
	public void setHidaServiceLowerId(Long hidaServiceLowerId) {
		this.hidaServiceLowerId = hidaServiceLowerId;
	}
	public Long getHidaServiceBetweenId() {
		return hidaServiceBetweenId;
	}
	public void setHidaServiceBetweenId(Long hidaServiceBetweenId) {
		this.hidaServiceBetweenId = hidaServiceBetweenId;
	}
	public Long getHidaServiceHigherId() {
		return hidaServiceHigherId;
	}
	public void setHidaServiceHigherId(Long hidaServiceHigherId) {
		this.hidaServiceHigherId = hidaServiceHigherId;
	}
	public Double getHidaServiceChargesLower() {
		return hidaServiceChargesLower;
	}
	public void setHidaServiceChargesLower(Double hidaServiceChargesLower) {
		this.hidaServiceChargesLower = hidaServiceChargesLower;
	}
	public Double getHidaServiceChargesHigher() {
		return hidaServiceChargesHigher;
	}
	public void setHidaServiceChargesHigher(Double hidaServiceChargesHigher) {
		this.hidaServiceChargesHigher = hidaServiceChargesHigher;
	}
	public Double getHidaServiceChargesBetween() {
		return hidaServiceChargesBetween;
	}
	public void setHidaServiceChargesBetween(Double hidaServiceChargesBetween) {
		this.hidaServiceChargesBetween = hidaServiceChargesBetween;
	}
	public Long getGuaranteeredemptionId() {
		return guaranteeredemptionId;
	}
	public void setGuaranteeredemptionId(Long guaranteeredemptionId) {
		this.guaranteeredemptionId = guaranteeredemptionId;
	}
	public Double getAdvanceInvoiceHigher() {
		return advanceInvoiceHigher;
	}
	public void setAdvanceInvoiceHigher(Double advanceInvoiceHigher) {
		this.advanceInvoiceHigher = advanceInvoiceHigher;
	}
	public Long getInsuranceDeductibleRuleId() {
		return insuranceDeductibleRuleId;
	}
	public void setInsuranceDeductibleRuleId(Long insuranceDeductibleRuleId) {
		this.insuranceDeductibleRuleId = insuranceDeductibleRuleId;
	}
	public Integer getInsuranceDeductiblePercentage() {
		return insuranceDeductiblePercentage;
	}
	public void setInsuranceDeductiblePercentage(Integer insuranceDeductiblePercentage) {
		this.insuranceDeductiblePercentage = insuranceDeductiblePercentage;
	}
	public Double getInsuranceDeductibleMin() {
		return insuranceDeductibleMin;
	}
	public void setInsuranceDeductibleMin(Double insuranceDeductibleMin) {
		this.insuranceDeductibleMin = insuranceDeductibleMin;
	}
	public Double getInsuranceDeductibleFixed() {
		return insuranceDeductibleFixed;
	}
	public void setInsuranceDeductibleFixed(Double insuranceDeductibleFixed) {
		this.insuranceDeductibleFixed = insuranceDeductibleFixed;
	}
	public Date getStartDateConvention() {
		return startDateConvention;
	}
	public void setStartDateConvention(Date startDateConvention) {
		this.startDateConvention = startDateConvention;
	}
	public Date getEffectiveDate() {
		return effectiveDate;
	}
	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}
 
	@Override
    public boolean equals(Object obj) {

    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    PartnerRulesDTO other = (PartnerRulesDTO) obj;
    if (id == other.id && refPartnerId == other.refPartnerId && refModeGestionId == other.refModeGestionId
	    && stampDuty == other.stampDuty && vetuste == other.vetuste && advanceOnInvoice == other.advanceOnInvoice
		&& insuranceDeductible == other.insuranceDeductible && hidaServiceCharges == other.hidaServiceCharges
		&& proportionalRule == other.proportionalRule && ceilingOverflow == other.ceilingOverflow
		&& redemptionGuarantee == other.redemptionGuarantee && shareResponsibility == other.shareResponsibility
		&& vatSubjectVatId == other.vatSubjectVatId && stampDutySubjectVatId == other.stampDutySubjectVatId
		&& vetusteSubjectVatId == other.vetusteSubjectVatId && shareResponsabilitySubjectvatId == other.shareResponsabilitySubjectvatId
		&& ceilingOverflowSubjectVatId == other.ceilingOverflowSubjectVatId && proportionalRuleSubjectVatId == other.proportionalRuleSubjectVatId
		&& vatNotSubjectVatId == other.vatNotSubjectVatId && stampDutyNotSubjectVatId == other.stampDutyNotSubjectVatId
		&& vetusteNotSubjectVatId == other.vetusteNotSubjectVatId && shareResponsabilityNotSubjectVatId == other.shareResponsabilityNotSubjectVatId
		&& ceilingOverflowNotSubjectVatId == other.ceilingOverflowNotSubjectVatId && proportionalRuleNotSubjectVatId == other.proportionalRuleNotSubjectVatId
		&& advanceInvoiceId == other.advanceInvoiceId && commitmentGaIhcVatId == other.commitmentGaIhcVatId
		&& commitmentGaIhcNvatId == other.commitmentGaIhcNvatId && commitmentGaVatInfId == other.commitmentGaVatInfId
		&& commitmentGaVatEquId == other.commitmentGaVatEquId && commitmentGaNvatInfId == other.commitmentGaNvatInfId
		&& commitmentGaNvatEquId == other.commitmentGaNvatEquId && commitmentGaTdevisInfId == other.commitmentGaTdevisInfId
		&& commitmentGaTdevisSupId == other.commitmentGaTdevisSupId && commitmentGaNdevisInfId == other.commitmentGaNdevisInfId
		&& commitmentGaNdevisSupId == other.commitmentGaNdevisSupId && hidaServiceLowerId == other.hidaServiceLowerId
		&& hidaServiceBetweenId == other.hidaServiceBetweenId && hidaServiceHigherId == other.hidaServiceHigherId
		&& hidaServiceChargesLower == other.hidaServiceChargesLower && hidaServiceChargesHigher == other.hidaServiceChargesHigher
		&& hidaServiceChargesBetween == other.hidaServiceChargesBetween && guaranteeredemptionId == other.guaranteeredemptionId
		&& advanceInvoiceHigher == other.advanceInvoiceHigher && insuranceDeductibleRuleId == other.insuranceDeductibleRuleId
		&& insuranceDeductiblePercentage == other.insuranceDeductiblePercentage && insuranceDeductibleMin == other.insuranceDeductibleMin
		&& insuranceDeductibleFixed == other.insuranceDeductibleFixed ){
      return true;
    }else{
    return false;
	}
  }     
}