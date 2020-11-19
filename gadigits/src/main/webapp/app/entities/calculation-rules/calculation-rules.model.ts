import { BaseEntity } from './../../shared';
export class CalculationRules implements BaseEntity {
    constructor(
        public id?: number,
        public refPartnerId?: number,
        public refPartnerLabel?: string,
        public refModeGestionId?: number,
        public refModeGestionLabel?: string,
        public stampDuty?: boolean,
        public vetuste?: boolean,
        public advanceOnInvoice?: boolean,
        public insuranceDeductible?: boolean,
        public hidaServiceCharges?: boolean,
        public proportionalRule?: boolean,
        public ceilingOverflow?: boolean,
        public redemptionGuarantee?: boolean,
        public shareResponsibility?: boolean,
        public vatSubjectVatId?: number,
        public stampDutySubjectVatId?: number,
        public vetusteSubjectVatId?: number,
        public shareResponsabilitySubjectvatId?: number,
        public ceilingOverflowSubjectVatId?: number,
        public proportionalRuleSubjectVatId?: number,
        public vatNotSubjectVatId?: number,
        public stampDutyNotSubjectVatId?: number,
        public vetusteNotSubjectVatId?: number,
        public shareResponsabilityNotSubjectVatId?: number,
        public ceilingOverflowNotSubjectVatId?: number,
        public proportionalRuleNotSubjectVatId?: number,
        public advanceInvoiceId?: number,
        public commitmentGaIhcVatId?: number,
        public commitmentGaIhcNvatId?: number,
        public commitmentGaVatInfId?: number,
        public commitmentGaVatEquId?: number,
        public commitmentGaNvatInfId?: number,
        public commitmentGaNvatEquId?: number,
        public commitmentGaTdevisInfId?: number,
        public commitmentGaTdevisSupId?: number,
        public commitmentGaNdevisInfId?: number,
        public commitmentGaNdevisSupId?: number,
        public hidaServiceLowerId?: number,
        public hidaServiceBetweenId?: number,
        public hidaServiceHigherId?: number,
        public hidaServiceChargesLower?: number,
        public hidaServiceChargesHigher?: number,
        public hidaServiceChargesBetween?: number,
        public guaranteeredemptionId?: number,
        public advanceInvoiceHigher?: number,
        public insuranceDeductibleRuleId?: number,
        public insuranceDeductiblePercentage?: number,
        public insuranceDeductibleMin?: number,
        public insuranceDeductibleFixed?: number,
        public startDateConvention?: any,
        public effectiveDate?: any
    ) {
        this.stampDuty = false;
        this.vetuste = false;
        this.advanceOnInvoice = false;
        this.insuranceDeductible = false;
        this.hidaServiceCharges = false;
        this.proportionalRule = false;
        this.ceilingOverflow = false;
        this.redemptionGuarantee = false;
        this.shareResponsibility = false;
      }
}
