package com.gaconnecte.auxilium.service.dto;


import javax.validation.constraints.*;

import org.javers.core.metamodel.annotation.DiffIgnore;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the RefModeGestion entity.
 */
public class RefModeGestionDTO implements Serializable {
	@DiffIgnore
    private Long id;

    @NotNull
    @Size(max = 100)
    private String libelle;
    @DiffIgnore
    @Size(max = 256)
    private String description;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RefModeGestionDTO refModeGestionDTO = (RefModeGestionDTO) o;
        if(refModeGestionDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), refModeGestionDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RefModeGestionDTO{" +
            "id=" + getId() +
            ", libelle='" + getLibelle() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
