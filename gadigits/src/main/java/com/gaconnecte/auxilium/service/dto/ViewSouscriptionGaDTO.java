package com.gaconnecte.auxilium.service.dto;

import java.io.Serializable;
import java.time.LocalDate;



public class ViewSouscriptionGaDTO implements Serializable{

	
	  
	    private Long id;
	    private Long compagnieId;
	    private String compagnie; 
	    private String numeroContrat;
	    private Long zoneId; 
	    private String zone; 
	    private Long packId; 
	    private String pack; 
	    private Long primeHt;
	    private Long primeTtc; 
	    private Long partRea; 
	    private LocalDate dateCreation;
	    private Long serviceId; 
	    private String service; 
	    private Long Idcharge;
	    private String Charge;
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
		public String getNumeroContrat() {
			return numeroContrat;
		}
		public void setNumeroContrat(String numeroContrat) {
			this.numeroContrat = numeroContrat;
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
		public Long getPrimeHt() {
			return primeHt;
		}
		public void setPrimeHt(Long primeHt) {
			this.primeHt = primeHt;
		}
		public Long getPrimeTtc() {
			return primeTtc;
		}
		public void setPrimeTtc(Long primeTtc) {
			this.primeTtc = primeTtc;
		}
		public Long getPartRea() {
			return partRea;
		}
		public void setPartRea(Long partRea) {
			this.partRea = partRea;
		}
		public LocalDate getDateCreation() {
			return dateCreation;
		}
		public void setDateCreation(LocalDate dateCreation) {
			this.dateCreation = dateCreation;
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
		public Long getIdcharge() {
			return Idcharge;
		}
		public void setIdcharge(Long idcharge) {
			Idcharge = idcharge;
		}
		public String getCharge() {
			return Charge;
		}
		public void setCharge(String charge) {
			Charge = charge;
		}
		@Override
		public String toString() {
			return "ViewSouscriptionGa [id=" + id + ", compagnieId=" + compagnieId + ", compagnie=" + compagnie
					+ ", numeroContrat=" + numeroContrat + ", zoneId=" + zoneId + ", zone=" + zone + ", packId="
					+ packId + ", pack=" + pack + ", primeHt=" + primeHt + ", primeTtc=" + primeTtc + ", partRea="
					+ partRea + ", dateCreation=" + dateCreation + ", serviceId=" + serviceId + ", service=" + service
					+ ", Idcharge=" + Idcharge + ", Charge=" + Charge + "]";
		} 
	    
	    
	
}
