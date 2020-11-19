package com.gaconnecte.auxilium.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the DesignationFractionnement entity.
 */
public class DesignationFractionnementDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 100)
    private String libelle;

    private Long fractionnementId;

    private String fractionnementLibelle;

    private Long compagnieId;

    private String compagnieNom;

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

    public Long getFractionnementId() {
        return fractionnementId;
    }

    public void setFractionnementId(Long refFractionnementId) {
        this.fractionnementId = refFractionnementId;
    }

    public String getFractionnementLibelle() {
        return fractionnementLibelle;
    }

    public void setFractionnementLibelle(String refFractionnementLibelle) {
        this.fractionnementLibelle = refFractionnementLibelle;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DesignationFractionnementDTO designationFractionnementDTO = (DesignationFractionnementDTO) o;
        if(designationFractionnementDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), designationFractionnementDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DesignationFractionnementDTO{" +
            "id=" + getId() +
            ", libelle='" + getLibelle() + "'" +
            "}";
    }
}
