package com.gaconnecte.auxilium.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Facture.
 */
@Entity
@Table(name = "facture")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "facture")
public class Facture implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "is_supprime")
    private Boolean isSupprime;

    @NotNull
    @Column(name = "date_generation", nullable = false)
    private LocalDate dateGeneration;

    /**
     * Relation concernant la facture
     */
    @ApiModelProperty(value = "Relation concernant la facture")
    @OneToOne(optional = false)
    @NotNull
    @JoinColumn(unique = true)
    private Devis devis;

    @OneToOne
    @JoinColumn(unique = true)
    private BonSortie bonSortie;

    @OneToOne
    @JoinColumn(unique = true)
    private PrestationPEC dossier;

    @OneToMany(mappedBy = "facture")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<FactureDetailsMo> factureMos = new HashSet<>();

    @OneToMany(mappedBy = "facture")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<FacturePieces> factPieces = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isIsSupprime() {
        return isSupprime;
    }

    public Facture isSupprime(Boolean isSupprime) {
        this.isSupprime = isSupprime;
        return this;
    }

    public void setIsSupprime(Boolean isSupprime) {
        this.isSupprime = isSupprime;
    }

    public LocalDate getDateGeneration() {
        return dateGeneration;
    }

    public Facture dateGeneration(LocalDate dateGeneration) {
        this.dateGeneration = dateGeneration;
        return this;
    }

    public void setDateGeneration(LocalDate dateGeneration) {
        this.dateGeneration = dateGeneration;
    }

    public Devis getDevis() {
        return devis;
    }

    public Facture devis(Devis devis) {
        this.devis = devis;
        return this;
    }

    public void setDevis(Devis devis) {
        this.devis = devis;
    }

    public BonSortie getBonSortie() {
        return bonSortie;
    }

    public Facture bonSortie(BonSortie bonSortie) {
        this.bonSortie = bonSortie;
        return this;
    }

    public void setBonSortie(BonSortie bonSortie) {
        this.bonSortie = bonSortie;
    }

    public PrestationPEC getDossier() {
        return dossier;
    }

    public Facture dossier(PrestationPEC prestationPEC) {
        this.dossier = prestationPEC;
        return this;
    }

    public void setDossier(PrestationPEC prestationPEC) {
        this.dossier = prestationPEC;
    }

    public Set<FactureDetailsMo> getFactureMos() {
        return factureMos;
    }

    public Facture factureMos(Set<FactureDetailsMo> factureDetailsMos) {
        this.factureMos = factureDetailsMos;
        return this;
    }

    public Facture addFactureMo(FactureDetailsMo factureDetailsMo) {
        this.factureMos.add(factureDetailsMo);
        factureDetailsMo.setFacture(this);
        return this;
    }

    public Facture removeFactureMo(FactureDetailsMo factureDetailsMo) {
        this.factureMos.remove(factureDetailsMo);
        factureDetailsMo.setFacture(null);
        return this;
    }

    public void setFactureMos(Set<FactureDetailsMo> factureDetailsMos) {
        this.factureMos = factureDetailsMos;
    }

    public Set<FacturePieces> getFactPieces() {
        return factPieces;
    }

    public Facture factPieces(Set<FacturePieces> facturePieces) {
        this.factPieces = facturePieces;
        return this;
    }

    public Facture addFactPiece(FacturePieces facturePieces) {
        this.factPieces.add(facturePieces);
        facturePieces.setFacture(this);
        return this;
    }

    public Facture removeFactPiece(FacturePieces facturePieces) {
        this.factPieces.remove(facturePieces);
        facturePieces.setFacture(null);
        return this;
    }

    public void setFactPieces(Set<FacturePieces> facturePieces) {
        this.factPieces = facturePieces;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Facture facture = (Facture) o;
        if (facture.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), facture.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Facture{" +
            "id=" + getId() +
            ", isSupprime='" + isIsSupprime() + "'" +
            ", dateGeneration='" + getDateGeneration() + "'" +
            "}";
    }
}
