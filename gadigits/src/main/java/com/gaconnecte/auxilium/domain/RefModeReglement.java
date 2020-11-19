package com.gaconnecte.auxilium.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A RefModeReglement.
 */
@Entity
@Table(name = "ref_mode_reglement")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "reglement")
public class RefModeReglement implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(max = 60)
    @Column(name = "nom", length = 60, nullable = false)
    private String nom;

    @Column(name = "is_bloque")
    private Boolean isBloque;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public RefModeReglement nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Boolean isIsBloque() {
        return isBloque;
    }

    public RefModeReglement isBloque(Boolean isBloque) {
        this.isBloque = isBloque;
        return this;
    }

    public void setIsBloque(Boolean isBloque) {
        this.isBloque = isBloque;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RefModeReglement refModeReglement = (RefModeReglement) o;
        if (refModeReglement.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), refModeReglement.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RefModeReglement{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", isBloque='" + isIsBloque() + "'" +
            "}";
    }
}
