package com.gaconnecte.auxilium.service.dto;


import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Objects;

import com.gaconnecte.auxilium.domain.enumeration.EtatDossierRmq;
import com.gaconnecte.auxilium.domain.enumeration.NatureIncident;

/**
 * A DTO for the Dossier entity.
 */
public class DossierDTO implements Serializable {

    private Long id;

    private String reference;

    private Boolean deleted;

    private ZonedDateTime dateCreation;

    private ZonedDateTime dateCloture;

    private EtatDossierRmq etat;

    private ZonedDateTime dateDerniereMaj;

    private String telephone;

    private Integer nbrPassagers;

    private LocalDate dateAccident;

    private NatureIncident natureIncident;

    private String prenomConducteur;

    private String nomConducteur;

    private Integer permisConduc;

    private Long lieuId;

    private String lieuNom;

    private Long vehiculeId;
    
    private Long userId;

    public String vehiculeImmatriculationVehicule;

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

    public Boolean getDeleted() {
        return deleted;
    }

    public boolean isDeleted() {
        return this.deleted != null ? this.deleted.booleanValue() : false;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public ZonedDateTime getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(ZonedDateTime dateCreation) {
        this.dateCreation = dateCreation;
    }

    public ZonedDateTime getDateCloture() {
        return dateCloture;
    }

    public void setDateCloture(ZonedDateTime dateCloture) {
        this.dateCloture = dateCloture;
    }

    public ZonedDateTime getDateDerniereMaj() {
        return dateDerniereMaj;
    }

    public void setDateDerniereMaj(ZonedDateTime dateDerniereMaj) {
        this.dateDerniereMaj = dateDerniereMaj;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public Integer getNbrPassagers() {
        return nbrPassagers;
    }

    public void setNbrPassagers(Integer nbrPassagers) {
        this.nbrPassagers = nbrPassagers;
    }

    public LocalDate getDateAccident() {
        return dateAccident;
    }

    public void setDateAccident(LocalDate dateAccident) {
        this.dateAccident = dateAccident;
    }

    public NatureIncident getNatureIncident() {
        return natureIncident;
    }

    public void setNatureIncident(NatureIncident natureIncident) {
        this.natureIncident = natureIncident;
    }

    public String getPrenomConducteur() {
        return prenomConducteur;
    }

    public void setPrenomConducteur(String prenomConducteur) {
        this.prenomConducteur = prenomConducteur;
    }

    public String getNomConducteur() {
        return nomConducteur;
    }

    public void setNomConducteur(String nomConducteur) {
        this.nomConducteur = nomConducteur;
    }

    public Integer getPermisConduc() {
        return permisConduc;
    }

    public void setPermisConduc(Integer permisConduc) {
        this.permisConduc = permisConduc;
    }

    public Long getLieuId() {
        return lieuId;
    }

    public void setLieuId(Long sysVilleId) {
        this.lieuId = sysVilleId;
    }

    public EtatDossierRmq getEtat() {
        return etat;
    }

    public void setEtat(EtatDossierRmq etat) {
        this.etat = etat;
    }

    public String getLieuNom() {
        return lieuNom;
    }

    public void setLieuNom(String sysVilleNom) {
        this.lieuNom = sysVilleNom;
    }

    public Long getVehiculeId() {
        return vehiculeId;
    }

    public void setVehiculeId(Long vehiculeAssureId) {
        this.vehiculeId = vehiculeAssureId;
    }

    public String getVehiculeImmatriculationVehicule() {
        return vehiculeImmatriculationVehicule;
    }

    public void setVehiculeImmatriculationVehicule(String vehiculeAssureImmatriculationVehicule) {
        this.vehiculeImmatriculationVehicule = vehiculeAssureImmatriculationVehicule;
    }

    public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
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
            ", isDelete='" + isDeleted() + "'" +
            ", dateCreation='" + getDateCreation() + "'" +
            ", dateCloture='" + getDateCloture() + "'" +
            ", dateDerniereMaj='" + getDateDerniereMaj() + "'" +
            ", telephone='" + getTelephone() + "'" +
            ", nbrPassagers='" + getNbrPassagers() + "'" +
            ", dateAccident='" + getDateAccident() + "'" +
            ", prenomConducteur='" + getPrenomConducteur() + "'" +
            ", nomConducteur='" + getNomConducteur() + "'" +
            ", permisConduc='" + getPermisConduc() + "'" +
            "}";
    }
}
