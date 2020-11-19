package com.gaconnecte.auxilium.service.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A DTO for the SinisterPrestation entity.
 */
public class CompanyFtlDTO implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String companyName;
	private Set<SinisterPrestationDTO> prestations = new HashSet<>();
	private Double totalHt;
	
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public Set<SinisterPrestationDTO> getPrestations() {
		return prestations;
	}
	public void setPrestations(Set<SinisterPrestationDTO> prestations) {
		this.prestations = prestations;
	}
	public void addPrestation(SinisterPrestationDTO prestation) {
		this.prestations.add(prestation);
	}
	public Double getTotalHt() {
		return totalHt;
	}
	public void setTotalHt(Double totalHt) {
		this.totalHt = totalHt;
	}
	
	public void addTotalHt(Double totalHt) {
		this.totalHt += totalHt;
	}
}
