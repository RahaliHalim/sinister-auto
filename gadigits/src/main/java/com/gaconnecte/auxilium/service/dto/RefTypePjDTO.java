package com.gaconnecte.auxilium.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the RefTypePj entity.
 */
public class RefTypePjDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 100)
    private String libelle;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RefTypePjDTO refTypePjDTO = (RefTypePjDTO) o;
        if(refTypePjDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), refTypePjDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RefTypePjDTO{" +
            "id=" + getId() +
            ", libelle='" + getLibelle() + "'" +
            "}";
    }
}
