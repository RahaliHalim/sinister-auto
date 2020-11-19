package com.gaconnecte.auxilium.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Id;

public class ReportFrequencyRateDTO implements Serializable {

		private Long id; 
		private Long partnerId;
	    private String partnerName;
	    private String usageLabel;
	    private String incidentNature;
	    private Integer contractCount;
	    private LocalDate creationDate;
	    private double frequencyRate;
	    private String typeService;


	    
		public double getFrequencyRate() {
			return frequencyRate;
		}
		public void setFrequencyRate(double frequencyRate) {
			this.frequencyRate = frequencyRate;
		}
		public Long getId() {
			return id;
		}
		public void setId(Long id) {
			this.id = id;
		}
		public Long getPartnerId() {
			return partnerId;
		}
		public void setPartnerId(Long partnerId) {
			this.partnerId = partnerId;
		}
		public String getPartnerName() {
			return partnerName;
		}
		public void setPartnerName(String partnerName) {
			this.partnerName = partnerName;
		}
		public String getUsageLabel() {
			return usageLabel;
		}
		public void setUsageLabel(String usageLabel) {
			this.usageLabel = usageLabel;
		}
		public String getIncidentNature() {
			return incidentNature;
		}
		public void setIncidentNature(String incidentNature) {
			this.incidentNature = incidentNature;
		}
		public Integer getContractCount() {
			return contractCount;
		}
		public void setContractCount(Integer contractCount) {
			this.contractCount = contractCount;
		}
		
		public LocalDate getCreationDate() {
			return creationDate;
		}
		public void setCreationDate(LocalDate creationDate) {
			this.creationDate = creationDate;
		}
		
		
		public String getTypeService() {
			return typeService;
		}
		public void setTypeService(String typeService) {
			this.typeService = typeService;
		}
		@Override
		public String toString() {
			return "ReportFrequencyRateDTO [id=" + id + ", partnerId=" + partnerId + ", partnerName=" + partnerName
					+ ", usageLabel=" + usageLabel + ", incidentNature=" + incidentNature + ", contractCount="
					+ contractCount + ", creationDate=" + creationDate + ", frequencyRate=" + frequencyRate + "]";
		}

}
