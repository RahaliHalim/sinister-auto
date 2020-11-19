package com.gaconnecte.auxilium.service.dto;


import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the FacturePieces entity.
 */
public class FacturePiecesDTO implements Serializable {

    private Long id;

    @NotNull
    private LocalDate dateGeneration;

    private Long detailsPiecesId;

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

    public Long getDetailsPiecesId() {
        return detailsPiecesId;
    }

    public void setDetailsPiecesId(Long detailsPiecesId) {
        this.detailsPiecesId = detailsPiecesId;
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

        FacturePiecesDTO facturePiecesDTO = (FacturePiecesDTO) o;
        if(facturePiecesDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), facturePiecesDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "FacturePiecesDTO{" +
            "id=" + getId() +
            ", dateGeneration='" + getDateGeneration() + "'" +
            "}";
    }
}
