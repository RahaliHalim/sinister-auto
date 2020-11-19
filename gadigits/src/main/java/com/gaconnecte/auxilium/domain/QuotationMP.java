package com.gaconnecte.auxilium.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import java.time.ZonedDateTime;
import org.hibernate.annotations.DiscriminatorOptions;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import com.fasterxml.jackson.annotation.JsonBackReference;

/**
 * A Devis.
 */
@Entity
@Table(name = "quotation_mp")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "quotationMp")

public class QuotationMP implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "creation_date")
	private LocalDateTime creationDate;
	@Column(name = "stamp_duty")
	private Float stampDuty;
	@Column(name = "ttc_amount")
	private Double ttcAmount;
	@Column(name = "ht_amount")
	private Double htAmount;
	@Column(name = "repairable_vehicle")
	private Boolean repairableVehicle;
	@Column(name = "concordance_report")
	private Boolean concordanceReport;
	@Column(name = "preliminary_report")
	private Boolean preliminaryReport;
	@Column(name = "immobilized_vehicle")
	private Boolean immobilizedVehicle;
	@Column(name = "condition_vehicle")
	private String conditionVehicle;

	@Column(name = "kilometer")
	private Double kilometer;

	@Column(name = "total_mo")
	private Double totalMo;

	@Column(name = "heure_mo")
	private Double heureMO;

	@Column(name = "estimate_jour")
	private Double estimateJour;

	@Column(name = "price_new_vehicle")
	private Double priceNewVehicle;
	@Column(name = "market_value")
	private Double marketValue;
	@Column(name = "expertise_date")
	private LocalDate expertiseDate;

	@Column(name = "confirmation_decision_quote")
	private Boolean confirmationDecisionQuote;
	@Column(name = "confirmation_decision_quote_date")
	private LocalDate confirmationDecisionQuoteDate;

	@Size(max = 2000)
	@Column(name = "comment", length = 2000)
	private String comment;

	@Column(name = "prestation_id")
	private Long sinisterPecId;

	@ManyToOne
	@JoinColumn(name = "status")
	private QuotationStatus status;
	
	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "quotationMP", orphanRemoval = true)
	private List<DetailsPieces> listPieces = new ArrayList<>();
	
	@Column(name = "original_quotation_id")
	private Long originalQuotationId;

	@Column(name = "expert_decision")
	private String expertDecision ;

	@Column(name = "avis_expert_date")
	private LocalDateTime avisExpertDate;

	@Column(name = "revue_date")
	private LocalDateTime revueDate;
	
	@Column(name = "date_verification_devis_validation")
	private LocalDateTime verificationDevisValidationDate;
	@Column(name = "date_verification_devis_rectification")
	private LocalDateTime verificationDevisRectificationDate;
	@Column(name = "date_confirmation_devis")
	private LocalDateTime confirmationDevisDate;
	@Column(name = "date_mise_a_jour_devis")
	private LocalDateTime miseAJourDevisDate;
	@Column(name = "date_confirmation_devis_complementaire")
	private LocalDateTime confirmationDevisComplementaireDate;

	public QuotationMP() {

	}

	public QuotationMP(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getExpertDecision() {
		return expertDecision;
	}

	public void setExpertDecision(String expertDecision) {
		this.expertDecision = expertDecision;
	}

	public LocalDateTime getAvisExpertDate() {
		return avisExpertDate;
	}

	public void setAvisExpertDate(LocalDateTime avisExpertDate) {
		this.avisExpertDate = avisExpertDate;
	}

	public LocalDateTime getRevueDate() {
		return revueDate;
	}

	public void setRevueDate(LocalDateTime revueDate) {
		this.revueDate = revueDate;
	}

	public LocalDateTime getCreationDate() {
		return creationDate;
	}

	public Double getTotalMo() {
		return totalMo;
	}

	public void setTotalMo(Double totalMo) {
		this.totalMo = totalMo;
	}

	public Double getHeureMO() {
		return heureMO;
	}

	public void setHeureMO(Double heureMO) {
		this.heureMO = heureMO;
	}

	public void setCreationDate(LocalDateTime creationDate) {
		this.creationDate = creationDate;
	}

	public Float getStampDuty() {
		return stampDuty;
	}

	public void setStampDuty(Float stampDuty) {
		this.stampDuty = stampDuty;
	}

	public Double getTtcAmount() {
		return ttcAmount;
	}

	public void setTtcAmount(Double ttcAmount) {
		this.ttcAmount = ttcAmount;
	}

	public Double getHtAmount() {
		return htAmount;
	}

	public Boolean getImmobilizedVehicle() {
		return immobilizedVehicle;
	}

	public void setImmobilizedVehicle(Boolean immobilizedVehicle) {
		this.immobilizedVehicle = immobilizedVehicle;
	}

	public void setHtAmount(Double htAmount) {
		this.htAmount = htAmount;
	}

	public Boolean getRepairableVehicle() {
		return repairableVehicle;
	}

	public Double getEstimateJour() {
		return estimateJour;
	}

	public void setEstimateJour(Double estimateJour) {
		this.estimateJour = estimateJour;
	}

	public void setRepairableVehicle(Boolean repairableVehicle) {
		this.repairableVehicle = repairableVehicle;
	}

	public Boolean getConcordanceReport() {
		return concordanceReport;
	}

	public void setConcordanceReport(Boolean concordanceReport) {
		this.concordanceReport = concordanceReport;
	}

	public Boolean getPreliminaryReport() {
		return preliminaryReport;
	}

	public void setPreliminaryReport(Boolean preliminaryReport) {
		this.preliminaryReport = preliminaryReport;
	}

	public String getConditionVehicle() {
		return conditionVehicle;
	}

	public void setConditionVehicle(String conditionVehicle) {
		this.conditionVehicle = conditionVehicle;
	}

	public Double getKilometer() {
		return kilometer;
	}

	public void setKilometer(Double kilometer) {
		this.kilometer = kilometer;
	}

	public Double getPriceNewVehicle() {
		return priceNewVehicle;
	}

	public void setPriceNewVehicle(Double priceNewVehicle) {
		this.priceNewVehicle = priceNewVehicle;
	}

	public Double getMarketValue() {
		return marketValue;
	}

	public void setMarketValue(Double marketValue) {
		this.marketValue = marketValue;
	}

	public LocalDate getExpertiseDate() {
		return expertiseDate;
	}

	public void setExpertiseDate(LocalDate expertiseDate) {
		this.expertiseDate = expertiseDate;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public List<DetailsPieces> getListPieces() {
		return listPieces;
	}

	public void setListPieces(List<DetailsPieces> listPieces) {
		this.listPieces = listPieces;
	}

	public QuotationStatus getStatus() {
		return status;
	}

	public void setStatus(QuotationStatus status) {
		this.status = status;
	}

	public Boolean getConfirmationDecisionQuote() {
		return confirmationDecisionQuote;
	}

	public void setConfirmationDecisionQuote(Boolean confirmationDecisionQuote) {
		this.confirmationDecisionQuote = confirmationDecisionQuote;
	}

	public LocalDate getConfirmationDecisionQuoteDate() {
		return confirmationDecisionQuoteDate;
	}

	public void setConfirmationDecisionQuoteDate(LocalDate confirmationDecisionQuoteDate) {
		this.confirmationDecisionQuoteDate = confirmationDecisionQuoteDate;
	}

	public Long getOriginalQuotationId() {
		return originalQuotationId;
	}

	public void setOriginalQuotationId(Long originalQuotationId) {
		this.originalQuotationId = originalQuotationId;
	}

	public Long getSinisterPecId() {
		return sinisterPecId;
	}

	public void setSinisterPecId(Long sinisterPecId) {
		this.sinisterPecId = sinisterPecId;
	}

	public LocalDateTime getVerificationDevisValidationDate() {
		return verificationDevisValidationDate;
	}

	public void setVerificationDevisValidationDate(LocalDateTime verificationDevisValidationDate) {
		this.verificationDevisValidationDate = verificationDevisValidationDate;
	}

	public LocalDateTime getVerificationDevisRectificationDate() {
		return verificationDevisRectificationDate;
	}

	public void setVerificationDevisRectificationDate(LocalDateTime verificationDevisRectificationDate) {
		this.verificationDevisRectificationDate = verificationDevisRectificationDate;
	}

	public LocalDateTime getConfirmationDevisDate() {
		return confirmationDevisDate;
	}

	public void setConfirmationDevisDate(LocalDateTime confirmationDevisDate) {
		this.confirmationDevisDate = confirmationDevisDate;
	}

	public LocalDateTime getMiseAJourDevisDate() {
		return miseAJourDevisDate;
	}

	public void setMiseAJourDevisDate(LocalDateTime miseAJourDevisDate) {
		this.miseAJourDevisDate = miseAJourDevisDate;
	}

	public LocalDateTime getConfirmationDevisComplementaireDate() {
		return confirmationDevisComplementaireDate;
	}

	public void setConfirmationDevisComplementaireDate(LocalDateTime confirmationDevisComplementaireDate) {
		this.confirmationDevisComplementaireDate = confirmationDevisComplementaireDate;
	}

}