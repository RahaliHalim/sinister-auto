package com.gaconnecte.auxilium.service.dto;


import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the VehiculeLoueur entity.
 */
public class VehiculeLoueurDTO implements Serializable {

    private Long id;

    private String immatriculation;

    private Double totalPrix;
    private Double prixJour;

    
    private Long marqueId;
    private String marqueLabel;

    private Long modeleId;
    private String modeleLabel;
    
    private Long loueurId;
    private String loueurLabel;



    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImmatriculation() {
        return immatriculation;
    }

    public void setImmatriculation(String immatriculation) {
        this.immatriculation = immatriculation;
    }

    public Double getTotalPrix() {
        return totalPrix;
    }

    public void setTotalPrix(Double totalPrix) {
        this.totalPrix = totalPrix;
    }

    
      
    public Long getMarqueId() {
		return marqueId;
	}

	public void setMarqueId(Long marqueId) {
		this.marqueId = marqueId;
	}

	public Long getModeleId() {
		return modeleId;
	}

	public void setModeleId(Long modeleId) {
		this.modeleId = modeleId;
	}

	public String getMarqueLabel() {
		return marqueLabel;
	}

	public void setMarqueLabel(String marqueLabel) {
		this.marqueLabel = marqueLabel;
	}

	public String getModeleLabel() {
		return modeleLabel;
	}

	public void setModeleLabel(String modeleLabel) {
		this.modeleLabel = modeleLabel;
	}
	
	

	public Long getLoueurId() {
		return loueurId;
	}

	public void setLoueurId(Long loueurId) {
		this.loueurId = loueurId;
	}

	public String getLoueurLabel() {
		return loueurLabel;
	}

	public void setLoueurLabel(String loueurLabel) {
		this.loueurLabel = loueurLabel;
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

        VehiculeLoueurDTO vehiculeLoueurDTO = (VehiculeLoueurDTO) o;
        if(vehiculeLoueurDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), vehiculeLoueurDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "VehiculeLoueurDTO{" +
            "id=" + getId() +
            ", immatriculation='" + getImmatriculation() + "'" +
            ", totalPrix='" + getTotalPrix() + "'" +
            "}";
    }
}
