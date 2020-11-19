package com.gaconnecte.auxilium.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the RefModeReglement entity.
 */
public class RefModeReglementDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 60)
    private String nom;

    private Boolean isBloque;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Boolean isIsBloque() {
        return isBloque;
    }

    public void setIsBloque(Boolean isBloque) {
        this.isBloque = isBloque;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RefModeReglementDTO refModeReglementDTO = (RefModeReglementDTO) o;
        if(refModeReglementDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), refModeReglementDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RefModeReglementDTO{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", isBloque='" + isIsBloque() + "'" +
            "}";
    }
}
