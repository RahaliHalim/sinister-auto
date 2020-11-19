package com.gaconnecte.auxilium.service.dto;


import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import com.gaconnecte.auxilium.domain.enumeration.TypePrestation;

/**
 * A DTO for the Prestation entity.
 */
public class PrestationDTO implements Serializable {

    private Long id;

    @NotNull
    private String reference;

    private Boolean isDelete;

    private ZonedDateTime dateCreation;
   
    private ZonedDateTime dateCloture;

    private ZonedDateTime dateDerniereMaj;

    private Boolean isFerier;

    private Boolean isNuit;

    @Size(max = 2000)
    private String commentaire;

    private TypePrestation typePrestation;

    private Long dossierId;
    
    private Long userId;

    private String dossierReference;


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

    public Boolean isIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
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

    public Boolean isIsFerier() {
        return isFerier;
    }

    public void setIsFerier(Boolean isFerier) {
        this.isFerier = isFerier;
    }

    public Boolean isIsNuit() {
        return isNuit;
    }

    public void setIsNuit(Boolean isNuit) {
        this.isNuit = isNuit;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public TypePrestation getTypePrestation() {
        return typePrestation;
    }

    public void setTypePrestation(TypePrestation typePrestation) {
        this.typePrestation = typePrestation;
    }

    public Long getDossierId() {
        return dossierId;
    }

    public void setDossierId(Long dossierId) {
        this.dossierId = dossierId;
    }

    public String getDossierReference() {
        return dossierReference;
    }

    public void setDossierReference(String dossierReference) {
        this.dossierReference = dossierReference;
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

        PrestationDTO prestationDTO = (PrestationDTO) o;
        if(prestationDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), prestationDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PrestationDTO{" +
            "id=" + getId() +
            ", reference='" + getReference() + "'" +
            ", isDelete='" + isIsDelete() + "'" +
            ", dateCreation='" + getDateCreation() + "'" +
            ", dateCloture='" + getDateCloture() + "'" +
            ", dateDerniereMaj='" + getDateDerniereMaj() + "'" +
            ", isFerier='" + isIsFerier() + "'" +
            ", isNuit='" + isIsNuit() + "'" +
            ", commentaire='" + getCommentaire() + "'" +
            "}";
    }
}
