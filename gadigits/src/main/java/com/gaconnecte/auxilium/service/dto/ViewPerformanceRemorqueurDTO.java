package com.gaconnecte.auxilium.service.dto;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Id;

public class ViewPerformanceRemorqueurDTO {

	  private Long id;
	  private String referenceGa;
	  private Long remorqueurId;
	  private String remorqueur;
	  private Long compagnieId;
	   private String compagnie;
	  private String immatriculationVehicule;
	   private String typeService;
	  private Long zoneId;
	    private String zone;
	   private LocalDate creationDate;
	   private Long usageId;
	   private String usage;
	    private Long count;
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
		public Long getRemorqueurId() {
			return remorqueurId;
		}
		public void setRemorqueurId(Long remorqueurId) {
			this.remorqueurId = remorqueurId;
		}
		public String getRemorqueur() {
			return remorqueur;
		}
		public void setRemorqueur(String remorqueur) {
			this.remorqueur = remorqueur;
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
		public String getImmatriculationVehicule() {
			return immatriculationVehicule;
		}
		public void setImmatriculationVehicule(String immatriculationVehicule) {
			this.immatriculationVehicule = immatriculationVehicule;
		}
		public String getTypeService() {
			return typeService;
		}
		public void setTypeService(String typeService) {
			this.typeService = typeService;
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
		public LocalDate getCreationDate() {
			return creationDate;
		}
		public void setCreationDate(LocalDate creationDate) {
			this.creationDate = creationDate;
		}
		public Long getUsageId() {
			return usageId;
		}
		public void setUsageId(Long usageId) {
			this.usageId = usageId;
		}
		public String getUsage() {
			return usage;
		}
		public void setUsage(String usage) {
			this.usage = usage;
		}
		public Long getCount() {
			return count;
		}
		public void setCount(Long count) {
			this.count = count;
		}
		@Override
		public String toString() {
			return "ViewPerformanceRemorqueurDTO [id=" + id + ", referenceGa=" + referenceGa + ", remorqueurId="
					+ remorqueurId + ", remorqueur=" + remorqueur + ", compagnieId=" + compagnieId + ", compagnie="
					+ compagnie + ", immatriculationVehicule=" + immatriculationVehicule + ", typeService="
					+ typeService + ", zoneId=" + zoneId + ", zone=" + zone + ", creationDate=" + creationDate
					+ ", usageId=" + usageId + ", usage=" + usage + ", count=" + count + "]";
		}
	    
	    
	    
}
