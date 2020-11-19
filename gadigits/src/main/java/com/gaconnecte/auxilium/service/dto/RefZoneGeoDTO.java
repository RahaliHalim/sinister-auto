package com.gaconnecte.auxilium.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the RefZoneGeo entity.
 */
public class RefZoneGeoDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 100)
    private String libelle;

    private Set<GovernorateDTO> gouvernorats = new HashSet<>();

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

    public Set<GovernorateDTO> getGouvernorats() {
        return gouvernorats;
    }

    public void setGouvernorats(Set<GovernorateDTO> sysGouvernorats) {
        this.gouvernorats = sysGouvernorats;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RefZoneGeoDTO refZoneGeoDTO = (RefZoneGeoDTO) o;
        if(refZoneGeoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), refZoneGeoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RefZoneGeoDTO{" +
            "id=" + getId() +
            ", libelle='" + getLibelle() + "'" +
            "}";
    }
}
