package com.gaconnecte.auxilium.service.dto;


import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Facture entity.
 */
public class FactureDTO implements Serializable {

    private Long id;

    private Boolean isSupprime;

    @NotNull
    private LocalDate dateGeneration;

    private Long devisId;

    private Long bonSortieId;

    private String bonSortieNumero;

    private Long dossierId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isIsSupprime() {
        return isSupprime;
    }

    public void setIsSupprime(Boolean isSupprime) {
        this.isSupprime = isSupprime;
    }

    public LocalDate getDateGeneration() {
        return dateGeneration;
    }

    public void setDateGeneration(LocalDate dateGeneration) {
        this.dateGeneration = dateGeneration;
    }

    public Long getDevisId() {
        return devisId;
    }

    public void setDevisId(Long devisId) {
        this.devisId = devisId;
    }

    public Long getBonSortieId() {
        return bonSortieId;
    }

    public void setBonSortieId(Long bonSortieId) {
        this.bonSortieId = bonSortieId;
    }

    public String getBonSortieNumero() {
        return bonSortieNumero;
    }

    public void setBonSortieNumero(String bonSortieNumero) {
        this.bonSortieNumero = bonSortieNumero;
    }

    public Long getDossierId() {
        return dossierId;
    }

    public void setDossierId(Long prestationPECId) {
        this.dossierId = prestationPECId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        FactureDTO factureDTO = (FactureDTO) o;
        if(factureDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), factureDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "FactureDTO{" +
            "id=" + getId() +
            ", isSupprime='" + isIsSupprime() + "'" +
            ", dateGeneration='" + getDateGeneration() + "'" +
            "}";
    }
}
