package com.gaconnecte.auxilium.service.dto;


import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import com.gaconnecte.auxilium.domain.enumeration.EtatDossierRmq;
import com.gaconnecte.auxilium.domain.enumeration.NatureIncident;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * A DTO for the Dossier entity.
 */
public class DemandPecDTO implements Serializable {

    private Long id;
	private String reference;
    private String referenceSinistre;
    private Boolean isDelete;
    private EtatDossierRmq etat;
    private LocalDateTime dateCreation;
    private LocalDate dateCloture;
    private LocalDate dateDerniereMaj;
    private String telephone;
    private String contractNumber;
    private LocalDate dateAccident;
    private Long lieuId;
    private String lieuNom;
    private Long insuredId;
    private String insuredName;
    public String immatriculation;
    private String managmentMode;

    private Long quotationId;


	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the reference
	 */
	public String getReference() {
		return reference;
	}

	/**
	 * @param reference the reference to set
	 */
	public void setReference(String reference) {
		this.reference = reference;
	}

	/**
	 * @return the referenceSinistre
	 */
	public String getReferenceSinistre() {
		return referenceSinistre;
	}

	/**
	 * @param referenceSinistre the referenceSinistre to set
	 */
	public void setReferenceSinistre(String referenceSinistre) {
		this.referenceSinistre = referenceSinistre;
	}

	/**
	 * @return the isDelete
	 */
	public Boolean getIsDelete() {
		return isDelete;
	}

	/**
	 * @param isDelete the isDelete to set
	 */
	public void setIsDelete(Boolean isDelete) {
		this.isDelete = isDelete;
	}

	/**
	 * @return the etat
	 */
	public EtatDossierRmq getEtat() {
		return etat;
	}

	/**
	 * @param etat the etat to set
	 */
	public void setEtat(EtatDossierRmq etat) {
		this.etat = etat;
	}

	/**
	 * @return the dateCreation
	 */
	

	/**
	 * @return the dateCloture
	 */
	public LocalDate getDateCloture() {
		return dateCloture;
	}

	public LocalDateTime getDateCreation() {
		return dateCreation;
	}

	public void setDateCreation(LocalDateTime dateCreation) {
		this.dateCreation = dateCreation;
	}

	/**
	 * @param dateCloture the dateCloture to set
	 */
	public void setDateCloture(LocalDate dateCloture) {
		this.dateCloture = dateCloture;
	}

	/**
	 * @return the dateDerniereMaj
	 */
	public LocalDate getDateDerniereMaj() {
		return dateDerniereMaj;
	}

	/**
	 * @param dateDerniereMaj the dateDerniereMaj to set
	 */
	public void setDateDerniereMaj(LocalDate dateDerniereMaj) {
		this.dateDerniereMaj = dateDerniereMaj;
	}

	/**
	 * @return the telephone
	 */
	public String getTelephone() {
		return telephone;
	}

	/**
	 * @param telephone the telephone to set
	 */
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	/**
	 * @return the contractNumber
	 */
	public String getContractNumber() {
		return contractNumber;
	}

	/**
	 * @param contractNumber the contractNumber to set
	 */
	public void setContractNumber(String contractNumber) {
		this.contractNumber = contractNumber;
	}

	/**
	 * @return the dateAccident
	 */
	public LocalDate getDateAccident() {
		return dateAccident;
	}

	/**
	 * @param dateAccident the dateAccident to set
	 */
	public void setDateAccident(LocalDate dateAccident) {
		this.dateAccident = dateAccident;
	}

	/**
	 * @return the lieuId
	 */
	public Long getLieuId() {
		return lieuId;
	}

	/**
	 * @param lieuId the lieuId to set
	 */
	public void setLieuId(Long lieuId) {
		this.lieuId = lieuId;
	}

	/**
	 * @return the lieuNom
	 */
	public String getLieuNom() {
		return lieuNom;
	}

	/**
	 * @param lieuNom the lieuNom to set
	 */
	public void setLieuNom(String lieuNom) {
		this.lieuNom = lieuNom;
	}

	/**
	 * @return the insuredId
	 */
	public Long getInsuredId() {
		return insuredId;
	}

	/**
	 * @param insuredId the insuredId to set
	 */
	public void setInsuredId(Long insuredId) {
		this.insuredId = insuredId;
	}

	/**
	 * @return the insuredName
	 */
	public String getInsuredName() {
		return insuredName;
	}

	/**
	 * @param insuredName the insuredName to set
	 */
	public void setInsuredName(String insuredName) {
		this.insuredName = insuredName;
	}

	/**
	 * @return the immatriculation
	 */
	public String getImmatriculation() {
		return immatriculation;
	}

	/**
	 * @param immatriculation the immatriculation to set
	 */
	public void setImmatriculation(String immatriculation) {
		this.immatriculation = immatriculation;
	}

	/**
	 * @return the managmentMode
	 */
	public String getManagmentMode() {
		return managmentMode;
	}

	/**
	 * @param managmentMode the managmentMode to set
	 */
	public void setManagmentMode(String managmentMode) {
		this.managmentMode = managmentMode;
	}

    public Long getQuotationId() {
        return quotationId;
    }

    public void setQuotationId(Long quotationId) {
        this.quotationId = quotationId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DossierDTO dossierDTO = (DossierDTO) o;
        if(dossierDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), dossierDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DossierDTO{" +
            "id=" + getId() +
            ", reference='" + getReference() + "'" +
            /*", isDelete='" + isIsDelete() + "'" +
            ", dateCreation='" + getDateCreation() + "'" +
            ", dateCloture='" + getDateCloture() + "'" +
            ", dateDerniereMaj='" + getDateDerniereMaj() + "'" +
            ", telephone='" + getTelephone() + "'" +
            ", nbrPassagers='" + getNbrPassagers() + "'" +
            ", dateAccident='" + getDateAccident() + "'" +
            ", prenomConducteur='" + getPrenomConducteur() + "'" +
            ", nomConducteur='" + getNomConducteur() + "'" +
            ", permisConduc='" + getPermisConduc() + "'" +
            ", prenomTiersConduc='" + getPrenomTiersConduc() + "'" +
            ", nomTiersConduc='" + getNomTiersConduc() + "'" +
            ", permisTiersConduc='" + getPermisTiersConduc() + "'" +*/
            "}";
    }


}
