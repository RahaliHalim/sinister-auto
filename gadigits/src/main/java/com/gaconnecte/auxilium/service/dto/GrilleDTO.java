package com.gaconnecte.auxilium.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Grille entity.
 */
public class GrilleDTO implements Serializable {

    private Long id;

    @NotNull
    @DecimalMax(value = "99999999")
    private Float th;

    @DecimalMax(value = "99")
    private Float remise;

    @DecimalMax(value = "99")
    private Float tva;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getTh() {
        return th;
    }

    public void setTh(Float th) {
        this.th = th;
    }

    public Float getRemise() {
        return remise;
    }

    public void setRemise(Float remise) {
        this.remise = remise;
    }

    public Float getTva() {
        return tva;
    }

    public void setTva(Float tva) {
        this.tva = tva;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        GrilleDTO grilleDTO = (GrilleDTO) o;
        if(grilleDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), grilleDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "GrilleDTO{" +
            "id=" + getId() +
            ", th='" + getTh() + "'" +
            ", remise='" + getRemise() + "'" +
            ", tva='" + getTva() + "'" +
            "}";
    }
}
