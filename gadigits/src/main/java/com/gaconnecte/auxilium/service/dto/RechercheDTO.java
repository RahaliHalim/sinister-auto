package com.gaconnecte.auxilium.service.dto;

import java.io.Serializable;
import java.time.LocalDate;

public class RechercheDTO implements Serializable{
	
	private String type;
	private String immatriculation;
	private String reference;
	private LocalDate startDate;
	private LocalDate endDate;
	private Long zoneId;
	private LocalDate dateCreation;
	private String naturePack;
	private Long idEtapePrestation;

	
	
	
	public Long getIdEtapePrestation() {
		return idEtapePrestation;
	}
	public void setIdEtapePrestation(Long idEtapePrestation) {
		this.idEtapePrestation = idEtapePrestation;
	}
	public String getType() {
		return type;
	}
	public String getReference() {
		return reference;
	}
	public void setReference(String reference) {
		this.reference = reference;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getImmatriculation() {
		return immatriculation;
	}
	public void setImmatriculation(String immatriculation) {
		this.immatriculation = immatriculation;
	}
	public LocalDate getStartDate() {
		return startDate;
	}
	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}
	public LocalDate getEndDate() {
		return endDate;
	}
	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}
	
	
	
	

	public Long getZoneId() {
		return zoneId;
	}
	public void setZoneId(Long zoneId) {
		this.zoneId = zoneId;
	}
	public LocalDate getDateCreation() {
		return dateCreation;
	}
	public void setDateCreation(LocalDate dateCreation) {
		this.dateCreation = dateCreation;
	}
	public String getNaturePack() {
		return naturePack;
	}
	public void setNaturePack(String naturePack) {
		this.naturePack = naturePack;
	}
	@Override
	public String toString() {
		return "RechercheDTO [type=" + type + ", immatriculation=" + immatriculation + ", reference=" + reference
				+ ", startDate=" + startDate + ", endDate=" + endDate + ", zoneId=" + zoneId + ", dateCreation="
				+ dateCreation + ", naturePack=" + naturePack + ", idEtapePrestation=" + idEtapePrestation + "]";
	}
	
}
