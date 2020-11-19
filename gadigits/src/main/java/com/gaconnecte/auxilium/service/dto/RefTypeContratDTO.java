package com.gaconnecte.auxilium.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the RefTypeContrat entity.
 */
public class RefTypeContratDTO implements Serializable {

    private Long id;

    @NotNull
    @Max(value = 99999999)
    private Integer code;

    @NotNull
    @Size(max = 100)
    private String libelle;

    private Boolean isActif;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public Boolean isIsActif() {
        return isActif;
    }

    public void setIsActif(Boolean isActif) {
        this.isActif = isActif;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RefTypeContratDTO refTypeContratDTO = (RefTypeContratDTO) o;
        if(refTypeContratDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), refTypeContratDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RefTypeContratDTO{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", libelle='" + getLibelle() + "'" +
            ", isActif='" + isIsActif() + "'" +
            "}";
    }
}
