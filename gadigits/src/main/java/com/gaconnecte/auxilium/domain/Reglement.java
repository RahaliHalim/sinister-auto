/*package com.gaconnecte.auxilium.domain;

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


@Entity
@Table(name = "reglement")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "reglement")
public class Reglement implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Max(value = 365)
    @Column(name = "echeance_jours")
    private Integer echeanceJours;

    @OneToMany(mappedBy = "reglement")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Reparateur> reparateurs = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    private RefModeReglement modeReglement;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getEcheanceJours() {
        return echeanceJours;
    }

    public Reglement echeanceJours(Integer echeanceJours) {
        this.echeanceJours = echeanceJours;
        return this;
    }

    public void setEcheanceJours(Integer echeanceJours) {
        this.echeanceJours = echeanceJours;
    }

    public Set<Reparateur> getReparateurs() {
        return reparateurs;
    }

    public Reglement reparateurs(Set<Reparateur> reparateurs) {
        this.reparateurs = reparateurs;
        return this;
    }

    public Reglement addReparateur(Reparateur reparateur) {
        this.reparateurs.add(reparateur);
        reparateur.setReglement(this);
        return this;
    }

    public Reglement removeReparateur(Reparateur reparateur) {
        this.reparateurs.remove(reparateur);
        reparateur.setReglement(null);
        return this;
    }

    public void setReparateurs(Set<Reparateur> reparateurs) {
        this.reparateurs = reparateurs;
    }

    public RefModeReglement getModeReglement() {
        return modeReglement;
    }

    public Reglement modeReglement(RefModeReglement refModeReglement) {
        this.modeReglement = refModeReglement;
        return this;
    }

    public void setModeReglement(RefModeReglement refModeReglement) {
        this.modeReglement = refModeReglement;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Reglement reglement = (Reglement) o;
        if (reglement.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), reglement.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Reglement{" +
            "id=" + getId() +
            ", echeanceJours='" + getEcheanceJours() + "'" +
            "}";
    }
}*/
