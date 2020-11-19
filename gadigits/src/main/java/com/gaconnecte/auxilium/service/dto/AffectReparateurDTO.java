package com.gaconnecte.auxilium.service.dto;


import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the AffectReparateur entity.
 */
public class AffectReparateurDTO implements Serializable {

    private Long id;

    @NotNull
    private LocalDate dateEffet;

    private LocalDate dateModif;

    @Size(max = 2000)
    private String commentaire;

    private Long devisId;

    private Long prestationId;

    private String dossierReference;

    private Long reparateurId;

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

    public Long getDevisId() {
        return devisId;
    }

    public void setDevisId(Long devisId) {
        this.devisId = devisId;
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

    public Long getReparateurId() {
        return reparateurId;
    }

    public void setReparateurId(Long reparateurId) {
        this.reparateurId = reparateurId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AffectReparateurDTO affectReparateurDTO = (AffectReparateurDTO) o;
        if(affectReparateurDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), affectReparateurDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AffectReparateurDTO{" +
            "id=" + getId() +
            ", dateEffet='" + getDateEffet() + "'" +
            ", dateModif='" + getDateModif() + "'" +
            ", commentaire='" + getCommentaire() + "'" +
            "}";
    }
}
