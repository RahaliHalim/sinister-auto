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
 * A PieceJointe.
 */
@Entity
@Table(name = "piece_jointe")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "piecejointe")
public class PieceJointe implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(max = 256)
    @Column(name = "libelle", length = 256, nullable = false)
    private String libelle;

    @NotNull
    @Size(max = 256)
    @Column(name = "chemin", length = 256, nullable = false)
    private String chemin;

    @Column(name = "is_originale")
    private Boolean isOriginale;

    @NotNull
    @Column(name = "date_import", nullable = false)
    private LocalDate dateImport;

    @Size(max = 2000)
    @Column(name = "note", length = 2000)
    private String note;

    /**
     * Relation concernant les pieces jointes
     */
    @ApiModelProperty(value = "Relation concernant les pieces jointes")
    @ManyToOne(optional = false)

    @NotNull
    private RefTypePj type;

    @ManyToOne(optional = false)
    @NotNull
    private User user;

    @ManyToOne(optional = false)
    private PrestationPEC prestation;

    @ManyToOne(optional = false)
    private Devis devis;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public PieceJointe libelle(String libelle) {
        this.libelle = libelle;
        return this;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getChemin() {
        return chemin;
    }

    public PieceJointe chemin(String chemin) {
        this.chemin = chemin;
        return this;
    }

    public void setChemin(String chemin) {
        this.chemin = chemin;
    }

    public Boolean isIsOriginale() {
        return isOriginale;
    }

    public PieceJointe isOriginale(Boolean isOriginale) {
        this.isOriginale = isOriginale;
        return this;
    }

    public void setIsOriginale(Boolean isOriginale) {
        this.isOriginale = isOriginale;
    }

    public LocalDate getDateImport() {
        return dateImport;
    }

    public PieceJointe dateImport(LocalDate dateImport) {
        this.dateImport = dateImport;
        return this;
    }

    public void setDateImport(LocalDate dateImport) {
        this.dateImport = dateImport;
    }

     public String getNote() {
        return note;
    }

    public PieceJointe note(String note) {
        this.note = note;
        return this;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public RefTypePj getType() {
        return type;
    }

    public PieceJointe type(RefTypePj refTypePj) {
        this.type = refTypePj;
        return this;
    }

    public void setType(RefTypePj refTypePj) {
        this.type = refTypePj;
    }

    public User getUser() {
        return user;
    }

    public PieceJointe User(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

     public PrestationPEC getPrestation() {
        return prestation;
    }

    public PieceJointe prestation(PrestationPEC prestation) {
        this.prestation = prestation;
        return this;
    }

    public void setPrestation(PrestationPEC prestation) {
        this.prestation = prestation;
    }

     public Devis getDevis() {
        return devis;
    }

    public PieceJointe devis(Devis devis) {
        this.devis = devis;
        return this;
    }

    public void setDevis(Devis devis) {
        this.devis = devis;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PieceJointe pieceJointe = (PieceJointe) o;
        if (pieceJointe.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), pieceJointe.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PieceJointe{" +
            "id=" + getId() +
            ", libelle='" + getLibelle() + "'" +
            ", chemin='" + getChemin() + "'" +
            ", isOriginale='" + isIsOriginale() + "'" +
            ", dateImport='" + getDateImport() + "'" +
            ", prestationID='" + getPrestation() + "'" +
            "}";
    }
}
