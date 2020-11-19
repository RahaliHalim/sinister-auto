package com.gaconnecte.auxilium.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the RefTva entity.
 */
public class RefAgeVehiculeDTO implements Serializable {

    private Long id;

   

    @NotNull
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

        RefAgeVehiculeDTO refAgeVehiculeDTO = (RefAgeVehiculeDTO) o;
        if(refAgeVehiculeDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), refAgeVehiculeDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RefAgeVehiculeDTO{" +
            "id=" + getId() +
            ", valeur='" + getValeur() + "'" +
            "}";
    }
}
