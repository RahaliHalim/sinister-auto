package com.gaconnecte.auxilium.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Concessionnaire entity.
 */
public class ConcessionnaireDTO implements Serializable {

    private Long id;

    @NotNull
    private String libelle;

    private Set<VehicleBrandDTO> marques = new HashSet<>();

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

    public Set<VehicleBrandDTO> getMarques() {
        return marques;
    }

    public void setMarques(Set<VehicleBrandDTO> refMarques) {
        this.marques = refMarques;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ConcessionnaireDTO concessionnaireDTO = (ConcessionnaireDTO) o;
        if(concessionnaireDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), concessionnaireDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ConcessionnaireDTO{" +
            "id=" + getId() +
            ", libelle='" + getLibelle() + "'" +
            "}";
    }
}
