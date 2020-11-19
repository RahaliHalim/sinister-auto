package com.gaconnecte.auxilium.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the DesignationTypeContrat entity.
 */
public class DesignationTypeContratDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 100)
    private String libelle;

    private Long compagnieId;

    private String compagnieNom;

    private Long refTypeContratId;

    private String refTypeContratLibelle;

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

    public Long getCompagnieId() {
        return compagnieId;
    }

    public void setCompagnieId(Long refCompagnieId) {
        this.compagnieId = refCompagnieId;
    }

    public String getCompagnieNom() {
        return compagnieNom;
    }

    public void setCompagnieNom(String refCompagnieNom) {
        this.compagnieNom = refCompagnieNom;
    }

    public Long getRefTypeContratId() {
        return refTypeContratId;
    }

    public void setRefTypeContratId(Long refTypeContratId) {
        this.refTypeContratId = refTypeContratId;
    }

    public String getRefTypeContratLibelle() {
        return refTypeContratLibelle;
    }

    public void setRefTypeContratLibelle(String refTypeContratLibelle) {
        this.refTypeContratLibelle = refTypeContratLibelle;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DesignationTypeContratDTO designationTypeContratDTO = (DesignationTypeContratDTO) o;
        if(designationTypeContratDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), designationTypeContratDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DesignationTypeContratDTO{" +
            "id=" + getId() +
            ", libelle='" + getLibelle() + "'" +
            "}";
    }
}
