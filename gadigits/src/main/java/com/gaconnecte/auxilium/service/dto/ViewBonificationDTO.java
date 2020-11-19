package com.gaconnecte.auxilium.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;


public class ViewBonificationDTO  implements Serializable {
	
    private Long id;
    private Long compagnieId;
    private String compagnie;
    private String numContrat;
    private Long zoneId;
     private String zone;
    private Long packId; 
     private String pack; 
    private String referenceGa;
    private Long reparateurId;
    private String reparateurName;
    private String reparateurCode;
    private Long idZoneReparateur;
    private String zoneReparateur;
    private LocalDate dateBonSortie;
    private String moisBonSortie;
    private BigDecimal mdIp;
    private Long piece;
    private BigDecimal partGa;
    private BigDecimal partCie;
    private LocalDate creationDate;
    private Long serviceId;
    private String service;
    private Long chargeId;
    private String charge;
    private Long expertId;
    private String expert;
    private LocalDate dateOuverture;
    private String marque;
    private String marqueCode;


    private BigDecimal baseCalculModIp;
    
    private BigDecimal baseCalculPiece;
    
    
    
    
    
	public String getReparateurCode() {
		return reparateurCode;
	}
	public void setReparateurCode(String reparateurCode) {
		this.reparateurCode = reparateurCode;
	}
	public String getMarqueCode() {
		return marqueCode;
	}
	public void setMarqueCode(String marqueCode) {
		this.marqueCode = marqueCode;
	}
	public LocalDate getDateOuverture() {
		return dateOuverture;
	}
	public void setDateOuverture(LocalDate dateOuverture) {
		this.dateOuverture = dateOuverture;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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

	public String getNumContrat() {
		return numContrat;
	}
	public void setNumContrat(String numContrat) {
		this.numContrat = numContrat;
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
	public Long getPackId() {
		return packId;
	}
	public void setPackId(Long packId) {
		this.packId = packId;
	}
	public String getPack() {
		return pack;
	}
	public void setPack(String pack) {
		this.pack = pack;
	}
	public String getReferenceGa() {
		return referenceGa;
	}
	public void setReferenceGa(String referenceGa) {
		this.referenceGa = referenceGa;
	}
	public Long getReparateurId() {
		return reparateurId;
	}
	public void setReparateurId(Long reparateurId) {
		this.reparateurId = reparateurId;
	}
	public String getReparateurName() {
		return reparateurName;
	}
	public void setReparateurName(String reparateurName) {
		this.reparateurName = reparateurName;
	}
	public Long getIdZoneReparateur() {
		return idZoneReparateur;
	}
	public void setIdZoneReparateur(Long idZoneReparateur) {
		this.idZoneReparateur = idZoneReparateur;
	}
	public String getZoneReparateur() {
		return zoneReparateur;
	}
	public void setZoneReparateur(String zoneReparateur) {
		this.zoneReparateur = zoneReparateur;
	}
	public LocalDate getDateBonSortie() {
		return dateBonSortie;
	}
	public void setDateBonSortie(LocalDate dateBonSortie) {
		this.dateBonSortie = dateBonSortie;
	}
	public String getMoisBonSortie() {
		return moisBonSortie;
	}
	public void setMoisBonSortie(String moisBonSortie) {
		this.moisBonSortie = moisBonSortie;
	}
		public Long getPiece() {
		return piece;
	}
	public void setPiece(Long piece) {
		this.piece = piece;
	}
		public LocalDate getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(LocalDate creationDate) {
		this.creationDate = creationDate;
	}
	public Long getServiceId() {
		return serviceId;
	}
	public void setServiceId(Long serviceId) {
		this.serviceId = serviceId;
	}
	public String getService() {
		return service;
	}
	public void setService(String service) {
		this.service = service;
	}
	public Long getChargeId() {
		return chargeId;
	}
	public void setChargeId(Long chargeId) {
		this.chargeId = chargeId;
	}
	public String getCharge() {
		return charge;
	}
	public void setCharge(String charge) {
		this.charge = charge;
	}
	public Long getExpertId() {
		return expertId;
	}
	public void setExpertId(Long expertId) {
		this.expertId = expertId;
	}
	public String getExpert() {
		return expert;
	}
	public void setExpert(String expert) {
		this.expert = expert;
	}
	public String getMarque() {
		return marque;
	}
	public void setMarque(String marque) {
		this.marque = marque;
	}
	@Override
	public String toString() {
		return "ViewBonificationDTO [id=" + id + ", compagnieId=" + compagnieId + ", compagnie=" + compagnie
				+ ", numContrat=" + numContrat + ", zoneId=" + zoneId + ", zone=" + zone + ", packId=" + packId
				+ ", pack=" + pack + ", referenceGa=" + referenceGa + ", reparateurId=" + reparateurId
				+ ", reparateurName=" + reparateurName + ", idZoneReparateur=" + idZoneReparateur + ", zoneReparateur="
				+ zoneReparateur + ", dateBonSortie=" + dateBonSortie + ", moisBonSortie=" + moisBonSortie + ", mdIp="
				+ mdIp + ", piece=" + piece + ", partGa=" + partGa + ", partCie=" + partCie + ", creationDate="
				+ creationDate + ", serviceId=" + serviceId + ", service=" + service + ", chargeId=" + chargeId
				+ ", charge=" + charge + ", expertId=" + expertId + ", expert=" + expert + ", dateOuverture="
				+ dateOuverture + ", baseCalculModIp=" + baseCalculModIp + ", baseCalculPiece=" + baseCalculPiece + "]";
	}
	public BigDecimal getMdIp() {
		return mdIp;
	}
	public void setMdIp(BigDecimal mdIp) {
		this.mdIp = mdIp;
	}
	public BigDecimal getPartGa() {
		return partGa;
	}
	public void setPartGa(BigDecimal partGa) {
		this.partGa = partGa;
	}
	public BigDecimal getPartCie() {
		return partCie;
	}
	public void setPartCie(BigDecimal partCie) {
		this.partCie = partCie;
	}
	public BigDecimal getBaseCalculModIp() {
		return baseCalculModIp;
	}
	public void setBaseCalculModIp(BigDecimal baseCalculModIp) {
		this.baseCalculModIp = baseCalculModIp;
	}
	public BigDecimal getBaseCalculPiece() {
		return baseCalculPiece;
	}
	public void setBaseCalculPiece(BigDecimal baseCalculPiece) {
		this.baseCalculPiece = baseCalculPiece;
	}
	
	
	
    
}
