package com.gaconnecte.auxilium.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A PolicyHolder.
 */
@Entity
@Table(name = "app_policy_holder")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "app_policy_holder")
public class PolicyHolder implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "company")
    private Boolean company;

    @Column(name = "title")
    private String title;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "company_name")
    private String companyName;

    @Column(name = "first_phone")
    private String firstPhone;

    @Column(name = "second_phone")
    private String secondPhone;

    @Column(name = "fax")
    private String fax;

    @Column(name = "first_email")
    private String firstEmail;

    @Column(name = "second_email")
    private String secondEmail;

    @Column(name = "address")
    private String address;

    @Column(name = "identifier")
    private Integer identifier;

    @Column(name = "vat_registered")
    private Boolean vatRegistered;

    @Column(name = "trade_register")
    private String tradeRegister;

    @Column(name = "creation_date")
    private LocalDate creationDate;

    @Column(name = "update_date")
    private LocalDate updateDate;

    @ManyToOne
    private Governorate governorate;

    @ManyToOne
    private Delegation delegation;

    @ManyToOne(optional = false)
    @NotNull
    private User creationUser;

    @ManyToOne
    private User updateUser;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isCompany() {
        return company;
    }

    public PolicyHolder company(Boolean company) {
        this.company = company;
        return this;
    }

    public void setCompany(Boolean company) {
        this.company = company;
    }

    public String getTitle() {
        return title;
    }

    public PolicyHolder title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFirstName() {
        return firstName;
    }

    public PolicyHolder firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public PolicyHolder lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public PolicyHolder companyName(String companyName) {
        this.companyName = companyName;
        return this;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getFirstPhone() {
        return firstPhone;
    }

    public PolicyHolder firstPhone(String firstPhone) {
        this.firstPhone = firstPhone;
        return this;
    }

    public void setFirstPhone(String firstPhone) {
        this.firstPhone = firstPhone;
    }

    public String getSecondPhone() {
        return secondPhone;
    }

    public PolicyHolder secondPhone(String secondPhone) {
        this.secondPhone = secondPhone;
        return this;
    }

    public void setSecondPhone(String secondPhone) {
        this.secondPhone = secondPhone;
    }

    public String getFax() {
        return fax;
    }

    public PolicyHolder fax(String fax) {
        this.fax = fax;
        return this;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getFirstEmail() {
        return firstEmail;
    }

    public PolicyHolder firstEmail(String firstEmail) {
        this.firstEmail = firstEmail;
        return this;
    }

    public void setFirstEmail(String firstEmail) {
        this.firstEmail = firstEmail;
    }

    public String getSecondEmail() {
        return secondEmail;
    }

    public PolicyHolder secondEmail(String secondEmail) {
        this.secondEmail = secondEmail;
        return this;
    }

    public void setSecondEmail(String secondEmail) {
        this.secondEmail = secondEmail;
    }

    public String getAddress() {
        return address;
    }

    public PolicyHolder address(String address) {
        this.address = address;
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getIdentifier() {
        return identifier;
    }

    public PolicyHolder identifier(Integer identifier) {
        this.identifier = identifier;
        return this;
    }

    public void setIdentifier(Integer identifier) {
        this.identifier = identifier;
    }

    public Boolean isVatRegistered() {
        return vatRegistered;
    }

    public PolicyHolder vatRegistered(Boolean vatRegistered) {
        this.vatRegistered = vatRegistered;
        return this;
    }

    public void setVatRegistered(Boolean vatRegistered) {
        this.vatRegistered = vatRegistered;
    }

    public String getTradeRegister() {
        return tradeRegister;
    }

    public PolicyHolder tradeRegister(String tradeRegister) {
        this.tradeRegister = tradeRegister;
        return this;
    }

    public void setTradeRegister(String tradeRegister) {
        this.tradeRegister = tradeRegister;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public PolicyHolder creationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDate getUpdateDate() {
        return updateDate;
    }

    public PolicyHolder updateDate(LocalDate updateDate) {
        this.updateDate = updateDate;
        return this;
    }

    public void setUpdateDate(LocalDate updateDate) {
        this.updateDate = updateDate;
    }

    public Governorate getGovernorate() {
        return governorate;
    }

    public PolicyHolder governorate(Governorate governorate) {
        this.governorate = governorate;
        return this;
    }

    public void setGovernorate(Governorate governorate) {
        this.governorate = governorate;
    }

    public Delegation getDelegation() {
        return delegation;
    }

    public PolicyHolder delegation(Delegation delegation) {
        this.delegation = delegation;
        return this;
    }

    public void setDelegation(Delegation delegation) {
        this.delegation = delegation;
    }

    public User getCreationUser() {
        return creationUser;
    }

    public PolicyHolder creationUser(User user) {
        this.creationUser = user;
        return this;
    }

    public void setCreationUser(User user) {
        this.creationUser = user;
    }

    public User getUpdateUser() {
        return updateUser;
    }

    public PolicyHolder updateUser(User user) {
        this.updateUser = user;
        return this;
    }

    public void setUpdateUser(User user) {
        this.updateUser = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PolicyHolder policyHolder = (PolicyHolder) o;
        if (policyHolder.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), policyHolder.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PolicyHolder{" +
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
