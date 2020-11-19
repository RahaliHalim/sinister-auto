package com.gaconnecte.auxilium.domain.view;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


	
	 @Entity
	 @Table(name = "view_delai_moyen_immobilisation")
	 public class ViewDelaiMoyImmobilisation {
	     @Id
	     private Long id;
	     @Column(name = "referencega")
	     private String referenceGa;
	     
	 
	     @Column(name = "compagnie_id")
	     private Long compagnieId;
	     
	     @Column(name = "compagnie")
	     private String compagnie;
	     
	     @Column(name = "governorate_id")
	     private Long zoneId;
	     
	     @Column(name = "zone")
	     private String zone;
	     

	     @Column(name = "mode_gestion_id")
	     private Long modeGestionId;
	     
	     @Column(name = "mode_gestion")
	     private String modeGestion;
	     
	     @Column(name = "charge_id")
	     private Long chargeId;
	     
	     @Column(name = "charge_nom")
	     private String chargeNom;
	     @Column(name = "charge_prenom")
	     private String chargePrenom;
	
	     @Column(name = "date_bon_de_sortie")
	     private LocalDate dateBonDeSortie;
	     
	     @Column(name = "date_ouverture")
	     private LocalDate dateOuverture;
	     
	     @Column(name = "nbre_de_jour")
	     private String nbreDeJour;
	     
	     @Column(name = "date_creation")
	     private LocalDate creationDate;
	     
	     @Column(name = "state_shock")
	     private Boolean typeChocBoolean;
	     
	     @Column(name = "expert")
	     private String expert;
	     
	     @Column(name = "reparateur")
	     private String reparateur;
	     
	     @Column(name = "date_accord")
	     private LocalDate dateAccord;
	     
	     @Column(name = "vehicle_receipt_date")
	     private LocalDate vehicleReceiptDate;
	     
	     
	     public Boolean getTypeChocBoolean() {
			return typeChocBoolean;
		}
		public void setTypeChocBoolean(Boolean typeChocBoolean) {
			this.typeChocBoolean = typeChocBoolean;
		}
		@Column(name = "light_shock")
	     private String typeChoc;
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
		public String getExpert() {
			return expert;
		}
		public void setExpert(String expert) {
			this.expert = expert;
		}
		public String getReparateur() {
			return reparateur;
		}
		public void setReparateur(String reparateur) {
			this.reparateur = reparateur;
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
			return "ViewDelaiMoyImmobilisation [id=" + id + ", referenceGa=" + referenceGa + ", compagnieId="
					+ compagnieId + ", compagnie=" + compagnie + ", zoneId=" + zoneId + ", zone=" + zone
					+ ", modeGestionId=" + modeGestionId + ", modeGestion=" + modeGestion + ", chargeId=" + chargeId
					+ ", chargeNom=" + chargeNom + ", chargePrenom=" + chargePrenom + ", dateBonDeSortie="
					+ dateBonDeSortie + ", dateOuverture=" + dateOuverture + ", nbreDeJour=" + nbreDeJour
					+ ", creationDate=" + creationDate + ", typeChocBoolean=" + typeChocBoolean + ", typeChoc="
					+ typeChoc + "]";
		}
	
	
}
