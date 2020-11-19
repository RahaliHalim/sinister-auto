package com.gaconnecte.auxilium.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Tarif.
 */
@Entity
@Table(name = "ref_age_vehicule")
public class RefAgeVehicule implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

 
    private String valeur;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getValeur() {
		return valeur;
	}

	public void setValeur(String valeur) {
		this.valeur = valeur;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RefAgeVehicule tarif = (RefAgeVehicule) o;
        if (tarif.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), tarif.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RefAGeVehicule{" +
            "id=" + getId() +
            ", valeur='" + getValeur() + "'" +
            "}";
    }
}
