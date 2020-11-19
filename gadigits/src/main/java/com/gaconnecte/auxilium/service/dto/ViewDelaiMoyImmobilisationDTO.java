package com.gaconnecte.auxilium.service.dto;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Id;

public class ViewDelaiMoyImmobilisationDTO {

	
	     private Long id;
	     private String referenceGa;
	     private Long compagnieId;
	     private String compagnie;
	     private Long zoneId;
	     private String zone;
	     private Long modeGestionId;
	     private String modeGestion;
	     private Long chargeId;
	     private String chargeNom;
	     private String chargePrenom;
	     private LocalDate dateBonDeSortie;
	     private LocalDate dateOuverture;
	     private String nbreDeJour;
	     private LocalDate creationDate;
	     private Boolean typeChocBoolean;
	     private String typeChoc;
	     private String reparateur;
	     private String expert;
	     private LocalDate dateAccord;  
	     private LocalDate vehicleReceiptDate;
		public Long getId() {
			return id;
		}
		public void setId(Long id) {
			this.id = id;
		}
		public String getReferenceGa() {
			return referenceGa;
		}
		public void setReferenceGa(String referenceGa) {
			this.referenceGa = referenceGa;
		}
		public Long getCompagnieId() {
			return compagnieId;
		}
		public void setCompagnieId(Long compagnieId) {
			this.compagnieId = compagnieId;
		}
		public String getCompagnie() {
			return compagnie;
		}
		public void setCompagnie(String compagnie) {
			this.compagnie = compagnie;
		}
		public Long getZoneId() {
			return zoneId;
		}
		public void setZoneId(Long zoneId) {
			this.zoneId = zoneId;
		}
		public String getZone() {
			return zone;
		}
		public void setZone(String zone) {
			this.zone = zone;
		}
		public Long getModeGestionId() {
			return modeGestionId;
		}
		public void setModeGestionId(Long modeGestionId) {
			this.modeGestionId = modeGestionId;
		}
		public String getModeGestion() {
			return modeGestion;
		}
		public void setModeGestion(String modeGestion) {
			this.modeGestion = modeGestion;
		}
		public Long getChargeId() {
			return chargeId;
		}
		public void setChargeId(Long chargeId) {
			this.chargeId = chargeId;
		}
		public String getChargeNom() {
			return chargeNom;
		}
		public void setChargeNom(String chargeNom) {
			this.chargeNom = chargeNom;
		}
		public String getChargePrenom() {
			return chargePrenom;
		}
		public void setChargePrenom(String chargePrenom) {
			this.chargePrenom = chargePrenom;
		}
		public LocalDate getDateBonDeSortie() {
			return dateBonDeSortie;
		}
		public void setDateBonDeSortie(LocalDate dateBonDeSortie) {
			this.dateBonDeSortie = dateBonDeSortie;
		}
		public LocalDate getDateOuverture() {
			return dateOuverture;
		}
		public void setDateOuverture(LocalDate dateOuverture) {
			this.dateOuverture = dateOuverture;
		}
		public String getNbreDeJour() {
			return nbreDeJour;
		}
		public void setNbreDeJour(String nbreDeJour) {
			this.nbreDeJour = nbreDeJour;
		}
		public LocalDate getCreationDate() {
			return creationDate;
		}
		public void setCreationDate(LocalDate creationDate) {
			this.creationDate = creationDate;
		}
	
		public String getTypeChoc() {
			return typeChoc;
		}
		public void setTypeChoc(String typeChoc) {
			this.typeChoc = typeChoc;
		}
		public String getReparateur() {
			return reparateur;
		}
		public void setReparateur(String reparateur) {
			this.reparateur = reparateur;
		}
		public String getExpert() {
			return expert;
		}
		public void setExpert(String expert) {
			this.expert = expert;
		}
		
		public LocalDate getDateAccord() {
			return dateAccord;
		}
		public void setDateAccord(LocalDate dateAccord) {
			this.dateAccord = dateAccord;
		}
		public LocalDate getVehicleReceiptDate() {
			return vehicleReceiptDate;
		}
		public void setVehicleReceiptDate(LocalDate vehicleReceiptDate) {
			this.vehicleReceiptDate = vehicleReceiptDate;
		}
		@Override
		public String toString() {
			return "ViewDelaiMoyImmobilisationDTO [id=" + id + ", referenceGa=" + referenceGa + ", compagnieId="
					+ compagnieId + ", compagnie=" + compagnie + ", zoneId=" + zoneId + ", zone=" + zone
					+ ", modeGestionId=" + modeGestionId + ", modeGestion=" + modeGestion + ", chargeId=" + chargeId
					+ ", chargeNom=" + chargeNom + ", chargePrenom=" + chargePrenom + ", dateBonDeSortie="
					+ dateBonDeSortie + ", dateOuverture=" + dateOuverture + ", nbreDeJour=" + nbreDeJour
					+ ", creationDate=" + creationDate + ", typeChocBoolean=" + typeChocBoolean + ", typeChoc="
					+ typeChoc + "]";
		}
		public Boolean getTypeChocBoolean() {
			return typeChocBoolean;
		}
		public void setTypeChocBoolean(Boolean typeChocBoolean) {
			this.typeChocBoolean = typeChocBoolean;
		}
	
}
