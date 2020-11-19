package com.gaconnecte.auxilium.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Tarif.
 */
@Entity
@Table(name = "tarif")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Tarif implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(max = 100)
    @Column(name = "libelle", length = 100, nullable = false)
    private String libelle;

    @NotNull
    @Column(name = "majoration_ferier", nullable = false)
    private Integer majorationFerier;

    @NotNull
    @Column(name = "majoration_nuit", nullable = false)
    private Integer majorationNuit;

    @NotNull
    @Column(name = "taux_tarif", nullable = false)
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

    public Tarif libelle(String libelle) {
        this.libelle = libelle;
        return this;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public Integer getMajorationFerier() {
        return majorationFerier;
    }

    public Tarif majorationFerier(Integer majorationFerier) {
        this.majorationFerier = majorationFerier;
        return this;
    }

    public void setMajorationFerier(Integer majorationFerier) {
        this.majorationFerier = majorationFerier;
    }

    public Integer getMajorationNuit() {
        return majorationNuit;
    }

    public Tarif majorationNuit(Integer majorationNuit) {
        this.majorationNuit = majorationNuit;
        return this;
    }

    public void setMajorationNuit(Integer majorationNuit) {
        this.majorationNuit = majorationNuit;
    }

    public Double getTauxTarif() {
        return tauxTarif;
    }

    public Tarif tauxTarif(Double tauxTarif) {
        this.tauxTarif = tauxTarif;
        return this;
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
        Tarif tarif = (Tarif) o;
        if (tarif.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), tarif.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Tarif{" +
            "id=" + getId() +
            ", majorationFerier='" + getMajorationFerier() + "'" +
            ", majorationNuit='" + getMajorationNuit() + "'" +
            ", tauxTarif='" + getTauxTarif() + "'" +
            "}";
    }
}
