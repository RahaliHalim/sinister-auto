package com.gaconnecte.auxilium.service.dto;


import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the PieceJointe entity.
 */
public class PieceJointeDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 256)
    private String libelle;

    private String chemin;

    private Boolean isOriginale;

    
    private LocalDate dateImport;

    private Long typeId;

    private String typeLibelle;

    private Long userId;

    private String userLogin;

    private Long prestationId;

    //private String prestationReference;

    private Long devisId;

    @Size(max = 2000)
    private String note;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getChemin() {
        return chemin;
    }

    public void setChemin(String chemin) {
        this.chemin = chemin;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    public Boolean isIsOriginale() {
        return isOriginale;
    }

    public void setIsOriginale(Boolean isOriginale) {
        this.isOriginale = isOriginale;
    }

    public LocalDate getDateImport() {
        return dateImport;
    }

    public void setDateImport(LocalDate dateImport) {
        this.dateImport = dateImport;
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long refTypePjId) {
        this.typeId = refTypePjId;
    }

    public String getTypeLibelle() {
        return typeLibelle;
    }

    public void setTypeLibelle(String refTypePjLibelle) {
        this.typeLibelle = refTypePjLibelle;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

     public Long getPrestationId() {
        return prestationId;
    }

    public void setPrestationId(Long prestationId) {
        this.prestationId = prestationId;
    }

    public Long getDevisId() {
        return devisId;
    }

    public void setDevisId(Long devisId) {
        this.devisId = devisId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PieceJointeDTO pieceJointeDTO = (PieceJointeDTO) o;
        if(pieceJointeDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), pieceJointeDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PieceJointeDTO{" +
            "id=" + getId() +
            ", libelle='" + getLibelle() + "'" +
            ", chemin='" + getChemin() + "'" +
            ", isOriginale='" + isIsOriginale() + "'" +
            ", dateImport='" + getDateImport() + "'" +
            "}";
    }
}
