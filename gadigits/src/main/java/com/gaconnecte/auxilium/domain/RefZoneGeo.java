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
 * A RefZoneGeo.
 */
@Entity
@Table(name = "ref_zone_geo")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "refzonegeo")
public class RefZoneGeo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(max = 100)
    @Column(name = "libelle", length = 100, nullable = false)
    private String libelle;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @NotNull
    @JoinTable(name = "ref_zone_geo_gouvernorat",
               joinColumns = @JoinColumn(name="ref_zone_geos_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="gouvernorats_id", referencedColumnName="id"))
    private Set<Governorate> gouvernorats = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public RefZoneGeo libelle(String libelle) {
        this.libelle = libelle;
        return this;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public Set<Governorate> getGouvernorats() {
        return gouvernorats;
    }

    public RefZoneGeo gouvernorats(Set<Governorate> sysGouvernorats) {
        this.gouvernorats = sysGouvernorats;
        return this;
    }

    public void setGouvernorats(Set<Governorate> sysGouvernorats) {
        this.gouvernorats = sysGouvernorats;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RefZoneGeo refZoneGeo = (RefZoneGeo) o;
        if (refZoneGeo.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), refZoneGeo.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RefZoneGeo{" +
            "id=" + getId() +
            ", libelle='" + getLibelle() + "'" +
            "}";
    }
}
