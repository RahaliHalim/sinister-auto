package com.gaconnecte.auxilium.domain.view;


import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import org.springframework.data.elasticsearch.annotations.Document;

/**
 * A ViewPolicy.
 */
@Entity
@Table(name = "view_policy")
@Document(indexName = "ViewPolicy")
public class ViewPolicy implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private Long id;

    @Column(name = "policy_number")
    private String policyNumber;

    @Column(name = "company_id")
    private Long companyId;

    @Column(name = "company_name")
    private String companyName;

    @Column(name = "agency_id")
    private Long agencyId;

    @Column(name = "agency_name")
    private String agencyName;

    @Column(name = "vehicle_id")
    private Long vehicleId;

    @Column(name = "registration_number")
    private String registrationNumber;

    @Column(name = "policy_holder_id")
    private Long policyHolderId;

    @Column(name = "policy_holder_name")
    private String policyHolderName;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;
    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPolicyNumber() {
        return policyNumber;
    }

    public ViewPolicy policyNumber(String policyNumber) {
        this.policyNumber = policyNumber;
        return this;
    }

    public void setPolicyNumber(String policyNumber) {
        this.policyNumber = policyNumber;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public ViewPolicy companyId(Long companyId) {
        this.companyId = companyId;
        return this;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public ViewPolicy companyName(String companyName) {
        this.companyName = companyName;
        return this;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Long getAgencyId() {
        return agencyId;
    }

    public ViewPolicy agencyId(Long agencyId) {
        this.agencyId = agencyId;
        return this;
    }

    public void setAgencyId(Long agencyId) {
        this.agencyId = agencyId;
    }

    public String getAgencyName() {
        return agencyName;
    }

    public ViewPolicy agencyName(String agencyName) {
        this.agencyName = agencyName;
        return this;
    }

    public void setAgencyName(String agencyName) {
        this.agencyName = agencyName;
    }

    public Long getVehicleId() {
        return vehicleId;
    }

    public ViewPolicy vehicleId(Long vehicleId) {
        this.vehicleId = vehicleId;
        return this;
    }

    public void setVehicleId(Long vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public ViewPolicy registrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
        return this;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public Long getPolicyHolderId() {
        return policyHolderId;
    }

    public ViewPolicy policyHolderId(Long policyHolderId) {
        this.policyHolderId = policyHolderId;
        return this;
    }

    public void setPolicyHolderId(Long policyHolderId) {
        this.policyHolderId = policyHolderId;
    }

    public String getPolicyHolderName() {
        return policyHolderName;
    }

    public ViewPolicy policyHolderName(String policyHolderName) {
        this.policyHolderName = policyHolderName;
        return this;
    }

    public void setPolicyHolderName(String policyHolderName) {
        this.policyHolderName = policyHolderName;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public ViewPolicy startDate(LocalDate startDate) {
        this.startDate = startDate;
        return this;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public ViewPolicy endDate(LocalDate endDate) {
        this.endDate = endDate;
        return this;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }


	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ViewPolicy viewPolicy = (ViewPolicy) o;
        if (viewPolicy.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), viewPolicy.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ViewPolicy{" +
            "id=" + getId() +
            ", policyNumber='" + getPolicyNumber() + "'" +
            ", companyId='" + getCompanyId() + "'" +
            ", companyName='" + getCompanyName() + "'" +
            ", agencyId='" + getAgencyId() + "'" +
            ", agencyName='" + getAgencyName() + "'" +
            ", vehicleId='" + getVehicleId() + "'" +
            ", registrationNumber='" + getRegistrationNumber() + "'" +
            ", policyHolderId='" + getPolicyHolderId() + "'" +
            ", policyHolderName='" + getPolicyHolderName() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            "}";
    }
}
