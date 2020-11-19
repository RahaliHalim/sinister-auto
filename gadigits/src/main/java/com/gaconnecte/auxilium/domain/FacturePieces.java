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
 * A FacturePieces.
 */
@Entity
@Table(name = "facture_pieces")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "facturepieces")
public class FacturePieces implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "date_generation", nullable = false)
    private LocalDate dateGeneration;

    @ManyToOne
    private DetailsPieces detailsPieces;

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

    public FacturePieces dateGeneration(LocalDate dateGeneration) {
        this.dateGeneration = dateGeneration;
        return this;
    }

    public void setDateGeneration(LocalDate dateGeneration) {
        this.dateGeneration = dateGeneration;
    }

    public DetailsPieces getDetailsPieces() {
        return detailsPieces;
    }

    public FacturePieces detailsPieces(DetailsPieces detailsPieces) {
        this.detailsPieces = detailsPieces;
        return this;
    }

    public void setDetailsPieces(DetailsPieces detailsPieces) {
        this.detailsPieces = detailsPieces;
    }

    public Facture getFacture() {
        return facture;
    }

    public FacturePieces facture(Facture facture) {
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
        FacturePieces facturePieces = (FacturePieces) o;
        if (facturePieces.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), facturePieces.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "FacturePieces{" +
            "id=" + getId() +
            ", dateGeneration='" + getDateGeneration() + "'" +
            "}";
    }
}
