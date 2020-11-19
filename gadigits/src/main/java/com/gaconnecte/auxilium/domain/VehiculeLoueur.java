package com.gaconnecte.auxilium.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import com.fasterxml.jackson.annotation.JsonBackReference;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.Objects;

/**
 * A VehiculeLoueur.
 */
@Entity
@Table(name = "vehicule_loueur")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "vehiculeloueur")
public class VehiculeLoueur implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "immatriculation")
    private String immatriculation;

    @Column(name = "total_prix")
    private Double totalPrix;
    
    @Column(name = "prix_jour")
    private Double prixJour;
    
    @ManyToOne
    @JoinColumn(name = "marque_id")
    private VehicleBrand marque;
    
    @ManyToOne
    @JoinColumn(name = "modele_id")
    private VehicleBrandModel modele;
    
    @ManyToOne(optional = false)
    @JoinColumn(name = "loueur_id")
    @JsonBackReference(value = "loueur")
    private Loueur loueur;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImmatriculation() {
        return immatriculation;
    }

    public VehiculeLoueur immatriculation(String immatriculation) {
        this.immatriculation = immatriculation;
        return this;
    }

    public void setImmatriculation(String immatriculation) {
        this.immatriculation = immatriculation;
    }

    public Double getTotalPrix() {
        return totalPrix;
    }

    public VehiculeLoueur totalPrix(Double totalPrix) {
        this.totalPrix = totalPrix;
        return this;
    }

    public void setTotalPrix(Double totalPrix) {
        this.totalPrix = totalPrix;
    }

    public VehicleBrand getMarque() {
		return marque;
	}

	public void setMarque(VehicleBrand marque) {
		this.marque = marque;
	}

	public VehicleBrandModel getModele() {
		return modele;
	}

	public void setModele(VehicleBrandModel modele) {
		this.modele = modele;
	}

	public Loueur getLoueur() {
		return loueur;
	}

	public void setLoueur(Loueur loueur) {
		this.loueur = loueur;
	}

	public Double getPrixJour() {
		return prixJour;
	}

	public void setPrixJour(Double prixJour) {
		this.prixJour = prixJour;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        VehiculeLoueur vehiculeLoueur = (VehiculeLoueur) o;
        if (vehiculeLoueur.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), vehiculeLoueur.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "VehiculeLoueur{" +
            "id=" + getId() +
            ", immatriculation='" + getImmatriculation() + "'" +
            ", totalPrix='" + getTotalPrix() + "'" +
            "}";
    }
}
