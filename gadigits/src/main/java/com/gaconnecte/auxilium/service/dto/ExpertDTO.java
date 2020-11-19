package com.gaconnecte.auxilium.service.dto;

import java.time.LocalDate;


import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import org.javers.core.metamodel.annotation.DiffIgnore;

/**
 * A DTO for the Expert entity.
 */
public class ExpertDTO implements Serializable {

	private Long id;
	private String raisonSociale;
	private String nom;
	private String prenom;
	private String email;
	private String telephone;
	private String fax;
	private String mobile;
	private String mobile2;
	private String numeroFTUSA;
	private Boolean centreExpertise;
	private Boolean blocage;
	private Boolean isActive;
	private Boolean eng;
	private LocalDate debutBlocage;
	private LocalDate finBlocage;
	private Long delegationId;
	private Long gouvernoratId;
	private String delegationLabel;
	private String governorateLabel;
	private LocalDate dateEffetConvention;
	private LocalDate dateFinConvention;
	private String adresse;

	 private List<ExpertGarantieImpliqueDTO>  garantieImpliques;
	 @DiffIgnore
	 private List<DelegationDTO>   listeVilles;
	 @DiffIgnore
	 private List<GovernorateDTO> gouvernorats;
	 
	public List<DelegationDTO> getListeVilles() {
		return listeVilles;
	}

	public void setListeVilles(List<DelegationDTO> listeVilles) {
		this.listeVilles = listeVilles;
	}

	public Long getGouvernoratId() {
		return gouvernoratId;
	}

	public void setGouvernoratId(Long gouvernoratId) {
		this.gouvernoratId = gouvernoratId;
	}

	public String getDelegationLabel() {
		return delegationLabel;
	}

	public void setDelegationLabel(String delegationLabel) {
		this.delegationLabel = delegationLabel;
	}

	public String getGovernorateLabel() {
		return governorateLabel;
	}

	public void setGovernorateLabel(String governorateLabel) {
		this.governorateLabel = governorateLabel;
	}
	public List<GovernorateDTO> getGouvernorats() {
		return gouvernorats;
	}

	public void setGouvernorats(List<GovernorateDTO> gouvernorats) {
		this.gouvernorats = gouvernorats;
	}

	public String getMobile2() {
		return mobile2;
	}

	public void setMobile2(String mobile2) {
		this.mobile2 = mobile2;
	}


	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRaisonSociale() {
		return raisonSociale;
	}

	public void setRaisonSociale(String raisonSociale) {
		this.raisonSociale = raisonSociale;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}
	public List<ExpertGarantieImpliqueDTO> getGarantieImpliques() {
		return garantieImpliques;
	}

	public void setGarantieImpliques(List<ExpertGarantieImpliqueDTO> garantieImpliques) {
		this.garantieImpliques = garantieImpliques;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getNumeroFTUSA() {
		return numeroFTUSA;
	}

	public void setNumeroFTUSA(String numeroFTUSA) {
		this.numeroFTUSA = numeroFTUSA;
	}

	public Boolean getCentreExpertise() {
		return centreExpertise;
	}

	public void setCentreExpertise(Boolean centreExpertise) {
		this.centreExpertise = centreExpertise;
	}

	public Boolean getBlocage() {
		return blocage;
	}

	public void setBlocage(Boolean blocage) {
		this.blocage = blocage;
	}

	public Boolean getEng() {
		return eng;
	}

	public void setEng(Boolean eng) {
		this.eng = eng;
	}

	public LocalDate getDebutBlocage() {
		return debutBlocage;
	}

	public void setDebutBlocage(LocalDate debutBlocage) {
		this.debutBlocage = debutBlocage;
	}

	public LocalDate getFinBlocage() {
		return finBlocage;
	}

	public void setFinBlocage(LocalDate finBlocage) {
		this.finBlocage = finBlocage;
	}

	public Long getDelegationId() {
		return delegationId;
	}

	public void setDelegationId(Long delegationId) {
		this.delegationId = delegationId;
	}

	public String getAdresse() {
		return adresse;
	}

	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}

	public LocalDate getDateEffetConvention() {
		return dateEffetConvention;
	}

	public void setDateEffetConvention(LocalDate dateEffetConvention) {
		this.dateEffetConvention = dateEffetConvention;
	}

	public LocalDate getDateFinConvention() {
		return dateFinConvention;
	}

	public void setDateFinConvention(LocalDate dateFinConvention) {
		this.dateFinConvention = dateFinConvention;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		ExpertDTO expertDTO = (ExpertDTO) o;
		if (expertDTO.getId() == null || getId() == null) {
			return false;
		}
		return Objects.equals(getId(), expertDTO.getId());
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(getId());
	}

    @Override
    public String toString() {
        return "ExpertDTO{" + "id=" + id + ", raisonSociale=" + raisonSociale + ", nom=" + nom + ", prenom=" + prenom + ", email=" + email + ", telephone=" + telephone + ", fax=" + fax + ", mobile=" + mobile + ", mobile2=" + mobile2 + ", numeroFTUSA=" + numeroFTUSA + ", centreExpertise=" + centreExpertise + ", blocage=" + blocage + ", isActive=" + isActive + ", eng=" + eng + ", debutBlocage=" + debutBlocage + ", finBlocage=" + finBlocage + ", delegationId=" + delegationId + ", gouvernoratId=" + gouvernoratId + ", delegationLabel=" + delegationLabel + ", governorateLabel=" + governorateLabel + ", dateEffetConvention=" + dateEffetConvention + ", dateFinConvention=" + dateFinConvention + ", adresse=" + adresse + ", garantieImpliques=" + garantieImpliques + ", listeVilles=" + listeVilles + ", gouvernorats=" + gouvernorats + '}';
    }

}
