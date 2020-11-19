package com.gaconnecte.auxilium.service.dto;


import java.time.LocalDate;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Policy entity.
 */
public class PolicyDTO implements Serializable {

    private Long id;

    private String reference;

    private LocalDate startDate;

    private LocalDate endDate;

    private BigDecimal newVehiclePrice;

    private Boolean newVehiclePriceIsAmount;

    private BigDecimal dcCapital;

    private Boolean dcCapitalIsAmount;

    private BigDecimal bgCapital;

    private Boolean bgCapitalIsAmount;

    private BigDecimal marketValue;

    private Boolean marketValueIsAmount;

    private Boolean active;

    private Boolean deleted;

    private Long typeId;

    private String typeLabel;

    private Long natureId;

    private String natureLabel;

    private Long periodicityId;

    private String periodicityLabel;

    private Long partnerId;

    private String partnerCompanyName;

    private Long agencyId;

    private String agencyName;

    private Long policyHolderId;

    private String policyHolderCompanyName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public BigDecimal getNewVehiclePrice() {
        return newVehiclePrice;
    }

    public void setNewVehiclePrice(BigDecimal newVehiclePrice) {
        this.newVehiclePrice = newVehiclePrice;
    }

    public Boolean isNewVehiclePriceIsAmount() {
        return newVehiclePriceIsAmount;
    }

    public void setNewVehiclePriceIsAmount(Boolean newVehiclePriceIsAmount) {
        this.newVehiclePriceIsAmount = newVehiclePriceIsAmount;
    }

    public BigDecimal getDcCapital() {
        return dcCapital;
    }

    public void setDcCapital(BigDecimal dcCapital) {
        this.dcCapital = dcCapital;
    }

    public Boolean isDcCapitalIsAmount() {
        return dcCapitalIsAmount;
    }

    public void setDcCapitalIsAmount(Boolean dcCapitalIsAmount) {
        this.dcCapitalIsAmount = dcCapitalIsAmount;
    }

    public BigDecimal getBgCapital() {
        return bgCapital;
    }

    public void setBgCapital(BigDecimal bgCapital) {
        this.bgCapital = bgCapital;
    }

    public Boolean isBgCapitalIsAmount() {
        return bgCapitalIsAmount;
    }

    public void setBgCapitalIsAmount(Boolean bgCapitalIsAmount) {
        this.bgCapitalIsAmount = bgCapitalIsAmount;
    }

    public BigDecimal getMarketValue() {
        return marketValue;
    }

    public void setMarketValue(BigDecimal marketValue) {
        this.marketValue = marketValue;
    }

    public Boolean isMarketValueIsAmount() {
        return marketValueIsAmount;
    }

    public void setMarketValueIsAmount(Boolean marketValueIsAmount) {
        this.marketValueIsAmount = marketValueIsAmount;
    }

    public Boolean isActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long policyTypeId) {
        this.typeId = policyTypeId;
    }

    public String getTypeLabel() {
        return typeLabel;
    }

    public void setTypeLabel(String policyTypeLabel) {
        this.typeLabel = policyTypeLabel;
    }

    public Long getNatureId() {
        return natureId;
    }

    public void setNatureId(Long policyNatureId) {
        this.natureId = policyNatureId;
    }

    public String getNatureLabel() {
        return natureLabel;
    }

    public void setNatureLabel(String policyNatureLabel) {
        this.natureLabel = policyNatureLabel;
    }

    public Long getPeriodicityId() {
        return periodicityId;
    }

    public void setPeriodicityId(Long periodicityId) {
        this.periodicityId = periodicityId;
    }

    public String getPeriodicityLabel() {
        return periodicityLabel;
    }

    public void setPeriodicityLabel(String periodicityLabel) {
        this.periodicityLabel = periodicityLabel;
    }

    public Long getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(Long partnerId) {
        this.partnerId = partnerId;
    }

    public String getPartnerCompanyName() {
        return partnerCompanyName;
    }

    public void setPartnerCompanyName(String partnerCompanyName) {
        this.partnerCompanyName = partnerCompanyName;
    }

    public Long getAgencyId() {
        return agencyId;
    }

    public void setAgencyId(Long agencyId) {
        this.agencyId = agencyId;
    }

    public String getAgencyName() {
        return agencyName;
    }

    public void setAgencyName(String agencyName) {
        this.agencyName = agencyName;
    }

    public Long getPolicyHolderId() {
        return policyHolderId;
    }

    public void setPolicyHolderId(Long policyHolderId) {
        this.policyHolderId = policyHolderId;
    }

    public String getPolicyHolderCompanyName() {
        return policyHolderCompanyName;
    }

    public void setPolicyHolderCompanyName(String policyHolderCompanyName) {
        this.policyHolderCompanyName = policyHolderCompanyName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PolicyDTO policyDTO = (PolicyDTO) o;
        if(policyDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), policyDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PolicyDTO{" +
            "id=" + getId() +
            ", reference='" + getReference() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", newVehiclePrice='" + getNewVehiclePrice() + "'" +
            ", newVehiclePriceIsAmount='" + isNewVehiclePriceIsAmount() + "'" +
            ", dcCapital='" + getDcCapital() + "'" +
            ", dcCapitalIsAmount='" + isDcCapitalIsAmount() + "'" +
            ", bgCapital='" + getBgCapital() + "'" +
            ", bgCapitalIsAmount='" + isBgCapitalIsAmount() + "'" +
            ", marketValue='" + getMarketValue() + "'" +
            ", marketValueIsAmount='" + isMarketValueIsAmount() + "'" +
            ", active='" + isActive() + "'" +
            ", deleted='" + isDeleted() + "'" +
            "}";
    }
}
