package com.gaconnecte.auxilium.service.dto;


import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.validation.constraints.*;



import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the RefTarif entity.
 */
public class TugPricingDTO implements Serializable {
	
        private Long id;
	@NotNull
	    private Long tugId;
	@NotNull
	    private Double priceUrbanPlanService;
	@NotNull
	    private Double priceKlmShortDistance;
	@NotNull
	    private Double priceKlmLongDistance;
	@NotNull
	    private Double priceExtraction;
	@NotNull
	    private Double priceReparation;
	@NotNull
	    private Double priceUrbanMobility;
	@NotNull
	    private Double priceUrbanPlanServicePl;
	@NotNull
	    private Double priceKlmShortDistancePl;
	@NotNull
	    private Double priceKlmLongDistancePl;
	
	    private  LocalDate dateEffectiveAgreement;
	
	    private  LocalDate dateEndAgreement;
	   
    public Long getId() {
        return id;
    }
	public void setId(Long id) {
        this.id = id;
    }
	public Long getTugId() {
		return tugId;
	}
	public void setTugId(Long tugId) {
		this.tugId = tugId;
	}
	public Double getPriceUrbanPlanService() {
		return priceUrbanPlanService;
	}
	public void setPriceUrbanPlanService(Double priceUrbanPlanService) {
		this.priceUrbanPlanService = priceUrbanPlanService;
	}
	public Double getPriceKlmShortDistance() {
		return priceKlmShortDistance;
	}
	public void setPriceKlmShortDistance(Double priceKlmShortDistance) {
		this.priceKlmShortDistance = priceKlmShortDistance;
	}
	public Double getPriceKlmLongDistance() {
		return priceKlmLongDistance;
	}
	public void setPriceKlmLongDistance(Double priceKlmLongDistance) {
		this.priceKlmLongDistance = priceKlmLongDistance;
	}
	public Double getPriceExtraction() {
		return priceExtraction;
	}
	public void setPriceExtraction(Double priceExtraction) {
		this.priceExtraction = priceExtraction;
	}
	public Double getPriceReparation() {
		return priceReparation;
	}
	public void setPriceReparation(Double priceReparation) {
		this.priceReparation = priceReparation;
	}
	public Double getPriceUrbanMobility() {
		return priceUrbanMobility;
	}
	public void setPriceUrbanMobility(Double priceUrbanMobility) {
		this.priceUrbanMobility = priceUrbanMobility;
	}
	public Double getPriceUrbanPlanServicePl() {
		return priceUrbanPlanServicePl;
	}
	public void setPriceUrbanPlanServicePl(Double priceUrbanPlanServicePl) {
		this.priceUrbanPlanServicePl = priceUrbanPlanServicePl;
	}
	public Double getPriceKlmShortDistancePl() {
		return priceKlmShortDistancePl;
	}
	public void setPriceKlmShortDistancePl(Double priceKlmShortDistancePl) {
		this.priceKlmShortDistancePl = priceKlmShortDistancePl;
	}
	public Double getPriceKlmLongDistancePl() {
		return priceKlmLongDistancePl;
	}
	public void setPriceKlmLongDistancePl(Double priceKlmLongDistancePl) {
		this.priceKlmLongDistancePl = priceKlmLongDistancePl;
	}
	public LocalDate getDateEffectiveAgreement() {
		return dateEffectiveAgreement;
	}
	public void setDateEffectiveAgreement(LocalDate dateEffectiveAgreement) {
		this.dateEffectiveAgreement = dateEffectiveAgreement;
	}
	public LocalDate getDateEndAgreement() {
		return dateEndAgreement;
	}
	public void setDateEndAgreement(LocalDate dateEndAgreement) {
		this.dateEndAgreement = dateEndAgreement;
	}
	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TugPricingDTO tarifDTO = (TugPricingDTO) o;
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
		return "TugPricingDTO [priceUrbanPlanService=" + priceUrbanPlanService + ", priceKlmShortDistance="
				+ priceKlmShortDistance + ", priceKlmLongDistance=" + priceKlmLongDistance + ", priceExtraction="
				+ priceExtraction + ", priceReparation=" + priceReparation + ", priceUrbanMobility="
				+ priceUrbanMobility + ", priceUrbanPlanServicePl=" + priceUrbanPlanServicePl
				+ ", priceKlmShortDistancePl=" + priceKlmShortDistancePl + ", priceKlmLongDistancePl="
				+ priceKlmLongDistancePl + ", dateEffectiveAgreement=" + dateEffectiveAgreement + ", dateEndAgreement="
				+ dateEndAgreement + "]";
	}
}
