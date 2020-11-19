package com.gaconnecte.auxilium.service.dto;


import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the ValidationDevis entity.
 */
public class ValidationDevisDTO implements Serializable {

    private Long id;

    private Boolean isValide;

    private Boolean isAnnule;

    @Size(max = 2000)
    private String commentaire;

    @NotNull
    private LocalDate dateValidation;

    private Long affectExpertId;

    private Long devisId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isIsValide() {
        return isValide;
    }

    public void setIsValide(Boolean isValide) {
        this.isValide = isValide;
    }

    public Boolean isIsAnnule() {
        return isAnnule;
    }

    public void setIsAnnule(Boolean isAnnule) {
        this.isAnnule = isAnnule;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public LocalDate getDateValidation() {
        return dateValidation;
    }

    public void setDateValidation(LocalDate dateValidation) {
        this.dateValidation = dateValidation;
    }

    public Long getAffectExpertId() {
        return affectExpertId;
    }

    public void setAffectExpertId(Long affectExpertId) {
        this.affectExpertId = affectExpertId;
    }

    public Long getDevisId() {
        return devisId;
    }

    public void setDevisId(Long devisId) {
        this.devisId = devisId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ValidationDevisDTO validationDevisDTO = (ValidationDevisDTO) o;
        if(validationDevisDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), validationDevisDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ValidationDevisDTO{" +
            "id=" + getId() +
            ", isValide='" + isIsValide() + "'" +
            ", isAnnule='" + isIsAnnule() + "'" +
            ", commentaire='" + getCommentaire() + "'" +
            ", dateValidation='" + getDateValidation() + "'" +
            "}";
    }
}
