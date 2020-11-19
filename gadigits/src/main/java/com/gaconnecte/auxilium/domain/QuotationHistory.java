package com.gaconnecte.auxilium.domain;

import java.io.Serializable;
import java.time.ZonedDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "quotation_history")
public class QuotationHistory implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;
	@NotNull
    @Column(name = "date_modification", nullable = false)
    private ZonedDateTime dateModification;

    private Long devisId;
    private Long quotationStatusId;
    @ManyToOne(optional = false)
    @NotNull
    private PrestationPEC prestation;
    @NotNull
    @DecimalMax(value = "100000000000000000")
    @Column(name = "total_ttc", nullable = false)
    private Double totalTtc;
    
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public ZonedDateTime getDateModification() {
		return dateModification;
	}
	public void setDateModification(ZonedDateTime dateModification) {
		this.dateModification = dateModification;
	}
	public Long getDevisId() {
		return devisId;
	}
	public void setDevisId(Long devisId) {
		this.devisId = devisId;
	}
	public Long getQuotationStatusId() {
		return quotationStatusId;
	}
	public void setQuotationStatusId(Long quotationStatusId) {
		this.quotationStatusId = quotationStatusId;
	}
	public PrestationPEC getPrestation() {
		return prestation;
	}
	public void setPrestation(PrestationPEC prestation) {
		this.prestation = prestation;
	}
	public Double getTotalTtc() {
		return totalTtc;
	}
	public void setTotalTtc(Double totalTtc) {
		this.totalTtc = totalTtc;
	}
    
    
	
}
