package com.gaconnecte.auxilium.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Piece.
 */
@Entity
@Table(name = "piece")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "piece")
public class Piece implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(max = 100)
    @Column(name = "reference", length = 100, nullable = false)
    private String reference;

    private String designation;

    @Column(name = "is_vetuste")
    private Boolean isVetuste;

    @ManyToOne(optional = false)
    @NotNull
    private RefTypePieces typePiece;

    @OneToMany(mappedBy = "designation")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<DetailsMo> detailMos = new HashSet<>();

    @OneToMany(mappedBy = "designation")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<DetailsPieces> detailsPieces = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReference() {
        return reference;
    }

    public Piece reference(String reference) {
        this.reference = reference;
        return this;
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

    public Piece isVetuste(Boolean isVetuste) {
        this.isVetuste = isVetuste;
        return this;
    }

    public void setIsVetuste(Boolean isVetuste) {
        this.isVetuste = isVetuste;
    }

    public RefTypePieces getTypePiece() {
        return typePiece;
    }

    public Piece typePiece(RefTypePieces refTypePieces) {
        this.typePiece = refTypePieces;
        return this;
    }

    public void setTypePiece(RefTypePieces refTypePieces) {
        this.typePiece = refTypePieces;
    }
    public Set<DetailsMo> getDetailMos() {
        return detailMos;
    }

    public Piece detailMos(Set<DetailsMo> detailsMos) {
        this.detailMos = detailsMos;
        return this;
    }

    public Piece addDetailMo(DetailsMo detailsMo) {
        this.detailMos.add(detailsMo);
        detailsMo.setDesignation(this);
        return this;
    }

    public Piece removeDetailMo(DetailsMo detailsMo) {
        this.detailMos.remove(detailsMo);
        detailsMo.setDesignation(null);
        return this;
    }

    public void setDetailMos(Set<DetailsMo> detailsMos) {
        this.detailMos = detailsMos;
    }

    public Set<DetailsPieces> getDetailPieces() {
        return detailsPieces;
    }

    public Piece detailPieces(Set<DetailsPieces> detailsPieces) {
        this.detailsPieces = detailsPieces;
        return this;
    }

    /*public Piece addDetailsPieces(DetailsPieces detailsPieces) {
        this.detailsPieces.add(detailsPieces);
        detailsPieces.setDesignation(this);
        return this;
    }*/

    public Piece removeDetailsPieces(DetailsPieces detailsPieces) {
        this.detailsPieces.remove(detailsPieces);
        detailsPieces.setDesignation(null);
        return this;
    }

    public void setDetailsPieces(Set<DetailsPieces> detailsPieces) {
        this.detailsPieces = detailsPieces;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Piece piece = (Piece) o;
        if (piece.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), piece.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Piece{" +
            "id=" + getId() +
            ", reference='" + getReference() + "'" +
            ", isVetuste='" + isIsVetuste() + "'" +
            "}";
    }
}
