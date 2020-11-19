package com.gaconnecte.auxilium.service.dto;

import java.io.Serializable;
import java.time.LocalDate;

public class ViewContratDTO implements Serializable {
	
	 private Long id;

	    private String nomAgentAssurance;
	    private Long idAgent;
	    private String usageLabel;

	    private String zone;
	    private Long zoneId;
		private Long packId;
	    private String naturePack;
	    private LocalDate dateCreation;
	    private Long nbrePack;

	    

		private String companyName;
	    private Long companyId;


		public Long getCompanyId() {
			return companyId;
		}

	
		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}
		public String getNaturePack() {
			return naturePack;
		}

		public void setNaturePack(String naturePack) {
			this.naturePack = naturePack;
		}

		public String getNomAgentAssurance() {
			return nomAgentAssurance;
		}

		public void setNomAgentAssurance(String nomAgentAssurance) {
			this.nomAgentAssurance = nomAgentAssurance;
		}

		public String getUsageLabel() {
			return usageLabel;
		}

		public void setUsageLabel(String usageLabel) {
			this.usageLabel = usageLabel;
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

		
		 public String getCompanyName() {
			return companyName;
		}

		public void setCompanyName(String companyName) {
			this.companyName = companyName;
		}

		public void setCompanyId(Long companyId) {
			this.companyId = companyId;
		}

		public Long getZoneId() {
				return zoneId;
			}

			public void setZoneId(Long zoneId) {
				this.zoneId = zoneId;
			}

		@Override
		public String toString() {
			return "ViewContratDTO [id=" + id + ", nomAgentAssurance=" + nomAgentAssurance + ", idAgent=" + idAgent
					+ ", usageLabel=" + usageLabel + ", zone=" + zone + ", zoneId=" + zoneId + ", packId=" + packId
					+ ", naturePack=" + naturePack + ", dateCreation=" + dateCreation + ", nbrePack=" + nbrePack
					+ ", companyName=" + companyName + ", companyId=" + companyId + "]";
		}

		public LocalDate getDateCreation() {
			return dateCreation;
		}

		public void setDateCreation(LocalDate dateCreation) {
			this.dateCreation = dateCreation;
		}

		public Long getIdAgent() {
			return idAgent;
		}

		public void setIdAgent(Long idAgent) {
			this.idAgent = idAgent;
		}

		public Long getNbrePack() {
			return nbrePack;
		}

		public void setNbrePack(Long nbrePack) {
			this.nbrePack = nbrePack;
		}


}
