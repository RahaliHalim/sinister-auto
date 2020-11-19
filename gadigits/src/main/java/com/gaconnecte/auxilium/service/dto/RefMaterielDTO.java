package com.gaconnecte.auxilium.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the RefMateriel entity.
 */
public class RefMaterielDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 100)
    private String libelle;

    private String description;

    private Boolean obligatoireCng;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean isObligatoireCng() {
        return obligatoireCng;
    }

    public void setObligatoireCng(Boolean obligatoireCng) {
        this.obligatoireCng = obligatoireCng;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RefMaterielDTO refMaterielDTO = (RefMaterielDTO) o;
        if(refMaterielDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), refMaterielDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RefMaterielDTO{" +
            "id=" + getId() +
            ", libelle='" + getLibelle() + "'" +
            ", description='" + getDescription() + "'" +
            ", obligatoireCng='" + isObligatoireCng() + "'" +
            "}";
    }
}
