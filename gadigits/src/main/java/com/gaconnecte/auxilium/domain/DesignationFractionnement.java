package com.gaconnecte.auxilium.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DesignationFractionnement.
 */
@Entity
@Table(name = "designation_fractionnement")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "designationfractionnement")
public class DesignationFractionnement implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(max = 100)
    @Column(name = "libelle", length = 100, nullable = false)
    private String libelle;

    @ManyToOne
    private RefFractionnement fractionnement;

    @ManyToOne
    private Partner compagnie;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public DesignationFractionnement libelle(String libelle) {
        this.libelle = libelle;
        return this;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public RefFractionnement getFractionnement() {
        return fractionnement;
    }

    public DesignationFractionnement fractionnement(RefFractionnement refFractionnement) {
        this.fractionnement = refFractionnement;
        return this;
    }

    public void setFractionnement(RefFractionnement refFractionnement) {
        this.fractionnement = refFractionnement;
    }

    public Partner getCompagnie() {
        return compagnie;
    }

    public DesignationFractionnement compagnie(Partner refCompagnie) {
        this.compagnie = refCompagnie;
        return this;
    }

    public void setCompagnie(Partner refCompagnie) {
        this.compagnie = refCompagnie;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DesignationFractionnement designationFractionnement = (DesignationFractionnement) o;
        if (designationFractionnement.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), designationFractionnement.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DesignationFractionnement{" +
            "id=" + getId() +
            ", libelle='" + getLibelle() + "'" +
            "}";
    }
}
