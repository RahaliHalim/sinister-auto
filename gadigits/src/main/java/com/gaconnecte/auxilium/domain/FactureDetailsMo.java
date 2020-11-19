package com.gaconnecte.auxilium.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A FactureDetailsMo.
 */
@Entity
@Table(name = "facture_details_mo")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "facturedetailsmo")
public class FactureDetailsMo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "date_generation", nullable = false)
    private LocalDate dateGeneration;

    @ManyToOne
    private DetailsMo detailsMo;

    @ManyToOne
    private Facture facture;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDateGeneration() {
        return dateGeneration;
    }

    public FactureDetailsMo dateGeneration(LocalDate dateGeneration) {
        this.dateGeneration = dateGeneration;
        return this;
    }

    public void setDateGeneration(LocalDate dateGeneration) {
        this.dateGeneration = dateGeneration;
    }

    public DetailsMo getDetailsMo() {
        return detailsMo;
    }

    public FactureDetailsMo detailsMo(DetailsMo detailsMo) {
        this.detailsMo = detailsMo;
        return this;
    }

    public void setDetailsMo(DetailsMo detailsMo) {
        this.detailsMo = detailsMo;
    }

    public Facture getFacture() {
        return facture;
    }

    public FactureDetailsMo facture(Facture facture) {
        this.facture = facture;
        return this;
    }

    public void setFacture(Facture facture) {
        this.facture = facture;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        FactureDetailsMo factureDetailsMo = (FactureDetailsMo) o;
        if (factureDetailsMo.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), factureDetailsMo.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "FactureDetailsMo{" +
            "id=" + getId() +
            ", dateGeneration='" + getDateGeneration() + "'" +
            "}";
    }
}
