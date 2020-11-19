package com.gaconnecte.auxilium.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the BonSortie entity.
 */
public class BonSortieDTO implements Serializable {

    private Long id;

    @NotNull
    @DecimalMax(value = "100000000000000000")
    private Double numero;

    private Boolean isSigne;

    @Size(max = 256)
    private String observation;

    private Long refEtatBsId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getNumero() {
        return numero;
    }

    public void setNumero(Double numero) {
        this.numero = numero;
    }

    public Boolean isIsSigne() {
        return isSigne;
    }

    public void setIsSigne(Boolean isSigne) {
        this.isSigne = isSigne;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public Long getRefEtatBsId() {
        return refEtatBsId;
    }

    public void setRefEtatBsId(Long refEtatBsId) {
        this.refEtatBsId = refEtatBsId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        BonSortieDTO bonSortieDTO = (BonSortieDTO) o;
        if(bonSortieDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), bonSortieDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "BonSortieDTO{" +
            "id=" + getId() +
            ", numero='" + getNumero() + "'" +
            ", isSigne='" + isIsSigne() + "'" +
            ", observation='" + getObservation() + "'" +
            "}";
    }
}
