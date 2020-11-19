package com.gaconnecte.auxilium.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gaconnecte.auxilium.domain.referential.RefPack;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Policy.
 */
@Entity
@Table(name = "app_policy")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "app_policy")
public class Policy implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "reference")
    private String reference;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "new_vehicle_price", precision=10, scale=2)
    private BigDecimal newVehiclePrice;

    @Column(name = "new_vehicle_price_is_amount")
    private Boolean newVehiclePriceIsAmount;

    @Column(name = "dc_capital", precision=10, scale=2)
    private BigDecimal dcCapital;

    @Column(name = "dc_capital_is_amount")
    private Boolean dcCapitalIsAmount;

    @Column(name = "bg_capital", precision=10, scale=2)
    private BigDecimal bgCapital;

    @Column(name = "bg_capital_is_amount")
    private Boolean bgCapitalIsAmount;

    @Column(name = "market_value", precision=10, scale=2)
    private BigDecimal marketValue;

    @Column(name = "market_value_is_amount")
    private Boolean marketValueIsAmount;

    @Column(name = "active")
    private Boolean active;

    @Column(name = "deleted")
    private Boolean deleted;

    @ManyToOne
    @JoinColumn(name = "type_id")
    private PolicyType type;

    @ManyToOne
    @JoinColumn(name = "nature_id")
    private PolicyNature nature;

    @ManyToOne
    @JoinColumn(name = "periodicity_id")
    private Periodicity periodicity;

    @ManyToOne
    @JoinColumn(name = "pack_id")
    private RefPack pack;
    
    @ManyToOne
    @JoinColumn(name = "partner_id")
    private Partner partner;

    @ManyToOne
    @JoinColumn(name = "agency_id")
    private Agency agency;

    @ManyToOne
    @JoinColumn(name = "policy_holder_id")
    private PolicyHolder policyHolder;

    @OneToMany(mappedBy = "policy")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Vehicle> vehicles = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReference() {
        return reference;
    }

    public Policy reference(String reference) {
        this.reference = reference;
        return this;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public Policy startDate(LocalDate startDate) {
        this.startDate = startDate;
        return this;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public Policy endDate(LocalDate endDate) {
        this.endDate = endDate;
        return this;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public BigDecimal getNewVehiclePrice() {
        return newVehiclePrice;
    }

    public Policy newVehiclePrice(BigDecimal newVehiclePrice) {
        this.newVehiclePrice = newVehiclePrice;
        return this;
    }

    public void setNewVehiclePrice(BigDecimal newVehiclePrice) {
        this.newVehiclePrice = newVehiclePrice;
    }

    public Boolean isNewVehiclePriceIsAmount() {
        return newVehiclePriceIsAmount;
    }

    public Policy newVehiclePriceIsAmount(Boolean newVehiclePriceIsAmount) {
        this.newVehiclePriceIsAmount = newVehiclePriceIsAmount;
        return this;
    }

    public void setNewVehiclePriceIsAmount(Boolean newVehiclePriceIsAmount) {
        this.newVehiclePriceIsAmount = newVehiclePriceIsAmount;
    }

    public BigDecimal getDcCapital() {
        return dcCapital;
    }

    public Policy dcCapital(BigDecimal dcCapital) {
        this.dcCapital = dcCapital;
        return this;
    }

    public void setDcCapital(BigDecimal dcCapital) {
        this.dcCapital = dcCapital;
    }

    public Boolean isDcCapitalIsAmount() {
        return dcCapitalIsAmount;
    }

    public Policy dcCapitalIsAmount(Boolean dcCapitalIsAmount) {
        this.dcCapitalIsAmount = dcCapitalIsAmount;
        return this;
    }

    public void setDcCapitalIsAmount(Boolean dcCapitalIsAmount) {
        this.dcCapitalIsAmount = dcCapitalIsAmount;
    }

    public BigDecimal getBgCapital() {
        return bgCapital;
    }

    public Policy bgCapital(BigDecimal bgCapital) {
        this.bgCapital = bgCapital;
        return this;
    }

    public void setBgCapital(BigDecimal bgCapital) {
        this.bgCapital = bgCapital;
    }

    public Boolean isBgCapitalIsAmount() {
        return bgCapitalIsAmount;
    }

    public Policy bgCapitalIsAmount(Boolean bgCapitalIsAmount) {
        this.bgCapitalIsAmount = bgCapitalIsAmount;
        return this;
    }

    public void setBgCapitalIsAmount(Boolean bgCapitalIsAmount) {
        this.bgCapitalIsAmount = bgCapitalIsAmount;
    }

    public BigDecimal getMarketValue() {
        return marketValue;
    }

    public Policy marketValue(BigDecimal marketValue) {
        this.marketValue = marketValue;
        return this;
    }

    public void setMarketValue(BigDecimal marketValue) {
        this.marketValue = marketValue;
    }

    public Boolean isMarketValueIsAmount() {
        return marketValueIsAmount;
    }

    public Policy marketValueIsAmount(Boolean marketValueIsAmount) {
        this.marketValueIsAmount = marketValueIsAmount;
        return this;
    }

    public void setMarketValueIsAmount(Boolean marketValueIsAmount) {
        this.marketValueIsAmount = marketValueIsAmount;
    }

    public Boolean isActive() {
        return active;
    }

    public Policy active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Boolean isDeleted() {
        return deleted;
    }

    public Policy deleted(Boolean deleted) {
        this.deleted = deleted;
        return this;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public PolicyType getType() {
        return type;
    }

    public Policy type(PolicyType policyType) {
        this.type = policyType;
        return this;
    }

    public void setType(PolicyType policyType) {
        this.type = policyType;
    }

    public PolicyNature getNature() {
        return nature;
    }

    public Policy nature(PolicyNature policyNature) {
        this.nature = policyNature;
        return this;
    }

    public void setNature(PolicyNature policyNature) {
        this.nature = policyNature;
    }

    public Periodicity getPeriodicity() {
        return periodicity;
    }

    public Policy periodicity(Periodicity periodicity) {
        this.periodicity = periodicity;
        return this;
    }

    public void setPeriodicity(Periodicity periodicity) {
        this.periodicity = periodicity;
    }

    public RefPack getPack() {
        return pack;
    }

    public void setPack(RefPack pack) {
        this.pack = pack;
    }

    public Partner getPartner() {
        return partner;
    }

    public Policy partner(Partner partner) {
        this.partner = partner;
        return this;
    }

    public void setPartner(Partner partner) {
        this.partner = partner;
    }

    public Agency getAgency() {
        return agency;
    }

    public Policy agency(Agency agency) {
        this.agency = agency;
        return this;
    }

    public void setAgency(Agency agency) {
        this.agency = agency;
    }

    public PolicyHolder getPolicyHolder() {
        return policyHolder;
    }

    public Policy policyHolder(PolicyHolder policyHolder) {
        this.policyHolder = policyHolder;
        return this;
    }

    public void setPolicyHolder(PolicyHolder policyHolder) {
        this.policyHolder = policyHolder;
    }

    public Set<Vehicle> getVehicles() {
        return vehicles;
    }

    public Policy vehicles(Set<Vehicle> vehicles) {
        this.vehicles = vehicles;
        return this;
    }

    public Policy addVehicle(Vehicle vehicle) {
        this.vehicles.add(vehicle);
        vehicle.setPolicy(this);
        return this;
    }

    public Policy removeVehicle(Vehicle vehicle) {
        this.vehicles.remove(vehicle);
        vehicle.setPolicy(null);
        return this;
    }

    public void setVehicles(Set<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Policy policy = (Policy) o;
        if (policy.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), policy.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Policy{" +
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
