package com.gaconnecte.auxilium.domain;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A AvisExpertMo.
 */
@Entity
@Table(name = "avis_expert_mo")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "avisexpertmo")
public class AvisExpertMo implements Serializable {

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

    /**
     * Relation concernant l'avis d expert
     */
    @ApiModelProperty(value = "Relation concernant l'avis d expert")
    @OneToOne
    @JoinColumn(unique = true)
    private DetailsMo detailsMo;

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

    public AvisExpertMo motif(String motif) {
        this.motif = motif;
        return this;
    }

    public void setMotif(String motif) {
        this.motif = motif;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public AvisExpertMo commentaire(String commentaire) {
        this.commentaire = commentaire;
        return this;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public Double getQuantite() {
        return quantite;
    }

    public AvisExpertMo quantite(Double quantite) {
        this.quantite = quantite;
        return this;
    }

    public void setQuantite(Double quantite) {
        this.quantite = quantite;
    }

    public DetailsMo getDetailsMo() {
        return detailsMo;
    }

    public AvisExpertMo detailsMo(DetailsMo detailsMo) {
        this.detailsMo = detailsMo;
        return this;
    }

    public void setDetailsMo(DetailsMo detailsMo) {
        this.detailsMo = detailsMo;
    }

    public ValidationDevis getValidationDevis() {
        return validationDevis;
    }

    public AvisExpertMo validationDevis(ValidationDevis validationDevis) {
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
        AvisExpertMo avisExpertMo = (AvisExpertMo) o;
        if (avisExpertMo.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), avisExpertMo.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AvisExpertMo{" +
            "id=" + getId() +
            ", motif='" + getMotif() + "'" +
            ", commentaire='" + getCommentaire() + "'" +
            ", quantite='" + getQuantite() + "'" +
            "}";
    }
}
