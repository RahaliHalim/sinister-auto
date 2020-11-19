package com.gaconnecte.auxilium.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DesignationTypeContrat.
 */
@Entity
@Table(name = "designation_type_contrat")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "designationtypecontrat")
public class DesignationTypeContrat implements Serializable {

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
    private RefTypeContrat refTypeContrat;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public DesignationTypeContrat libelle(String libelle) {
        this.libelle = libelle;
        return this;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public Partner getCompagnie() {
        return compagnie;
    }

    public DesignationTypeContrat compagnie(Partner refCompagnie) {
        this.compagnie = refCompagnie;
        return this;
    }

    public void setCompagnie(Partner refCompagnie) {
        this.compagnie = refCompagnie;
    }

    public RefTypeContrat getRefTypeContrat() {
        return refTypeContrat;
    }

    public DesignationTypeContrat refTypeContrat(RefTypeContrat refTypeContrat) {
        this.refTypeContrat = refTypeContrat;
        return this;
    }

    public void setRefTypeContrat(RefTypeContrat refTypeContrat) {
        this.refTypeContrat = refTypeContrat;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DesignationTypeContrat designationTypeContrat = (DesignationTypeContrat) o;
        if (designationTypeContrat.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), designationTypeContrat.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DesignationTypeContrat{" +
            "id=" + getId() +
            ", libelle='" + getLibelle() + "'" +
            "}";
    }
}
