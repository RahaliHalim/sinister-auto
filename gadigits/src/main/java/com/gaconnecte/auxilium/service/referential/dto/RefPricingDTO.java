package com.gaconnecte.auxilium.service.referential.dto;


import java.io.Serializable;

/**
 * A DTO for the RefPricing entity.
 */
public class RefPricingDTO implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

    private String label;

    private Double priceUrbanPlanService;
    
    private Double priceKlmShortDistance;
    
    private Double priceKlmLongDistance;
    
    private Double priceExtraction;
    
    private Double priceReparation;
    
    private Double priceUrbanMobility;
    
    private Double priceIncrease;
    
    private Double priceUrbanPlanServicePl;
    
    private Double priceKlmShortDistancePl;
    
    private Double priceKlmLongDistancePl;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * @param label the label to set
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	/**
	 * @return the priceUrbanPlanService
	 */
	public Double getPriceUrbanPlanService() {
		return priceUrbanPlanService;
	}

	/**
	 * @param priceUrbanPlanService the priceUrbanPlanService to set
	 */
	public void setPriceUrbanPlanService(Double priceUrbanPlanService) {
		this.priceUrbanPlanService = priceUrbanPlanService;
	}

	/**
	 * @return the priceKlmShortDistance
	 */
	public Double getPriceKlmShortDistance() {
		return priceKlmShortDistance;
	}

	/**
	 * @param priceKlmShortDistance the priceKlmShortDistance to set
	 */
	public void setPriceKlmShortDistance(Double priceKlmShortDistance) {
		this.priceKlmShortDistance = priceKlmShortDistance;
	}

	/**
	 * @return the priceKlmLongDistance
	 */
	public Double getPriceKlmLongDistance() {
		return priceKlmLongDistance;
	}

	/**
	 * @param priceKlmLongDistance the priceKlmLongDistance to set
	 */
	public void setPriceKlmLongDistance(Double priceKlmLongDistance) {
		this.priceKlmLongDistance = priceKlmLongDistance;
	}

	/**
	 * @return the priceExtraction
	 */
	public Double getPriceExtraction() {
		return priceExtraction;
	}

	/**
	 * @param priceExtraction the priceExtraction to set
	 */
	public void setPriceExtraction(Double priceExtraction) {
		this.priceExtraction = priceExtraction;
	}

	/**
	 * @return the priceReparation
	 */
	public Double getPriceReparation() {
		return priceReparation;
	}

	/**
	 * @param priceReparation the priceReparation to set
	 */
	public void setPriceReparation(Double priceReparation) {
		this.priceReparation = priceReparation;
	}

	/**
	 * @return the priceUrbanMobility
	 */
	public Double getPriceUrbanMobility() {
		return priceUrbanMobility;
	}

	/**
	 * @param priceUrbanMobility the priceUrbanMobility to set
	 */
	public void setPriceUrbanMobility(Double priceUrbanMobility) {
		this.priceUrbanMobility = priceUrbanMobility;
	}

	/**
	 * @return the priceIncrease
	 */
	public Double getPriceIncrease() {
		return priceIncrease;
	}

	/**
	 * @param priceIncrease the priceIncrease to set
	 */
	public void setPriceIncrease(Double priceIncrease) {
		this.priceIncrease = priceIncrease;
	}

	/**
	 * @return the priceUrbanPlanServicePl
	 */
	public Double getPriceUrbanPlanServicePl() {
		return priceUrbanPlanServicePl;
	}

	/**
	 * @param priceUrbanPlanServicePl the priceUrbanPlanServicePl to set
	 */
	public void setPriceUrbanPlanServicePl(Double priceUrbanPlanServicePl) {
		this.priceUrbanPlanServicePl = priceUrbanPlanServicePl;
	}

	/**
	 * @return the priceKlmShortDistancePl
	 */
	public Double getPriceKlmShortDistancePl() {
		return priceKlmShortDistancePl;
	}

	/**
	 * @param priceKlmShortDistancePl the priceKlmShortDistancePl to set
	 */
	public void setPriceKlmShortDistancePl(Double priceKlmShortDistancePl) {
		this.priceKlmShortDistancePl = priceKlmShortDistancePl;
	}

	/**
	 * @return the priceKlmLongDistancePl
	 */
	public Double getPriceKlmLongDistancePl() {
		return priceKlmLongDistancePl;
	}

	/**
	 * @param priceKlmLongDistancePl the priceKlmLongDistancePl to set
	 */
	public void setPriceKlmLongDistancePl(Double priceKlmLongDistancePl) {
		this.priceKlmLongDistancePl = priceKlmLongDistancePl;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RefPricingDTO other = (RefPricingDTO) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "RefPricingDTO [id=" + id + ", label=" + label + ", priceUrbanPlanService=" + priceUrbanPlanService
				+ ", priceKlmShortDistance=" + priceKlmShortDistance + ", priceKlmLongDistance=" + priceKlmLongDistance
				+ ", priceExtraction=" + priceExtraction + ", priceReparation=" + priceReparation
				+ ", priceUrbanMobility=" + priceUrbanMobility + ", priceIncrease=" + priceIncrease
				+ ", priceUrbanPlanServicePl=" + priceUrbanPlanServicePl + ", priceKlmShortDistancePl="
				+ priceKlmShortDistancePl + ", priceKlmLongDistancePl=" + priceKlmLongDistancePl + "]";
	}

    
}
