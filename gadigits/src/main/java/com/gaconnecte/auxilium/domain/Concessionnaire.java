package com.gaconnecte.auxilium.domain;

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
 * A Concessionnaire.
 */
@Entity
@Table(name = "concessionnaire")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "concessionnaire")
public class Concessionnaire implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "libelle", nullable = false)
    private String libelle;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @NotNull
    @JoinTable(name = "concessionnaire_marque",
               joinColumns = @JoinColumn(name="concessionnaires_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="marques_id", referencedColumnName="id"))
    private Set<VehicleBrand> marques = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public Concessionnaire libelle(String libelle) {
        this.libelle = libelle;
        return this;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public Set<VehicleBrand> getMarques() {
        return marques;
    }

    public Concessionnaire marques(Set<VehicleBrand> refMarques) {
        this.marques = refMarques;
        return this;
    }

    public void setMarques(Set<VehicleBrand> refMarques) {
        this.marques = refMarques;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Concessionnaire concessionnaire = (Concessionnaire) o;
        if (concessionnaire.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), concessionnaire.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Concessionnaire{" +
            "id=" + getId() +
            ", libelle='" + getLibelle() + "'" +
            "}";
    }
}
