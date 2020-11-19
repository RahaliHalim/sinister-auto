package com.gaconnecte.auxilium.service.dto;


import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the FactureDetailsMo entity.
 */
public class FactureDetailsMoDTO implements Serializable {

    private Long id;

    @NotNull
    private LocalDate dateGeneration;

    private Long detailsMoId;

    private Long factureId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDateGeneration() {
        return dateGeneration;
    }

    public void setDateGeneration(LocalDate dateGeneration) {
        this.dateGeneration = dateGeneration;
    }

    public Long getDetailsMoId() {
        return detailsMoId;
    }

    public void setDetailsMoId(Long detailsMoId) {
        this.detailsMoId = detailsMoId;
    }

    public Long getFactureId() {
        return factureId;
    }

    public void setFactureId(Long factureId) {
        this.factureId = factureId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        FactureDetailsMoDTO factureDetailsMoDTO = (FactureDetailsMoDTO) o;
        if(factureDetailsMoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), factureDetailsMoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "FactureDetailsMoDTO{" +
            "id=" + getId() +
            ", dateGeneration='" + getDateGeneration() + "'" +
            "}";
    }
}
