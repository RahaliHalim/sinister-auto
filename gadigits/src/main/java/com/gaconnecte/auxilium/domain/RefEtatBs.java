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
 * A RefEtatBs.
 */
@Entity
@Table(name = "ref_etat_bs")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "refetatbs")
public class RefEtatBs implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(max = 100)
    @Column(name = "libelle", length = 100, nullable = false)
    private String libelle;

    @OneToMany(mappedBy = "refEtatBs")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<BonSortie> bonSorties = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public RefEtatBs libelle(String libelle) {
        this.libelle = libelle;
        return this;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public Set<BonSortie> getBonSorties() {
        return bonSorties;
    }

    public RefEtatBs bonSorties(Set<BonSortie> bonSorties) {
        this.bonSorties = bonSorties;
        return this;
    }

    public RefEtatBs addBonSortie(BonSortie bonSortie) {
        this.bonSorties.add(bonSortie);
        bonSortie.setRefEtatBs(this);
        return this;
    }

    public RefEtatBs removeBonSortie(BonSortie bonSortie) {
        this.bonSorties.remove(bonSortie);
        bonSortie.setRefEtatBs(null);
        return this;
    }

    public void setBonSorties(Set<BonSortie> bonSorties) {
        this.bonSorties = bonSorties;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RefEtatBs refEtatBs = (RefEtatBs) o;
        if (refEtatBs.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), refEtatBs.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RefEtatBs{" +
            "id=" + getId() +
            ", libelle='" + getLibelle() + "'" +
            "}";
    }
}
