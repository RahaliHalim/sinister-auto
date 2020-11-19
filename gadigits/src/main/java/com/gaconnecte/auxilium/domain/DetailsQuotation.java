package com.gaconnecte.auxilium.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

@Entity
@Table(name = "details_quotation")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "details_quotation")
public class DetailsQuotation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "sinister_pec_id")
    private Long sinisterPecId;

    @Column(name = "price_new_vehicle")
    private Double priceNewVehicle;

    @Column(name = "market_value")
    private Double marketValue;

    @Column(name = "kilometer")
    private Double kilometer;

    @Column(name = "estimate_jour")
    private Double estimateJour;

    @Column(name = "heure_mo")
    private Double heureMO;

    @Column(name = "total_mo")
    private Double totalMo;

    @Column(name = "condition_vehicle")
    private String conditionVehicle;

    @Column(name = "repairable_vehicle")
    private Boolean repairableVehicle;

    @Column(name = "preliminary_report")
    private Boolean preliminaryReport;

    @Column(name = "immobilized_vehicle")
    private Boolean immobilizedVehicle;

    @Column(name = "concordance_report")
    private Boolean concordanceReport;

    @Column(name = "expertise_date")
    private LocalDateTime expertiseDate;

    @Column(name = "expert_decision")
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

    public String getExpertDecision() {
        return expertDecision;
    }

    public void setExpertDecision(String expertDecision) {
        this.expertDecision = expertDecision;
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

}
