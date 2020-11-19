package com.gaconnecte.auxilium.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Fournisseur entity.
 */
public class FournisseurDTO implements Serializable {

    private Long id;

    @DecimalMax(value = "99")
    private Float remise;

    private Boolean isBloque;

    private Long personneMoraleId;

    private String personneMoraleRaisonSociale;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getRemise() {
        return remise;
    }

    public void setRemise(Float remise) {
        this.remise = remise;
    }

    public Boolean isIsBloque() {
        return isBloque;
    }

    public void setIsBloque(Boolean isBloque) {
        this.isBloque = isBloque;
    }

    public Long getPersonneMoraleId() {
        return personneMoraleId;
    }

    public void setPersonneMoraleId(Long personneMoraleId) {
        this.personneMoraleId = personneMoraleId;
    }

    public String getPersonneMoraleRaisonSociale() {
        return personneMoraleRaisonSociale;
    }

    public void setPersonneMoraleRaisonSociale(String personneMoraleRaisonSociale) {
        this.personneMoraleRaisonSociale = personneMoraleRaisonSociale;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        FournisseurDTO fournisseurDTO = (FournisseurDTO) o;
        if(fournisseurDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), fournisseurDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "FournisseurDTO{" +
            "id=" + getId() +
            ", remise='" + getRemise() + "'" +
            ", isBloque='" + isIsBloque() + "'" +
            "}";
    }
}
