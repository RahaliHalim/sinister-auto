package com.gaconnecte.auxilium.domain.prm;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.util.Date;

import com.gaconnecte.auxilium.domain.RefModeGestion;
import com.gaconnecte.auxilium.domain.Partner;

@Entity
@Table(name = "prm_partner_rules",uniqueConstraints={
	    @UniqueConstraint(columnNames = {"partner_id", "mode_id"})})
public class PartnerRules implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "partner_id")
    private Partner partner;
    @ManyToOne
    @JoinColumn(name = "mode_id")
    private RefModeGestion refModeGestion;
    @Column(name = "stamp_duty")
    private Boolean stampDuty;
    @Column(name = "vetuste")
    private Boolean vetuste;
    @Column(name = "advance_on_invoice")
    private Boolean advanceOnInvoice;
    @Column(name = "insurance_deductible")
    private Boolean insuranceDeductible;
    @Column(name = "hida_service_charges")
    private Boolean hidaServiceCharges;
    @Column(name = "proportional_rule")
    private Boolean proportionalRule;
    @Column(name = "ceiling_overflow")
    private Boolean ceilingOverflow;
    @Column(name = "redemption_guarantee")
    private Boolean redemptionGuarantee;
    @Column(name = "share_responsibility")
    private Boolean shareResponsibility;
	@ManyToOne
    @JoinColumn(name = "vat_subject_vat")
    private Rules vatSubjectVat;
    @ManyToOne
    @JoinColumn(name = "stamp_duty_subject_vat")
    private Rules stampDutySubjectVat;
    @ManyToOne
    @JoinColumn(name = "vetuste_subject_vat")
    private Rules vetusteSubjectVat;
    @ManyToOne
    @JoinColumn(name = "share_responsability_subject_vat")
    private Rules shareResponsabilitySubjectvat;
    @ManyToOne
    @JoinColumn(name = "ceiling_overflow_subject_vat")
    private Rules ceilingOverflowSubjectVat;
    @ManyToOne
    @JoinColumn(name = "proportional_rule_subject_vat")
    private Rules proportionalRuleSubjectVat;
    @ManyToOne
    @JoinColumn(name = "vat_not_subject_vat")
    private Rules vatNotSubjectVat;
    @ManyToOne
    @JoinColumn(name = "stamp_duty_not_subject_vat")
    private Rules stampDutyNotSubjectVat;
    @ManyToOne
    @JoinColumn(name = "vetuste_not_subject_vat")
    private Rules vetusteNotSubjectVat;
    @ManyToOne
    @JoinColumn(name = "share_responsability_not_subject_vat")
    private Rules shareResponsabilityNotSubjectVat;
    @ManyToOne
    @JoinColumn(name = "ceiling_overflow_not_subject_vat")
    private Rules ceilingOverflowNotSubjectVat;
    @ManyToOne
    @JoinColumn(name = "proportional_rule_not_subject_vat")
    private Rules proportionalRuleNotSubjectVat;
	@ManyToOne
    @JoinColumn(name = "advance_invoice")
    private Rules advanceInvoice;
    @ManyToOne
    @JoinColumn(name = "commitment_ga_ihc_tva")
    private Rules commitmentGaIhcVat;
    @ManyToOne
    @JoinColumn(name = "commitment_ga_ihc_ntva")
    private Rules commitmentGaIhcNvat;
    @ManyToOne
    @JoinColumn(name = "commitment_ga_vat_inf")
    private Rules commitmentGaVatInf;
    @ManyToOne
    @JoinColumn(name = "commitment_ga_vat_equ")
    private Rules commitmentGaVatEqu;
    @ManyToOne
    @JoinColumn(name = "commitment_ga_vat_ninf")
    private Rules commitmentGaNvatInf;
    @ManyToOne
    @JoinColumn(name = "commitment_ga_vat_nequ")
    private Rules commitmentGaNvatEqu;
    @ManyToOne
    @JoinColumn(name = "commitment_ga_tdevis_inf")
    private Rules commitmentGaTdevisInf;
    @ManyToOne
    @JoinColumn(name = "commitment_ga_tdevis_sup")
    private Rules commitmentGaTdevisSup;
    @ManyToOne
    @JoinColumn(name = "commitment_ga_ndevis_inf")
    private Rules commitmentGaNdevisInf;
    @ManyToOne
    @JoinColumn(name = "commitment_ga_ndevis_sup")
    private Rules commitmentGaNdevisSup;
	@ManyToOne
	@JoinColumn(name = "hida_service_lower")
    private Rules hidaServiceLower;
	@ManyToOne
	@JoinColumn(name = "hida_service_between")
    private Rules hidaServiceBetween;
	@ManyToOne
	@JoinColumn(name = "hida_service_higher")
    private Rules hidaServiceHigher;
    @Column(name = "hida_service_charges_lower")
    private Double hidaServiceChargesLower;
    @Column(name = "hida_service_charges_higher")
    private Double hidaServiceChargesHigher;
    @Column(name = "hida_service_charges_between")
    private Double hidaServiceChargesBetween;
	@ManyToOne
    @JoinColumn(name = "guarantee_redemption")
    private Rules guaranteeredemption;
    @Column(name = "advance_invoice_higher")
    private Double advanceInvoiceHigher;
    @ManyToOne
    @JoinColumn(name = "insurance_deductible_rule")
    private Rules insuranceDeductibleRule;
    @Column(name = "insurance_deductible_percentage")
    private Integer insuranceDeductiblePercentage;
    @Column(name = "insurance_deductible_min")
    private Double insuranceDeductibleMin;
    @Column(name = "insurance_deductible_fixed")
    private Double insuranceDeductibleFixed;
	@Column(name = "start_date_convention")
    private Date startDateConvention;
	@Column(name = "effective_date")
    private Date effectiveDate;

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Partner getPartner() {
		return partner;
	}
	public void setPartner(Partner partner) {
		this.partner = partner;
	}
	public RefModeGestion getRefModeGestion() {
		return refModeGestion;
	}
	public void setRefModeGestion(RefModeGestion refModeGestion) {
		this.refModeGestion = refModeGestion;
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
	public Rules getVatSubjectVat() {
		return vatSubjectVat;
	}
	public void setVatSubjectVat(Rules vatSubjectVat) {
		this.vatSubjectVat = vatSubjectVat;
	}
	public Rules getStampDutySubjectVat() {
		return stampDutySubjectVat;
	}
	public void setStampDutySubjectVat(Rules stampDutySubjectVat) {
		this.stampDutySubjectVat = stampDutySubjectVat;
	}
	public Rules getVetusteSubjectVat() {
		return vetusteSubjectVat;
	}
	public void setVetusteSubjectVat(Rules vetusteSubjectVat) {
		this.vetusteSubjectVat = vetusteSubjectVat;
	}
	public Rules getShareResponsabilitySubjectvat() {
		return shareResponsabilitySubjectvat;
	}
	public void setShareResponsabilitySubjectvat(Rules shareResponsabilitySubjectvat) {
		this.shareResponsabilitySubjectvat = shareResponsabilitySubjectvat;
	}
	public Rules getCeilingOverflowSubjectVat() {
		return ceilingOverflowSubjectVat;
	}
	public void setCeilingOverflowSubjectVat(Rules ceilingOverflowSubjectVat) {
		this.ceilingOverflowSubjectVat = ceilingOverflowSubjectVat;
	}
	public Rules getProportionalRuleSubjectVat() {
		return proportionalRuleSubjectVat;
	}
	public void setProportionalRuleSubjectVat(Rules proportionalRuleSubjectVat) {
		this.proportionalRuleSubjectVat = proportionalRuleSubjectVat;
	}
	public Rules getVatNotSubjectVat() {
		return vatNotSubjectVat;
	}
	public void setVatNotSubjectVat(Rules vatNotSubjectVat) {
		this.vatNotSubjectVat = vatNotSubjectVat;
	}
	public Rules getStampDutyNotSubjectVat() {
		return stampDutyNotSubjectVat;
	}
	public void setStampDutyNotSubjectVat(Rules stampDutyNotSubjectVat) {
		this.stampDutyNotSubjectVat = stampDutyNotSubjectVat;
	}
	public Rules getVetusteNotSubjectVat() {
		return vetusteNotSubjectVat;
	}
	public void setVetusteNotSubjectVat(Rules vetusteNotSubjectVat) {
		this.vetusteNotSubjectVat = vetusteNotSubjectVat;
	}
	public Rules getShareResponsabilityNotSubjectVat() {
		return shareResponsabilityNotSubjectVat;
	}
	public void setShareResponsabilityNotSubjectVat(Rules shareResponsabilityNotSubjectVat) {
		this.shareResponsabilityNotSubjectVat = shareResponsabilityNotSubjectVat;
	}
	public Rules getCeilingOverflowNotSubjectVat() {
		return ceilingOverflowNotSubjectVat;
	}
	public void setCeilingOverflowNotSubjectVat(Rules ceilingOverflowNotSubjectVat) {
		this.ceilingOverflowNotSubjectVat = ceilingOverflowNotSubjectVat;
	}
	public Rules getProportionalRuleNotSubjectVat() {
		return proportionalRuleNotSubjectVat;
	}
	public void setProportionalRuleNotSubjectVat(Rules proportionalRuleNotSubjectVat) {
		this.proportionalRuleNotSubjectVat = proportionalRuleNotSubjectVat;
	}
	public Rules getAdvanceInvoice() {
		return advanceInvoice;
	}
	public void setAdvanceInvoice(Rules advanceInvoice) {
		this.advanceInvoice = advanceInvoice;
	}
	public Rules getCommitmentGaIhcVat() {
		return commitmentGaIhcVat;
	}
	public void setCommitmentGaIhcVat(Rules commitmentGaIhcVat) {
		this.commitmentGaIhcVat = commitmentGaIhcVat;
	}
	public Rules getCommitmentGaIhcNvat() {
		return commitmentGaIhcNvat;
	}
	public void setCommitmentGaIhcNvat(Rules commitmentGaIhcNvat) {
		this.commitmentGaIhcNvat = commitmentGaIhcNvat;
	}
	public Rules getCommitmentGaVatInf() {
		return commitmentGaVatInf;
	}
	public void setCommitmentGaVatInf(Rules commitmentGaVatInf) {
		this.commitmentGaVatInf = commitmentGaVatInf;
	}
	public Rules getCommitmentGaVatEqu() {
		return commitmentGaVatEqu;
	}
	public void setCommitmentGaVatEqu(Rules commitmentGaVatEqu) {
		this.commitmentGaVatEqu = commitmentGaVatEqu;
	}
	public Rules getCommitmentGaNvatInf() {
		return commitmentGaNvatInf;
	}
	public void setCommitmentGaNvatInf(Rules commitmentGaNvatInf) {
		this.commitmentGaNvatInf = commitmentGaNvatInf;
	}
	public Rules getCommitmentGaNvatEqu() {
		return commitmentGaNvatEqu;
	}
	public void setCommitmentGaNvatEqu(Rules commitmentGaNvatEqu) {
		this.commitmentGaNvatEqu = commitmentGaNvatEqu;
	}
	public Rules getCommitmentGaTdevisInf() {
		return commitmentGaTdevisInf;
	}
	public void setCommitmentGaTdevisInf(Rules commitmentGaTdevisInf) {
		this.commitmentGaTdevisInf = commitmentGaTdevisInf;
	}
	public Rules getCommitmentGaTdevisSup() {
		return commitmentGaTdevisSup;
	}
	public void setCommitmentGaTdevisSup(Rules commitmentGaTdevisSup) {
		this.commitmentGaTdevisSup = commitmentGaTdevisSup;
	}
	public Rules getCommitmentGaNdevisInf() {
		return commitmentGaNdevisInf;
	}
	public void setCommitmentGaNdevisInf(Rules commitmentGaNdevisInf) {
		this.commitmentGaNdevisInf = commitmentGaNdevisInf;
	}
	public Rules getCommitmentGaNdevisSup() {
		return commitmentGaNdevisSup;
	}
	public void setCommitmentGaNdevisSup(Rules commitmentGaNdevisSup) {
		this.commitmentGaNdevisSup = commitmentGaNdevisSup;
	}
	public Rules getHidaServiceLower() {
		return hidaServiceLower;
	}
	public void setHidaServiceLower(Rules hidaServiceLower) {
		this.hidaServiceLower = hidaServiceLower;
	}
	public Rules getHidaServiceBetween() {
		return hidaServiceBetween;
	}
	public void setHidaServiceBetween(Rules hidaServiceBetween) {
		this.hidaServiceBetween = hidaServiceBetween;
	}
	public Rules getHidaServiceHigher() {
		return hidaServiceHigher;
	}
	public void setHidaServiceHigher(Rules hidaServiceHigher) {
		this.hidaServiceHigher = hidaServiceHigher;
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
	public Rules getGuaranteeredemption() {
		return guaranteeredemption;
	}
	public void setGuaranteeredemption(Rules guaranteeredemption) {
		this.guaranteeredemption = guaranteeredemption;
	}
	public Double getAdvanceInvoiceHigher() {
		return advanceInvoiceHigher;
	}
	public void setAdvanceInvoiceHigher(Double advanceInvoiceHigher) {
		this.advanceInvoiceHigher = advanceInvoiceHigher;
	}
	public Rules getInsuranceDeductibleRule() {
		return insuranceDeductibleRule;
	}
	public void setInsuranceDeductibleRule(Rules insuranceDeductibleRule) {
		this.insuranceDeductibleRule = insuranceDeductibleRule;
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

	/*@Override
  public boolean equals(Object obj) {

    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    PartnerRules other = (PartnerRules) obj;
    if (id == other.id && partner.id == other.partner.id && refModeGestion.id == other.refModeGestion.id
	    && stampDuty == other.stampDuty && vetuste == other.vetuste && advanceOnInvoice == other.advanceOnInvoice
		&& insuranceDeductible == other.insuranceDeductible && hidaServiceCharges == other.hidaServiceCharges
		&& proportionalRule == other.proportionalRule && ceilingOverflow == other.ceilingOverflow
		&& redemptionGuarantee == other.redemptionGuarantee && shareResponsibility == other.shareResponsibility
		&& vatSubjectVat.id == other.vatSubjectVat.id && stampDutySubjectVat.id == other.stampDutySubjectVat.id
		&& vetusteSubjectVat.id == other.vetusteSubjectVat.id && shareResponsabilitySubjectvat.id == other.shareResponsabilitySubjectvat.id
		&& ceilingOverflowSubjectVat.id == other.ceilingOverflowSubjectVat.id && proportionalRuleSubjectVat.id == other.proportionalRuleSubjectVat.id
		&& vatNotSubjectVat.id == other.vatNotSubjectVat.id && stampDutyNotSubjectVat.id == other.stampDutyNotSubjectVat.id
		&& vetusteNotSubjectVat.id == other.vetusteNotSubjectVat.id && shareResponsabilityNotSubjectVat.id == other.shareResponsabilityNotSubjectVat.id
		&& ceilingOverflowNotSubjectVat.id == other.ceilingOverflowNotSubjectVat.id && proportionalRuleNotSubjectVat.id == other.proportionalRuleNotSubjectVat.id
		&& advanceInvoice.id == other.advanceInvoice.id && commitmentGaIhcVat.id == other.commitmentGaIhcVat.id
		&& commitmentGaIhcNvat.id == other.commitmentGaIhcNvat.id && commitmentGaVatInf.id == other.commitmentGaVatInf.id
		&& commitmentGaVatEqu.id == other.commitmentGaVatEqu.id && commitmentGaNvatInf.id == other.commitmentGaNvatInf.id
		&& commitmentGaNvatEqu.id == other.commitmentGaNvatEqu.id && commitmentGaTdevisInf.id == other.commitmentGaTdevisInf.id
		&& commitmentGaTdevisSup.id == other.commitmentGaTdevisSup.id && commitmentGaNdevisInf.id == other.commitmentGaNdevisInf.id
		&& commitmentGaNdevisSup.id == other.commitmentGaNdevisSup.id && hidaServiceLower.id == other.hidaServiceLower.id
		&& hidaServiceBetween.id == other.hidaServiceBetween.id && hidaServiceHigher.id == other.hidaServiceHigher.id
		&& hidaServiceChargesLower == other.hidaServiceChargesLower && hidaServiceChargesHigher == other.hidaServiceChargesHigher
		&& hidaServiceChargesBetween == other.hidaServiceChargesBetween && guaranteeredemption.id == other.guaranteeredemption.id
		&& advanceInvoiceHigher == other.advanceInvoiceHigher && insuranceDeductibleRule.id == other.insuranceDeductibleRule.id
		&& insuranceDeductiblePercentage == other.insuranceDeductiblePercentage && insuranceDeductibleMin == other.insuranceDeductibleMin
		&& insuranceDeductibleFixed == other.insuranceDeductibleFixed ){
      return true;
    }else{
    return false;
	}
  }*/
}