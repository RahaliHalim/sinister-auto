package com.gaconnecte.auxilium.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DesignationNatureContrat.
 */
@Entity
@Table(name = "designation_nature_contrat")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "designationnaturecontrat")
public class DesignationNatureContrat implements Serializable {

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
    private Partner compagnie;

    @ManyToOne
    private RefNatureContrat refTypeContrat;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public DesignationNatureContrat libelle(String libelle) {
        this.libelle = libelle;
        return this;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public Partner getCompagnie() {
        return compagnie;
    }

    public DesignationNatureContrat compagnie(Partner refCompagnie) {
        this.compagnie = refCompagnie;
        return this;
    }

    public void setCompagnie(Partner refCompagnie) {
        this.compagnie = refCompagnie;
    }

    public RefNatureContrat getRefTypeContrat() {
        return refTypeContrat;
    }

    public DesignationNatureContrat refTypeContrat(RefNatureContrat refNatureContrat) {
        this.refTypeContrat = refNatureContrat;
        return this;
    }

    public void setRefTypeContrat(RefNatureContrat refNatureContrat) {
        this.refTypeContrat = refNatureContrat;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DesignationNatureContrat designationNatureContrat = (DesignationNatureContrat) o;
        if (designationNatureContrat.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), designationNatureContrat.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DesignationNatureContrat{" +
            "id=" + getId() +
            ", libelle='" + getLibelle() + "'" +
            "}";
    }
}
