package com.gaconnecte.auxilium.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
 * A ValidationDevis.
 */
@Entity
@Table(name = "validation_devis")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "validationdevis")
public class ValidationDevis implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "is_valide")
    private Boolean isValide;

    @Column(name = "is_annule")
    private Boolean isAnnule;

    @Size(max = 2000)
    @Column(name = "commentaire", length = 2000)
    private String commentaire;

    @NotNull
    @Column(name = "date_validation", nullable = false)
    private LocalDate dateValidation;

    @OneToOne
    @JoinColumn(unique = true)
    private Devis devis;

    @OneToMany(mappedBy = "validationDevis")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<AvisExpertPiece> avisExpPieces = new HashSet<>();

    @OneToMany(mappedBy = "validationDevis")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<AvisExpertMo> avisExpMos = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isIsValide() {
        return isValide;
    }

    public ValidationDevis isValide(Boolean isValide) {
        this.isValide = isValide;
        return this;
    }

    public void setIsValide(Boolean isValide) {
        this.isValide = isValide;
    }

    public Boolean isIsAnnule() {
        return isAnnule;
    }

    public ValidationDevis isAnnule(Boolean isAnnule) {
        this.isAnnule = isAnnule;
        return this;
    }

    public void setIsAnnule(Boolean isAnnule) {
        this.isAnnule = isAnnule;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public ValidationDevis commentaire(String commentaire) {
        this.commentaire = commentaire;
        return this;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public LocalDate getDateValidation() {
        return dateValidation;
    }

    public ValidationDevis dateValidation(LocalDate dateValidation) {
        this.dateValidation = dateValidation;
        return this;
    }

    public void setDateValidation(LocalDate dateValidation) {
        this.dateValidation = dateValidation;
    }

    public Devis getDevis() {
        return devis;
    }

    public ValidationDevis devis(Devis devis) {
        this.devis = devis;
        return this;
    }

    public void setDevis(Devis devis) {
        this.devis = devis;
    }

    public Set<AvisExpertPiece> getAvisExpPieces() {
        return avisExpPieces;
    }

    public ValidationDevis avisExpPieces(Set<AvisExpertPiece> avisExpertPieces) {
        this.avisExpPieces = avisExpertPieces;
        return this;
    }

    public ValidationDevis addAvisExpPiece(AvisExpertPiece avisExpertPiece) {
        this.avisExpPieces.add(avisExpertPiece);
        avisExpertPiece.setValidationDevis(this);
        return this;
    }

    public ValidationDevis removeAvisExpPiece(AvisExpertPiece avisExpertPiece) {
        this.avisExpPieces.remove(avisExpertPiece);
        avisExpertPiece.setValidationDevis(null);
        return this;
    }

    public void setAvisExpPieces(Set<AvisExpertPiece> avisExpertPieces) {
        this.avisExpPieces = avisExpertPieces;
    }

    public Set<AvisExpertMo> getAvisExpMos() {
        return avisExpMos;
    }

    public ValidationDevis avisExpMos(Set<AvisExpertMo> avisExpertMos) {
        this.avisExpMos = avisExpertMos;
        return this;
    }

    public ValidationDevis addAvisExpMo(AvisExpertMo avisExpertMo) {
        this.avisExpMos.add(avisExpertMo);
        avisExpertMo.setValidationDevis(this);
        return this;
    }

    public ValidationDevis removeAvisExpMo(AvisExpertMo avisExpertMo) {
        this.avisExpMos.remove(avisExpertMo);
        avisExpertMo.setValidationDevis(null);
        return this;
    }

    public void setAvisExpMos(Set<AvisExpertMo> avisExpertMos) {
        this.avisExpMos = avisExpertMos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ValidationDevis validationDevis = (ValidationDevis) o;
        if (validationDevis.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), validationDevis.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ValidationDevis{" +
            "id=" + getId() +
            ", isValide='" + isIsValide() + "'" +
            ", isAnnule='" + isIsAnnule() + "'" +
            ", commentaire='" + getCommentaire() + "'" +
            ", dateValidation='" + getDateValidation() + "'" +
            "}";
    }
}
