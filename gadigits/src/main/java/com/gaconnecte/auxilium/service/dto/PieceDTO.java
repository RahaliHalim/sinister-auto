package com.gaconnecte.auxilium.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Piece entity.
 */
public class PieceDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 100)
    private String reference;

    private String designation;

    private Boolean isVetuste;

    private Long typePieceId;

    private String typePieceLibelle;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

     public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public Boolean isIsVetuste() {
        return isVetuste;
    }

    public void setIsVetuste(Boolean isVetuste) {
        this.isVetuste = isVetuste;
    }

    public Long getTypePieceId() {
        return typePieceId;
    }

    public void setTypePieceId(Long refTypePiecesId) {
        this.typePieceId = refTypePiecesId;
    }

    public String getTypePieceLibelle() {
        return typePieceLibelle;
    }

    public void setTypePieceLibelle(String refTypePiecesLibelle) {
        this.typePieceLibelle = refTypePiecesLibelle;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PieceDTO pieceDTO = (PieceDTO) o;
        if(pieceDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), pieceDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PieceDTO{" +
            "id=" + getId() +
            ", reference='" + getReference() + "'" +
            ", isVetuste='" + isIsVetuste() + "'" +
            "}";
    }
}
