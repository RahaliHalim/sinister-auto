package com.gaconnecte.auxilium.service.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

public class DetailsQuotationDTO implements Serializable {

    private Long id;

    private Long sinisterPecId;

    private Double priceNewVehicle;

    private Double marketValue;

    private Double kilometer;

    private Double estimateJour;

    private Double heureMO;

    private Double totalMo;

    private String conditionVehicle;

    private Boolean repairableVehicle;

    private Boolean preliminaryReport;

    private Boolean immobilizedVehicle;

    private Boolean concordanceReport;

    private LocalDateTime expertiseDate;

    private String expertDecision;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSinisterPecId() {
        return sinisterPecId;
    }

    public void setSinisterPecId(Long sinisterPecId) {
        this.sinisterPecId = sinisterPecId;
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

    public Double getKilometer() {
        return kilometer;
    }

    public void setKilometer(Double kilometer) {
        this.kilometer = kilometer;
    }

    public Double getEstimateJour() {
        return estimateJour;
    }

    public void setEstimateJour(Double estimateJour) {
        this.estimateJour = estimateJour;
    }

    public Double getHeureMO() {
        return heureMO;
    }

    public void setHeureMO(Double heureMO) {
        this.heureMO = heureMO;
    }

    public Double getTotalMo() {
        return totalMo;
    }

    public void setTotalMo(Double totalMo) {
        this.totalMo = totalMo;
    }

    public String getConditionVehicle() {
        return conditionVehicle;
    }

    public void setConditionVehicle(String conditionVehicle) {
        this.conditionVehicle = conditionVehicle;
    }

    public Boolean getRepairableVehicle() {
        return repairableVehicle;
    }

    public void setRepairableVehicle(Boolean repairableVehicle) {
        this.repairableVehicle = repairableVehicle;
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

    public Boolean getConcordanceReport() {
        return concordanceReport;
    }

    public void setConcordanceReport(Boolean concordanceReport) {
        this.concordanceReport = concordanceReport;
    }

    public LocalDateTime getExpertiseDate() {
        return expertiseDate;
    }

    public void setExpertiseDate(LocalDateTime expertiseDate) {
        this.expertiseDate = expertiseDate;
    }

    public String getExpertDecision() {
        return expertDecision;
    }

    public void setExpertDecision(String expertDecision) {
        this.expertDecision = expertDecision;
    }

}
