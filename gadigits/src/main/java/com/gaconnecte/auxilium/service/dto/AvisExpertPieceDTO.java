package com.gaconnecte.auxilium.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the AvisExpertPiece entity.
 */
public class AvisExpertPieceDTO implements Serializable {

    private Long id;

    @Size(max = 100)
    private String motif;

    @Size(max = 2000)
    private String commentaire;

    @DecimalMax(value = "100000000000000000")
    private Double quantite;

    private Long detailsPiecesId;

    private Long validationDevisId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMotif() {
        return motif;
    }

    public void setMotif(String motif) {
        this.motif = motif;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public Double getQuantite() {
        return quantite;
    }

    public void setQuantite(Double quantite) {
        this.quantite = quantite;
    }

    public Long getDetailsPiecesId() {
        return detailsPiecesId;
    }

    public void setDetailsPiecesId(Long detailsPiecesId) {
        this.detailsPiecesId = detailsPiecesId;
    }

    public Long getValidationDevisId() {
        return validationDevisId;
    }

    public void setValidationDevisId(Long validationDevisId) {
        this.validationDevisId = validationDevisId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AvisExpertPieceDTO avisExpertPieceDTO = (AvisExpertPieceDTO) o;
        if(avisExpertPieceDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), avisExpertPieceDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AvisExpertPieceDTO{" +
            "id=" + getId() +
            ", motif='" + getMotif() + "'" +
            ", commentaire='" + getCommentaire() + "'" +
            ", quantite='" + getQuantite() + "'" +
            "}";
    }
}
