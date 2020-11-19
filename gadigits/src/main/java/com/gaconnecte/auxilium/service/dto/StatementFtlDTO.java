package com.gaconnecte.auxilium.service.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class StatementFtlDTO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long id;
	//Remorqueur
	private Long idTug;
	private String socialReason;
	private String taxRegistration;
	private String adress;
	private String tel;
	private Integer code;
	//GA
	private String taxRegistrationGA;
	private String adressGA;
	private String telGA;

	private Set<SinisterPrestationDTO> listSinisterPrestation = new HashSet<>();
	private List<CompanyFtlDTO> companies = new ArrayList<>();
	private Double thtByCompagnie;
	
	//Facture
	private String invoiceReference;
	private Double tht;
	private Double vat;
	private Double stamped;
	private Double ttc;
	private String logo64;
	private String slogon64;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getIdTug() {
		return idTug;
	}
	public void setIdTug(Long idTug) {
		this.idTug = idTug;
	}
	public String getSocialReason() {
		return socialReason;
	}
	public void setSocialReason(String socialReason) {
		this.socialReason = socialReason;
	}
	public String getTaxRegistration() {
		return taxRegistration;
	}
	public void setTaxRegistration(String taxRegistration) {
		this.taxRegistration = taxRegistration;
	}
	public String getAdress() {
		return adress;
	}
	public void setAdress(String adress) {
		this.adress = adress;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	public String getTaxRegistrationGA() {
		return taxRegistrationGA;
	}
	public void setTaxRegistrationGA(String taxRegistrationGA) {
		this.taxRegistrationGA = taxRegistrationGA;
	}
	public String getAdressGA() {
		return adressGA;
	}
	public void setAdressGA(String adressGA) {
		this.adressGA = adressGA;
	}
	public String getTelGA() {
		return telGA;
	}
	public void setTelGA(String telGA) {
		this.telGA = telGA;
	}
	public Set<SinisterPrestationDTO> getListSinisterPrestation() {
		return listSinisterPrestation;
	}
	public void setListSinisterPrestation(Set<SinisterPrestationDTO> listSinisterPrestation) {
		this.listSinisterPrestation = listSinisterPrestation;
	}
	public Double getThtByCompagnie() {
		return thtByCompagnie;
	}
	public void setThtByCompagnie(Double thtByCompagnie) {
		this.thtByCompagnie = thtByCompagnie;
	}
	public String getInvoiceReference() {
		return invoiceReference;
	}
	public void setInvoiceReference(String invoiceReference) {
		this.invoiceReference = invoiceReference;
	}
	public Double getTht() {
		return tht;
	}
	public void setTht(Double tht) {
		this.tht = tht;
	}
	public void addTht(Double tht) {
		this.tht += tht;
	}
	public Double getVat() {
		return vat;
	}
	public void setVat(Double vat) {
		this.vat = vat;
	}
	public Double getStamped() {
		return stamped;
	}
	public void setStamped(Double stamped) {
		this.stamped = stamped;
	}
	public Double getTtc() {
		return ttc;
	}
	public void setTtc(Double ttc) {
		this.ttc = ttc;
	}
	public void addTtc(Double ttc) {
		this.ttc += ttc;
	}

	public List<CompanyFtlDTO> getCompanies() {
		return companies;
	}
	public void setCompanies(List<CompanyFtlDTO> companies) {
		this.companies = companies;
	}
	/**
	 * @return the logo64
	 */
	public String getLogo64() {
		return logo64;
	}
	/**
	 * @param logo64 the logo64 to set
	 */
	public void setLogo64(String logo64) {
		this.logo64 = logo64;
	}
	/**
	 * @return the slogon64
	 */
	public String getSlogon64() {
		return slogon64;
	}
	/**
	 * @param slogon64 the slogon64 to set
	 */
	public void setSlogon64(String slogon64) {
		this.slogon64 = slogon64;
	}

}
