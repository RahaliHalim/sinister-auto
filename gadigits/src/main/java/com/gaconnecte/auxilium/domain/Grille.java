package com.gaconnecte.auxilium.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Grille.
 */
@Entity
@Table(name = "grille")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "grille")
public class Grille implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @DecimalMax(value = "99999999")
    @Column(name = "th", nullable = false)
    private Float th;

    @DecimalMax(value = "99")
    @Column(name = "remise")
    private Float remise;

    @DecimalMax(value = "99")
    @Column(name = "tva")
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

    public Grille th(Float th) {
        this.th = th;
        return this;
    }

    public void setTh(Float th) {
        this.th = th;
    }

    public Float getRemise() {
        return remise;
    }

    public Grille remise(Float remise) {
        this.remise = remise;
        return this;
    }

    public void setRemise(Float remise) {
        this.remise = remise;
    }

    public Float getTva() {
        return tva;
    }

    public Grille tva(Float tva) {
        this.tva = tva;
        return this;
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
        Grille grille = (Grille) o;
        if (grille.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), grille.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Grille{" +
            "id=" + getId() +
            ", th='" + getTh() + "'" +
            ", remise='" + getRemise() + "'" +
            ", tva='" + getTva() + "'" +
            "}";
    }
}
