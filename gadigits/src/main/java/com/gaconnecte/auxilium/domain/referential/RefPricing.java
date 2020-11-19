package com.gaconnecte.auxilium.domain.referential;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A referential for pricing.
 */
@Entity
@Table(name = "ref_pricing")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class RefPricing implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "label", length = 100, nullable = false)
    private String label;

    @Column(name = "price_urban_plan_service", nullable = false)	
    private Double priceUrbanPlanService;
    
    @Column(name = "price_klm_short_distance", nullable = false)
    private Double priceKlmShortDistance;
    
    @Column(name = "price_klm_long_distance", nullable = false)
    private Double priceKlmLongDistance;
    
    @Column(name = "price_extraction", nullable = false)
    private Double priceExtraction;
    
    @Column(name = "price_reparation", nullable = false)
    private Double priceReparation;
    
    @Column(name = "price_urban_mobility", nullable = false)
    private Double priceUrbanMobility;
    
    @Column(name = "price_increase", nullable = false)
    private Double priceIncrease;
    
    @Column(name = "price_urban_plan_service_pl", nullable = false)
    private Double priceUrbanPlanServicePl;
    
    @Column(name = "price_klm_short_distance_pl", nullable = false)
    private Double priceKlmShortDistancePl;
    
    @Column(name = "price_klm_long_distance_pl", nullable = false)
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
		RefPricing other = (RefPricing) obj;
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
		return "RefPricing [id=" + id + ", label=" + label + ", priceUrbanPlanService=" + priceUrbanPlanService
				+ ", priceKlmShortDistance=" + priceKlmShortDistance + ", priceKlmLongDistance=" + priceKlmLongDistance
				+ ", priceExtraction=" + priceExtraction + ", priceReparation=" + priceReparation
				+ ", priceUrbanMobility=" + priceUrbanMobility + ", priceIncrease=" + priceIncrease
				+ ", priceUrbanPlanServicePl=" + priceUrbanPlanServicePl + ", priceKlmShortDistancePl="
				+ priceKlmShortDistancePl + ", priceKlmLongDistancePl=" + priceKlmLongDistancePl + "]";
	}

    
}
