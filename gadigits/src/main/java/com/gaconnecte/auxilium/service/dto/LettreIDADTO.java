package com.gaconnecte.auxilium.service.dto;

import java.io.Serializable;
import java.time.LocalDate;

import com.gaconnecte.auxilium.domain.Tiers;

public class LettreIDADTO implements Serializable {
	
	private String assureFullName;
	private String contratAssuranceNum;
	private String immatriculation;
	private String tiersImmatriculation;
	private String tiersName;
	private String referenceSinister;
	private String adresseSinistre;
	private String dateAccident;
	private Integer casDeBareme;
	private String assuranceTiersName;
	private String assuranceTiersAdresse ;
	private String assuranceAssureName;
	private String contratAssuranceNumTiers;
	
	
	public String getAssureFullName() {
		return assureFullName;
	}
	public void setAssureFullName(String assureFullName) {
		this.assureFullName = assureFullName;
	}
	public String getContratAssuranceNum() {
		return contratAssuranceNum;
	}
	public void setContratAssuranceNum(String contratAssuranceNum) {
		this.contratAssuranceNum = contratAssuranceNum;
	}
	public String getImmatriculation() {
		return immatriculation;
	}
	public void setImmatriculation(String immatriculation) {
		this.immatriculation = immatriculation;
	}
	public String getTiersImmatriculation() {
		return tiersImmatriculation;
	}
	public void setTiersImmatriculation(String tiersImmatriculation) {
		this.tiersImmatriculation = tiersImmatriculation;
	}
	public String getTiersName() {
		return tiersName;
	}
	public void setTiersName(String tiersName) {
		this.tiersName = tiersName;
	}
	public String getReferenceSinister() {
		return referenceSinister;
	}
	public void setReferenceSinister(String referenceSinister) {
		this.referenceSinister = referenceSinister;
	}
	public String getAdresseSinistre() {
		return adresseSinistre;
	}
	public void setAdresseSinistre(String adresseSinistre) {
		this.adresseSinistre = adresseSinistre;
	}
	public String getDateAccident() {
		return dateAccident;
	}
	public void setDateAccident(String dateAccident) {
		this.dateAccident = dateAccident;
	}
	public Integer getCasDeBareme() {
		return casDeBareme;
	}
	public void setCasDeBareme(Integer casDeBareme) {
		this.casDeBareme = casDeBareme;
	}
	public String getAssuranceTiersName() {
		return assuranceTiersName;
	}
	public void setAssuranceTiersName(String assuranceTiersName) {
		this.assuranceTiersName = assuranceTiersName;
	}
	public String getAssuranceTiersAdresse() {
		return assuranceTiersAdresse;
	}
	public void setAssuranceTiersAdresse(String assuranceTiersAdresse) {
		this.assuranceTiersAdresse = assuranceTiersAdresse;
	}
	public String getAssuranceAssureName() {
		return assuranceAssureName;
	}
	public void setAssuranceAssureName(String assuranceAssureName) {
		this.assuranceAssureName = assuranceAssureName;
	}
	public String getContratAssuranceNumTiers() {
		return contratAssuranceNumTiers;
	}
	public void setContratAssuranceNumTiers(String contratAssuranceNumTiers) {
		this.contratAssuranceNumTiers = contratAssuranceNumTiers;
	}
	
	
}
