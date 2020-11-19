package com.gaconnecte.auxilium.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
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
 * A Fournisseur.
 */
@Entity
@Table(name = "fournisseur")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "fournisseur")
public class Fournisseur implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @DecimalMax(value = "99")
    @Column(name = "remise")
    private Float remise;

    @Column(name = "is_bloque")
    private Boolean isBloque;

    /**
     * Relations concernant le Fournisseur
     */
    @ApiModelProperty(value = "Relations concernant le Fournisseur")
    @OneToOne(optional = false)
    @NotNull
    @JoinColumn(unique = true)
    private PersonneMorale personneMorale;

   
  

    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getRemise() {
        return remise;
    }

    public Fournisseur remise(Float remise) {
        this.remise = remise;
        return this;
    }

    public void setRemise(Float remise) {
        this.remise = remise;
    }

    public Boolean isIsBloque() {
        return isBloque;
    }

    public Fournisseur isBloque(Boolean isBloque) {
        this.isBloque = isBloque;
        return this;
    }

    public void setIsBloque(Boolean isBloque) {
        this.isBloque = isBloque;
    }

    public PersonneMorale getPersonneMorale() {
        return personneMorale;
    }

    public Fournisseur personneMorale(PersonneMorale personneMorale) {
        this.personneMorale = personneMorale;
        return this;
    }

    public void setPersonneMorale(PersonneMorale personneMorale) {
        this.personneMorale = personneMorale;
    }

    

    

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Fournisseur fournisseur = (Fournisseur) o;
        if (fournisseur.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), fournisseur.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Fournisseur{" +
            "id=" + getId() +
            ", remise='" + getRemise() + "'" +
            ", isBloque='" + isIsBloque() + "'" +
            "}";
    }
}
