package com.gaconnecte.auxilium.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A AvisExpertPiece.
 */
@Entity
@Table(name = "avis_expert_piece")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "avisexpertpiece")
public class AvisExpertPiece implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Size(max = 100)
    @Column(name = "motif", length = 100)
    private String motif;

    @Size(max = 2000)
    @Column(name = "commentaire", length = 2000)
    private String commentaire;

    @DecimalMax(value = "100000000000000000")
    @Column(name = "quantite")
    private Double quantite;

    @OneToOne
    @JoinColumn(unique = true)
    private DetailsPieces detailsPieces;

    @ManyToOne
    private ValidationDevis validationDevis;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMotif() {
        return motif;
    }

    public AvisExpertPiece motif(String motif) {
        this.motif = motif;
        return this;
    }

    public void setMotif(String motif) {
        this.motif = motif;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public AvisExpertPiece commentaire(String commentaire) {
        this.commentaire = commentaire;
        return this;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public Double getQuantite() {
        return quantite;
    }

    public AvisExpertPiece quantite(Double quantite) {
        this.quantite = quantite;
        return this;
    }

    public void setQuantite(Double quantite) {
        this.quantite = quantite;
    }

    public DetailsPieces getDetailsPieces() {
        return detailsPieces;
    }

    public AvisExpertPiece detailsPieces(DetailsPieces detailsPieces) {
        this.detailsPieces = detailsPieces;
        return this;
    }

    public void setDetailsPieces(DetailsPieces detailsPieces) {
        this.detailsPieces = detailsPieces;
    }

    public ValidationDevis getValidationDevis() {
        return validationDevis;
    }

    public AvisExpertPiece validationDevis(ValidationDevis validationDevis) {
        this.validationDevis = validationDevis;
        return this;
    }

    public void setValidationDevis(ValidationDevis validationDevis) {
        this.validationDevis = validationDevis;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AvisExpertPiece avisExpertPiece = (AvisExpertPiece) o;
        if (avisExpertPiece.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), avisExpertPiece.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AvisExpertPiece{" +
            "id=" + getId() +
            ", motif='" + getMotif() + "'" +
            ", commentaire='" + getCommentaire() + "'" +
            ", quantite='" + getQuantite() + "'" +
            "}";
    }
}
