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
 * A RefMateriel.
 */
@Entity
@Table(name = "ref_materiel")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "refmateriel")
public class RefMateriel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(max = 100)
    @Column(name = "libelle", length = 100, nullable = false)
    private String libelle;

    @Column(name = "description")
    private String description;

    @Column(name = "obligatoire_cng")
    private Boolean obligatoireCng;

   /* @ManyToMany(mappedBy = "materiels")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Reparateur> reparateurs = new HashSet<>();*/

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public RefMateriel libelle(String libelle) {
        this.libelle = libelle;
        return this;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getDescription() {
        return description;
    }

    public RefMateriel description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean isObligatoireCng() {
        return obligatoireCng;
    }

    public RefMateriel obligatoireCng(Boolean obligatoireCng) {
        this.obligatoireCng = obligatoireCng;
        return this;
    }

    public void setObligatoireCng(Boolean obligatoireCng) {
        this.obligatoireCng = obligatoireCng;
    }

   /* public Set<Reparateur> getReparateurs() {
        return reparateurs;
    }

    public RefMateriel reparateurs(Set<Reparateur> reparateurs) {
        this.reparateurs = reparateurs;
        return this;
    }*/
/*
    public RefMateriel addReparateur(Reparateur reparateur) {
        this.reparateurs.add(reparateur);
        reparateur.getMateriels().add(this);
        return this;
    }

    public RefMateriel removeReparateur(Reparateur reparateur) {
        this.reparateurs.remove(reparateur);
        reparateur.getMateriels().remove(this);
        return this;
    }

    public void setReparateurs(Set<Reparateur> reparateurs) {
        this.reparateurs = reparateurs;
    }*/

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RefMateriel refMateriel = (RefMateriel) o;
        if (refMateriel.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), refMateriel.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RefMateriel{" +
            "id=" + getId() +
            ", libelle='" + getLibelle() + "'" +
            ", description='" + getDescription() + "'" +
            ", obligatoireCng='" + isObligatoireCng() + "'" +
            "}";
    }
}
