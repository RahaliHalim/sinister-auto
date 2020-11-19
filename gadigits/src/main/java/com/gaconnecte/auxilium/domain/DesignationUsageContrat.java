package com.gaconnecte.auxilium.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DesignationUsageContrat.
 */
@Entity
@Table(name = "designation_usage_contrat")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "designationusagecontrat")
public class DesignationUsageContrat implements Serializable {

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
    private VehicleUsage refUsageContrat;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public DesignationUsageContrat libelle(String libelle) {
        this.libelle = libelle;
        return this;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public Partner getCompagnie() {
        return compagnie;
    }

    public DesignationUsageContrat compagnie(Partner refCompagnie) {
        this.compagnie = refCompagnie;
        return this;
    }

    public void setCompagnie(Partner refCompagnie) {
        this.compagnie = refCompagnie;
    }

    public VehicleUsage getRefUsageContrat() {
        return refUsageContrat;
    }

    public DesignationUsageContrat refUsageContrat(VehicleUsage refUsageContrat) {
        this.refUsageContrat = refUsageContrat;
        return this;
    }

    public void setRefUsageContrat(VehicleUsage refUsageContrat) {
        this.refUsageContrat = refUsageContrat;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DesignationUsageContrat designationUsageContrat = (DesignationUsageContrat) o;
        if (designationUsageContrat.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), designationUsageContrat.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DesignationUsageContrat{" +
            "id=" + getId() +
            ", libelle='" + getLibelle() + "'" +
            "}";
    }
}
