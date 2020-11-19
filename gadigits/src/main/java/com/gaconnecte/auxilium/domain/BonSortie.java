package com.gaconnecte.auxilium.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A BonSortie.
 */
@Entity
@Table(name = "bon_sortie")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "bonsortie")
public class BonSortie implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @DecimalMax(value = "100000000000000000")
    @Column(name = "numero", nullable = false)
    private Double numero;

    @Column(name = "is_signe")
    private Boolean isSigne;

    @Size(max = 256)
    @Column(name = "observation", length = 256)
    private String observation;

    @ManyToOne
    private RefEtatBs refEtatBs;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getNumero() {
        return numero;
    }

    public BonSortie numero(Double numero) {
        this.numero = numero;
        return this;
    }

    public void setNumero(Double numero) {
        this.numero = numero;
    }

    public Boolean isIsSigne() {
        return isSigne;
    }

    public BonSortie isSigne(Boolean isSigne) {
        this.isSigne = isSigne;
        return this;
    }

    public void setIsSigne(Boolean isSigne) {
        this.isSigne = isSigne;
    }

    public String getObservation() {
        return observation;
    }

    public BonSortie observation(String observation) {
        this.observation = observation;
        return this;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public RefEtatBs getRefEtatBs() {
        return refEtatBs;
    }

    public BonSortie refEtatBs(RefEtatBs refEtatBs) {
        this.refEtatBs = refEtatBs;
        return this;
    }

    public void setRefEtatBs(RefEtatBs refEtatBs) {
        this.refEtatBs = refEtatBs;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BonSortie bonSortie = (BonSortie) o;
        if (bonSortie.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), bonSortie.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "BonSortie{" +
            "id=" + getId() +
            ", numero='" + getNumero() + "'" +
            ", isSigne='" + isIsSigne() + "'" +
            ", observation='" + getObservation() + "'" +
            "}";
    }
}
