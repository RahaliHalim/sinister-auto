package com.gaconnecte.auxilium.domain.referential;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import com.gaconnecte.auxilium.domain.AbstractElementaryEntity;

/**
 * vehicle brand references.
 */
@Entity
@Table(name = "ref_vehicle_usage")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "ref_vehicle_usage")
public class RefVehicleUsage extends AbstractElementaryEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "code", length = 60)
    private String code;

    @Column(name = "active")
    private Boolean active;
    
    @Column(name = "compagnie_id")
    private Long compagnieId;
    
    @Column(name = "vat_rate_id")
    private Long vatRateId;

    @Column(name = "vat_rate")
    private BigDecimal vatRate;
    
    @Column(name = "compagnie")
    private String compagnie;
    
    
    
    
    
    public Long getVatRateId() {
		return vatRateId;
	}


	public void setVatRateId(Long vatRateId) {
		this.vatRateId = vatRateId;
	}


	public BigDecimal getVatRate() {
		return vatRate;
	}


	public void setVatRate(BigDecimal vatRate) {
		this.vatRate = vatRate;
	}


	public String getCompagnie() {
		return compagnie;
	}


	public void setCompagnie(String compagnie) {
		this.compagnie = compagnie;
	}


	public Long getCompagnieId() {
		return compagnieId;
	}


	public void setCompagnieId(Long compagnieId) {
		this.compagnieId = compagnieId;
	}


	/**
	 * @return the active
	 */
	public Boolean getActive() {
		return active;
	}


	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}


	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}


	/**
	 * @param active the active to set
	 */
	public void setActive(Boolean active) {
		this.active = active;
	}

    /**
	 * @return the active
	 */
	public boolean isActive() {
		return this.active != null ? this.active.booleanValue() : false;
	}
	

	@Override
	public String toString() {
		return "RefVehicleUsage [code=" + code + ", active=" + active + ", compagnieId=" + compagnieId + ", vatRateId="
				+ vatRateId + ", vatRate=" + vatRate + ", compagnie=" + compagnie + "]";
	}
}
