package com.gaconnecte.auxilium.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Reglement entity.
 */
public class ReglementDTO implements Serializable {

    private Long id;

    @Max(value = 365)
    private Integer echeanceJours;

    private Long modeReglementId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getEcheanceJours() {
        return echeanceJours;
    }

    public void setEcheanceJours(Integer echeanceJours) {
        this.echeanceJours = echeanceJours;
    }

    public Long getModeReglementId() {
        return modeReglementId;
    }

    public void setModeReglementId(Long refModeReglementId) {
        this.modeReglementId = refModeReglementId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ReglementDTO reglementDTO = (ReglementDTO) o;
        if(reglementDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), reglementDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ReglementDTO{" +
            "id=" + getId() +
            ", echeanceJours='" + getEcheanceJours() + "'" +
            "}";
    }
}
