package com.gaconnecte.auxilium.service.dto;

import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * A DTO for the Piece entity.
 */
public class QuotationHistoryDTO implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
    private ZonedDateTime dateModification;
    private Long devisId;
    private Long quotationStatusId;
    private Long prestationPecId ;
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
	public Long getPrestationPecId() {
		return prestationPecId;
	}
	public void setPrestationPecId(Long prestationPecId) {
		this.prestationPecId = prestationPecId;
	}
	public Double getTotalTtc() {
		return totalTtc;
	}
	public void setTotalTtc(Double totalTtc) {
		this.totalTtc = totalTtc;
	}
    
    


}