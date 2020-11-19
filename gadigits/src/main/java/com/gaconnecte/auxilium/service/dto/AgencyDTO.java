package com.gaconnecte.auxilium.service.dto;


import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;

import java.util.Objects;

/**
 * A DTO for the Agency entity.
 */
public class AgencyDTO implements Serializable {

    private Long id;

    private String code;

    private String name;

    private String address;

    private String zipcode;

    private String phone;

    private String mobile;

    private String nom;

    private String prenom;

    private Integer genre;

    private String fax;

    private String email;

    private String deuxiemeMail;

    private String type;

    private String typeAgence;

    private String category;

    private Long partnerId;

    private String partnerCompanyName;

    private Long governorateId;

    private String governorateLabel;

    private Long delegationId;

    private String delegationLabel;

    private Long regionId;

    private String regionLabel;

    private LocalDate dateCreation;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Long getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(Long partnerId) {
        this.partnerId = partnerId;
    }


    public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public Integer getGenre() {
		return genre;
	}

	public void setGenre(Integer genre) {
		this.genre = genre;
	}

	public String getDeuxiemeMail() {
		return deuxiemeMail;
	}

	public void setDeuxiemeMail(String deuxiemeMail) {
		this.deuxiemeMail = deuxiemeMail;
	}

	public String getTypeAgence() {
		return typeAgence;
	}

	public void setTypeAgence(String typeAgence) {
		this.typeAgence = typeAgence;
	}

	public String getPartnerCompanyName() {
        return partnerCompanyName;
    }

    public void setPartnerCompanyName(String partnerCompanyName) {
        this.partnerCompanyName = partnerCompanyName;
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

    public Long getRegionId() {
        return regionId;
    }

    public void setRegionId(Long regionId) {
        this.regionId = regionId;
    }

    public String getRegionLabel() {
        return regionLabel;
    }

    public void setRegionLabel(String regionLabel) {
        this.regionLabel = regionLabel;
    }

    public LocalDate getDateCreation() {
		return dateCreation;
	}

	public void setDateCreation(LocalDate dateCreation) {
		this.dateCreation = dateCreation;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AgencyDTO agencyDTO = (AgencyDTO) o;
        if(agencyDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), agencyDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AgencyDTO{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", name='" + getName() + "'" +
            ", address='" + getAddress() + "'" +
            ", zipcode='" + getZipcode() + "'" +
            ", phone='" + getPhone() + "'" +
            ", mobile='" + getMobile() + "'" +
            ", fax='" + getFax() + "'" +
            ", email='" + getEmail() + "'" +
            ", type='" + getType() + "'" +
            ", category='" + getCategory() + "'" +
            "}";
    }
}
