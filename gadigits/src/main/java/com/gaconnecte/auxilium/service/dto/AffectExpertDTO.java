package com.gaconnecte.auxilium.service.dto;


import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the AffectExpert entity.
 */
public class AffectExpertDTO implements Serializable {

    private Long id;

    private LocalDate dateEffet;

    private LocalDate dateModif;

    @Size(max = 2000)
    private String commentaire;

    private Long prestationId;

    private String dossierReference;

    private Long natureExpertiseId;

    private String natureExpertiseLibelle;

    private Long expertId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDateEffet() {
        return dateEffet;
    }

    public void setDateEffet(LocalDate dateEffet) {
        this.dateEffet = dateEffet;
    }

    public LocalDate getDateModif() {
        return dateModif;
    }

    public void setDateModif(LocalDate dateModif) {
        this.dateModif = dateModif;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public Long getPrestationId() {
        return prestationId;
    }

    public void setPrestationId(Long prestationPECId) {
        this.prestationId = prestationPECId;
    }

    public String getDossierReference() {
        return dossierReference;
    }

    public void setDossierReference(String dossierSinistreReference) {
        this.dossierReference = dossierSinistreReference;
    }

    public Long getNatureExpertiseId() {
        return natureExpertiseId;
    }

    public void setNatureExpertiseId(Long refNatureExpertiseId) {
        this.natureExpertiseId = refNatureExpertiseId;
    }

    public String getNatureExpertiseLibelle() {
        return natureExpertiseLibelle;
    }

    public void setNatureExpertiseLibelle(String refNatureExpertiseLibelle) {
        this.natureExpertiseLibelle = refNatureExpertiseLibelle;
    }

    public Long getExpertId() {
        return expertId;
    }

    public void setExpertId(Long expertId) {
        this.expertId = expertId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AffectExpertDTO affectExpertDTO = (AffectExpertDTO) o;
        if(affectExpertDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), affectExpertDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AffectExpertDTO{" +
            "id=" + getId() +
            ", dateEffet='" + getDateEffet() + "'" +
            ", dateModif='" + getDateModif() + "'" +
            ", commentaire='" + getCommentaire() + "'" +
            "}";
    }
}
