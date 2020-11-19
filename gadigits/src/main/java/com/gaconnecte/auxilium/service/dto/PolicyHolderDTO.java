package com.gaconnecte.auxilium.service.dto;


import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the PolicyHolder entity.
 */
public class PolicyHolderDTO implements Serializable {

    private Long id;

    private Boolean company;

    private String title;

    private String firstName;

    private String lastName;

    private String companyName;

    private String firstPhone;

    private String secondPhone;

    private String fax;

    private String firstEmail;

    private String secondEmail;

    private String address;

    private Integer identifier;

    private Boolean vatRegistered;

    private String tradeRegister;

    private LocalDate creationDate;

    private LocalDate updateDate;

    private Long governorateId;

    private String governorateLabel;

    private Long delegationId;

    private String delegationLabel;

    private Long creationUserId;

    private String creationUserLogin;

    private Long updateUserId;

    private String updateUserLogin;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isCompany() {
        return company;
    }

    public void setCompany(Boolean company) {
        this.company = company;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getFirstPhone() {
        return firstPhone;
    }

    public void setFirstPhone(String firstPhone) {
        this.firstPhone = firstPhone;
    }

    public String getSecondPhone() {
        return secondPhone;
    }

    public void setSecondPhone(String secondPhone) {
        this.secondPhone = secondPhone;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getFirstEmail() {
        return firstEmail;
    }

    public void setFirstEmail(String firstEmail) {
        this.firstEmail = firstEmail;
    }

    public String getSecondEmail() {
        return secondEmail;
    }

    public void setSecondEmail(String secondEmail) {
        this.secondEmail = secondEmail;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getIdentifier() {
        return identifier;
    }

    public void setIdentifier(Integer identifier) {
        this.identifier = identifier;
    }

    public Boolean isVatRegistered() {
        return vatRegistered;
    }

    public void setVatRegistered(Boolean vatRegistered) {
        this.vatRegistered = vatRegistered;
    }

    public String getTradeRegister() {
        return tradeRegister;
    }

    public void setTradeRegister(String tradeRegister) {
        this.tradeRegister = tradeRegister;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDate getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(LocalDate updateDate) {
        this.updateDate = updateDate;
    }

    public Long getGovernorateId() {
        return governorateId;
    }

    public void setGovernorateId(Long governorateId) {
        this.governorateId = governorateId;
    }

    public String getGovernorateLabel() {
        return governorateLabel;
    }

    public void setGovernorateLabel(String governorateLabel) {
        this.governorateLabel = governorateLabel;
    }

    public Long getDelegationId() {
        return delegationId;
    }

    public void setDelegationId(Long delegationId) {
        this.delegationId = delegationId;
    }

    public String getDelegationLabel() {
        return delegationLabel;
    }

    public void setDelegationLabel(String delegationLabel) {
        this.delegationLabel = delegationLabel;
    }

    public Long getCreationUserId() {
        return creationUserId;
    }

    public void setCreationUserId(Long userId) {
        this.creationUserId = userId;
    }

    public String getCreationUserLogin() {
        return creationUserLogin;
    }

    public void setCreationUserLogin(String userLogin) {
        this.creationUserLogin = userLogin;
    }

    public Long getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(Long userId) {
        this.updateUserId = userId;
    }

    public String getUpdateUserLogin() {
        return updateUserLogin;
    }

    public void setUpdateUserLogin(String userLogin) {
        this.updateUserLogin = userLogin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PolicyHolderDTO policyHolderDTO = (PolicyHolderDTO) o;
        if(policyHolderDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), policyHolderDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PolicyHolderDTO{" +
            "id=" + getId() +
            ", company='" + isCompany() + "'" +
            ", title='" + getTitle() + "'" +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", companyName='" + getCompanyName() + "'" +
            ", firstPhone='" + getFirstPhone() + "'" +
            ", secondPhone='" + getSecondPhone() + "'" +
            ", fax='" + getFax() + "'" +
            ", firstEmail='" + getFirstEmail() + "'" +
            ", secondEmail='" + getSecondEmail() + "'" +
            ", address='" + getAddress() + "'" +
            ", identifier='" + getIdentifier() + "'" +
            ", vatRegistered='" + isVatRegistered() + "'" +
            ", tradeRegister='" + getTradeRegister() + "'" +
            ", creationDate='" + getCreationDate() + "'" +
            ", updateDate='" + getUpdateDate() + "'" +
            "}";
    }
}
