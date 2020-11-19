package com.gaconnecte.auxilium.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the RefTarif entity.
 */
public class RefTarifDTO implements Serializable {

  
	private Long id;
	private Long lineId;

	@NotNull
    private Double montant;
	
	private String libelleTarif;
   
    public Long getId() {
        return id;
    }
	public void setId(Long id) {
        this.id = id;
    }

  	public Long getLineId() {
		return lineId;
	}
	public void setLineId(Long lineId) {
		this.lineId = lineId;
	}

  
    public Double getMontant() {
		return montant;
	}
	public void setMontant(Double montant) {
		this.montant = montant;
	}
	public String getLibelleTarif() {
		return libelleTarif;
	}
	public void setLibelleTarif(String libelleTarif) {
		this.libelleTarif = libelleTarif;
	}
	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RefTarifDTO tarifDTO = (RefTarifDTO) o;
        if(tarifDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), tarifDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TarifDTO{" +
            "id=" + getId() +
            ", montant='" + getMontant() + "'" +
            "}";
    }
}
