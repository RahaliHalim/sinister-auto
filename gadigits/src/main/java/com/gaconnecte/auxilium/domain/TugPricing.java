package com.gaconnecte.auxilium.domain;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import org.springframework.data.elasticsearch.annotations.Document;
@Entity
@Table(name = "tug_pricing")
@Document(indexName = "tugpricing")
  

public class TugPricing implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
	@SequenceGenerator(name = "sequenceGenerator")
	private Long id;

	@OneToOne(optional = false)
	@NotNull
	@JoinColumn(unique = true)
	private RefRemorqueur tug;

	@Column(name = "price_urban_plan_service")
	private Double priceUrbanPlanService;
	@NotNull
	@Column(name = "price_klm_short_distance")
	private Double priceKlmShortDistance;
	@NotNull
	@Column(name = "price_klm_long_distance")
	private Double priceKlmLongDistance;
	@NotNull
	@Column(name = "price_extraction")
	private Double priceExtraction;
	@NotNull
	@Column(name = "price_reparation")
	private Double priceReparation;
	@NotNull
	@Column(name = "price_urban_mobility")
	private Double priceUrbanMobility;
	@NotNull
	@Column(name = "price_urban_plan_service_pl")
	private Double priceUrbanPlanServicePl;
	@NotNull
	@Column(name = "price_klm_short_distance_pl")
	private Double priceKlmShortDistancePl;
	@NotNull
	@Column(name = "price_klm_long_distance_pl")
	private Double priceKlmLongDistancePl;
	@Column(name = "date_effective_agreement")
	private LocalDate dateEffectiveAgreement;


	@Column(name = "date_end_agreement")
	private LocalDate dateEndAgreement;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public RefRemorqueur getTug() {
		return tug;
	}

	public TugPricing tug(RefRemorqueur tug) {
		this.tug = tug;
		return this;
	}

	public void setTug(RefRemorqueur tug) {
		this.tug = tug;
	}

	public Double getPriceUrbanPlanService() {
		return priceUrbanPlanService;
	}

	public TugPricing priceUrbanPlanService(Double priceUrbanPlanService) {
		this.priceUrbanPlanService = priceUrbanPlanService;
		return this;
	}

	public void setPriceUrbanPlanService(Double priceUrbanPlanService) {
		this.priceUrbanPlanService = priceUrbanPlanService;
	}

	public Double getPriceKlmShortDistance() {
		return priceKlmShortDistance;
	}

	public TugPricing priceKlmShortDistance(Double priceKlmShortDistance) {
		this.priceKlmShortDistance = priceKlmShortDistance;
		return this;
	}

	public void setPriceKlmShortDistance(Double priceKlmShortDistance) {
		this.priceKlmShortDistance = priceKlmShortDistance;
	}

	public Double getPriceKlmLongDistance() {
		return priceKlmLongDistance;
	}

	public TugPricing priceKlmLongDistance(Double priceKlmLongDistance) {
		this.priceKlmLongDistance = priceKlmLongDistance;
		return this;
	}

	public void setPriceKlmLongDistance(Double priceKlmLongDistance) {
		this.priceKlmLongDistance = priceKlmLongDistance;
	}

	public Double getPriceExtraction() {
		return priceExtraction;
	}

	public TugPricing priceExtraction(Double priceExtraction) {
		this.priceExtraction = priceExtraction;
		return this;
	}

	public void setPriceExtraction(Double priceExtraction) {
		this.priceExtraction = priceExtraction;
	}

	public Double getPriceReparation() {
		return priceReparation;
	}

	public TugPricing priceReparation(Double priceReparation) {
		this.priceReparation = priceReparation;
		return this;
	}

	public void setPriceReparation(Double priceReparation) {
		this.priceReparation = priceReparation;
	}

	public Double getPriceUrbanMobility() {
		return priceUrbanMobility;
	}

	public TugPricing priceUrbanMobility(Double priceUrbanMobility) {
		this.priceUrbanMobility = priceUrbanMobility;
		return this;
	}

	public void setPriceUrbanMobility(Double priceUrbanMobility) {
		this.priceUrbanMobility = priceUrbanMobility;
	}

	public Double getPriceUrbanPlanServicePl() {
		return priceUrbanPlanServicePl;
	}

	public TugPricing priceUrbanPlanServicePl(Double priceUrbanPlanServicePl) {
		this.priceUrbanPlanServicePl = priceUrbanPlanServicePl;
		return this;
	}

	public void setPriceUrbanPlanServicePl(Double priceUrbanPlanServicePl) {
		this.priceUrbanPlanServicePl = priceUrbanPlanServicePl;
	}

	public Double getPriceKlmLongDistancePl() {
		return priceKlmLongDistancePl;
	}

	public TugPricing priceKlmLongDistancePl(Double priceKlmLongDistancePl) {
		this.priceKlmLongDistancePl = priceKlmLongDistancePl;
		return this;
	}

	public void setPriceKlmLongDistancePl(Double priceKlmLongDistancePl) {
		this.priceKlmLongDistancePl = priceKlmLongDistancePl;
	}

	public LocalDate getDateEffectiveAgreement() {
		return dateEffectiveAgreement;
	}

	public TugPricing dateEffectiveAgreement(LocalDate dateEffectiveAgreement) {
		this.dateEffectiveAgreement = dateEffectiveAgreement;
		return this;
	}

	public void setDateEffectiveAgreement(LocalDate dateEffectiveAgreement) {
		this.dateEffectiveAgreement = dateEffectiveAgreement;
	}

	public LocalDate getDateEndAgreement() {
		return dateEndAgreement;
	}

	public TugPricing dateEndAgreement(LocalDate dateEndAgreement) {
		this.dateEndAgreement = dateEndAgreement;
		return this;
	}

	public void setDateEndAgreement(LocalDate dateEndAgreement) {
		this.dateEndAgreement = dateEndAgreement;
	}

	public Double getPriceKlmShortDistancePl() {
		return priceKlmShortDistancePl;
	}

	public TugPricing priceKlmShortDistancePl(Double priceKlmShortDistancePl) {
		this.priceKlmShortDistancePl = priceKlmShortDistancePl;
		return this;
	}

	public void setPriceKlmShortDistancePl(Double priceKlmShortDistancePl) {
		this.priceKlmShortDistancePl = priceKlmShortDistancePl;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(getId());
	}

	
	public TugPricing() {
		super();
	}

	public TugPricing(Long id, RefRemorqueur tug, Double priceUrbanPlanService, Double priceKlmShortDistance,
			Double priceKlmLongDistance, Double priceExtraction, Double priceReparation, Double priceUrbanMobility,
			Double priceUrbanPlanServicePl, Double priceKlmShortDistancePl, Double priceKlmLongDistancePl,
			LocalDate dateEffectiveAgreement, LocalDate dateEndAgreement) {
		super();
		this.id = id;
		this.tug = tug;
		this.priceUrbanPlanService = priceUrbanPlanService;
		this.priceKlmShortDistance = priceKlmShortDistance;
		this.priceKlmLongDistance = priceKlmLongDistance;
		this.priceExtraction = priceExtraction;
		this.priceReparation = priceReparation;
		this.priceUrbanMobility = priceUrbanMobility;
		this.priceUrbanPlanServicePl = priceUrbanPlanServicePl;
		this.priceKlmShortDistancePl = priceKlmShortDistancePl;
		this.priceKlmLongDistancePl = priceKlmLongDistancePl;
		this.dateEffectiveAgreement = dateEffectiveAgreement;
		this.dateEndAgreement = dateEndAgreement;
	}

	@Override
	public String toString() {
		return "TugPricing [id=" + id + ", tug=" + tug + ", priceUrbanPlanService=" + priceUrbanPlanService
				+ ", priceKlmShortDistance=" + priceKlmShortDistance + ", priceKlmLongDistance=" + priceKlmLongDistance
				+ ", priceExtraction=" + priceExtraction + ", priceReparation=" + priceReparation
				+ ", priceUrbanMobility=" + priceUrbanMobility + ", priceUrbanPlanServicePl=" + priceUrbanPlanServicePl
				+ ", price_klm_short_distance_pl=" + priceKlmShortDistancePl + ", priceKlmLongDistancePl="
				+ priceKlmLongDistancePl + ", dateEffectiveAgreement=" + dateEffectiveAgreement + ", dateEndAgreement="
				+ dateEndAgreement + "]";
	}

}