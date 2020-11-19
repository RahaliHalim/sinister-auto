package com.gaconnecte.auxilium.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

public class QuotationMPDTO implements Serializable {

	private Long id;
	private LocalDateTime creationDate;
	private Float stampDuty;
	private Double ttcAmount;
	private Double htAmount;
	private Boolean repairableVehicle;
	private Boolean concordanceReport;
	private Boolean preliminaryReport;
	private Boolean immobilizedVehicle;
	private String conditionVehicle;
	private Double kilometer;
	private Double priceNewVehicle;
	private Double marketValue;
	private Double totalMo;
	private Double heureMO;
	private Double estimateJour;
	private LocalDate expertiseDate;
	private String comment;
	private Long sinisterPecId;
	private Long statusId;
	private Boolean confirmationDecisionQuote;
	private LocalDate confirmationDecisionQuoteDate;
	private List<DetailsPiecesDTO> listPieces = new ArrayList<>();
	private Long originalQuotationId;
	private String expertDecision;
	private LocalDateTime avisExpertDate;
	private LocalDateTime revueDate;
	private LocalDateTime verificationDevisValidationDate;
	private LocalDateTime verificationDevisRectificationDate;
	private LocalDateTime confirmationDevisDate;
	private LocalDateTime miseAJourDevisDate;
	private LocalDateTime confirmationDevisComplementaireDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getCreationDate() {
		return creationDate;
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

	public void setHtAmount(Double htAmount) {
		this.htAmount = htAmount;
	}

	public Boolean getRepairableVehicle() {
		return repairableVehicle;
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

	public Boolean getImmobilizedVehicle() {
		return immobilizedVehicle;
	}

	public void setImmobilizedVehicle(Boolean immobilizedVehicle) {
		this.immobilizedVehicle = immobilizedVehicle;
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

	public Double getEstimateJour() {
		return estimateJour;
	}

	public void setEstimateJour(Double estimateJour) {
		this.estimateJour = estimateJour;
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

	public Long getSinisterPecId() {
		return sinisterPecId;
	}

	public void setSinisterPecId(Long sinisterPecId) {
		this.sinisterPecId = sinisterPecId;
	}

	public Long getStatusId() {
		return statusId;
	}

	public void setStatusId(Long statusId) {
		this.statusId = statusId;
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

	public List<DetailsPiecesDTO> getListPieces() {
		return listPieces;
	}

	public void setListPieces(List<DetailsPiecesDTO> listPieces) {
		this.listPieces = listPieces;
	}

	public Long getOriginalQuotationId() {
		return originalQuotationId;
	}

	public void setOriginalQuotationId(Long originalQuotationId) {
		this.originalQuotationId = originalQuotationId;
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