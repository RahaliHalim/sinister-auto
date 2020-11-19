package com.gaconnecte.auxilium.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Tarif entity.
 */
public class TarifDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 100)
    private String libelle;

    @NotNull
    private Integer majorationFerier;

    @NotNull
    private Integer majorationNuit;

    @NotNull
    private Double tauxTarif;

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

    public Integer getMajorationFerier() {
        return majorationFerier;
    }

    public void setMajorationFerier(Integer majorationFerier) {
        this.majorationFerier = majorationFerier;
    }

    public Integer getMajorationNuit() {
        return majorationNuit;
    }

    public void setMajorationNuit(Integer majorationNuit) {
        this.majorationNuit = majorationNuit;
    }

    public Double getTauxTarif() {
        return tauxTarif;
    }

    public void setTauxTarif(Double tauxTarif) {
        this.tauxTarif = tauxTarif;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TarifDTO tarifDTO = (TarifDTO) o;
        if(tarifDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), tarifDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TarifDTO{" +
            "id=" + getId() +
            ", majorationFerier='" + getMajorationFerier() + "'" +
            ", majorationNuit='" + getMajorationNuit() + "'" +
            ", tauxTarif='" + getTauxTarif() + "'" +
            "}";
    }
}
