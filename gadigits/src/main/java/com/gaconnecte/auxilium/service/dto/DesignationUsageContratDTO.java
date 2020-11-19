package com.gaconnecte.auxilium.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the DesignationUsageContrat entity.
 */
public class DesignationUsageContratDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 100)
    private String libelle;

    private Long compagnieId;

    private String compagnieNom;

    private Long refUsageContratId;

    private String refUsageContratLibelle;

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

    public Long getRefUsageContratId() {
        return refUsageContratId;
    }

    public void setRefUsageContratId(Long refUsageContratId) {
        this.refUsageContratId = refUsageContratId;
    }

    public String getRefUsageContratLibelle() {
        return refUsageContratLibelle;
    }

    public void setRefUsageContratLibelle(String refUsageContratLibelle) {
        this.refUsageContratLibelle = refUsageContratLibelle;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DesignationUsageContratDTO designationUsageContratDTO = (DesignationUsageContratDTO) o;
        if(designationUsageContratDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), designationUsageContratDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DesignationUsageContratDTO{" +
            "id=" + getId() +
            ", libelle='" + getLibelle() + "'" +
            "}";
    }
}
