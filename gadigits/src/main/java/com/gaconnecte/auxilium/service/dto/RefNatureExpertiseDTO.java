package com.gaconnecte.auxilium.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the RefNatureExpertise entity.
 */
public class RefNatureExpertiseDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 100)
    private String libelle;

    private Boolean isActive;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public Boolean isIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RefNatureExpertiseDTO refNatureExpertiseDTO = (RefNatureExpertiseDTO) o;
        if(refNatureExpertiseDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), refNatureExpertiseDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RefNatureExpertiseDTO{" +
            "id=" + getId() +
            ", libelle='" + getLibelle() + "'" +
            ", isActive='" + isIsActive() + "'" +
            "}";
    }
}
