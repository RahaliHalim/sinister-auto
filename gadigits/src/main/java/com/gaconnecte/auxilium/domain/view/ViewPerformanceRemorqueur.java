package com.gaconnecte.auxilium.domain.view;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "view_performance_remorqueur")
public class ViewPerformanceRemorqueur {

	  @Id
	  private Long id;
	  
	  @Column(name = "referencega")
	    private String referenceGa;
	    
	  
	    @Column(name = "remorqueur_id")
	    private Long remorqueurId;
	    
	    @Column(name = "remorqueur")
	    private String remorqueur;
	    
	    @Column(name = "compagnie_id")
	    private Long compagnieId;
	    
	    @Column(name = "compagnie")
	    private String compagnie;
	    
	    @Column(name = "immatriculation_vehicule")
	    private String immatriculationVehicule;
	    
	    @Column(name = "type_service")
	    private String typeService;
	    
	    @Column(name = "incident_governorate_id")
	    private Long zoneId;
	    
	    @Column(name = "zone")
	    private String zone;
	    
	    @Column(name = "creation_date")
	    private LocalDate creationDate;
	    
	    @Column(name = "usage_id")
	    private Long usageId;
	    
	    @Column(name = "libelle")
	    private String usage;
	    
	    @Column(name = "count")
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
			return "ViewPerformanceRemorqueur [id=" + id + ", referenceGa=" + referenceGa + ", remorqueurId="
					+ remorqueurId + ", remorqueur=" + remorqueur + ", compagnieId=" + compagnieId + ", compagnie="
					+ compagnie + ", immatriculationVehicule=" + immatriculationVehicule + ", typeService="
					+ typeService + ", zoneId=" + zoneId + ", zone=" + zone + ", creationDate=" + creationDate
					+ ", usageId=" + usageId + ", usage=" + usage + ", count=" + count + "]";
		}
	  
	    
	    
	    
}
