/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gaconnecte.auxilium.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;

/**
 * A DTO for the VehicleBrand entity.
 * @author hannibaal
 */
public class ReportTugPerformanceDTO implements Serializable {

    private Long id; 
    
    private Long affectedTugId;

    private String affectedTugLabel; 

    private String reference;
    
    private String registrationNumber;
    
    private Long partnerId;
    
    private String partnerName;
    
    private String serviceTypeLabel;
    
    private String delaiOperation;
    
    private String usageLabel;
    
    private Long interventionTimeAvgMin;

    private String interventionTimeAvg;
    
    private Boolean hasHabillage;
    
    private String incidentGovernorateLabel;
    
    private BigDecimal priceTtc;
    
    private LocalDate creationDate;
    
    private Long zoneId;
    
    private LocalDateTime insuredArrivalDate;
    
    private LocalDateTime tugArrivalDate;
    
    private LocalDateTime tugAssignmentDate;
    
    private String cancelGroundsDescription;
    
    private LocalDateTime cancelDate;
	private LocalDateTime closureDate;
    private String creationUser;
    private String closureUser;



    
    public LocalDateTime getClosureDate() {
		return closureDate;
	}

	public void setClosureDate(LocalDateTime closureDate) {
		this.closureDate = closureDate;
	}

	public String getCreationUser() {
		return creationUser;
	}

	public void setCreationUser(String creationUser) {
		this.creationUser = creationUser;
	}

	public String getClosureUser() {
		return closureUser;
	}

	public void setClosureUser(String closureUser) {
		this.closureUser = closureUser;
	}

	public Long getZoneId() {
		return zoneId;
	}

	public void setZoneId(Long zoneId) {
		this.zoneId = zoneId;
	}

	
	
	public Long getInterventionTimeAvgMin() {
		return interventionTimeAvgMin;
	}

	public void setInterventionTimeAvgMin(Long interventionTimeAvgMin) {
		this.interventionTimeAvgMin = interventionTimeAvgMin;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAffectedTugId() {
        return affectedTugId;
    }

    public void setAffectedTugId(Long affectedTugId) {
        this.affectedTugId = affectedTugId;
    }

    public String getAffectedTugLabel() {
        return affectedTugLabel;
    }

    public void setAffectedTugLabel(String affectedTugLabel) {
        this.affectedTugLabel = affectedTugLabel;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
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

    public String getServiceTypeLabel() {
        return serviceTypeLabel;
    }

    public void setServiceTypeLabel(String serviceTypeLabel) {
        this.serviceTypeLabel = serviceTypeLabel;
    }

    public String getUsageLabel() {
        return usageLabel;
    }

    public void setUsageLabel(String usageLabel) {
        this.usageLabel = usageLabel;
    }


    public String getInterventionTimeAvg() {
        return interventionTimeAvg;
    }

    public void setInterventionTimeAvg(String interventionTimeAvg) {
        this.interventionTimeAvg = interventionTimeAvg;
    }

    public Boolean getHasHabillage() {
        return hasHabillage;
    }

    public void setHasHabillage(Boolean hasHabillage) {
        this.hasHabillage = hasHabillage;
    }

    public String getIncidentGovernorateLabel() {
        return incidentGovernorateLabel;
    }

    public void setIncidentGovernorateLabel(String incidentGovernorateLabel) {
        this.incidentGovernorateLabel = incidentGovernorateLabel;
    }

    public BigDecimal getPriceTtc() {
        return priceTtc;
    }

    public void setPriceTtc(BigDecimal priceTtc) {
        this.priceTtc = priceTtc;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public String getDelaiOperation() {
		return delaiOperation;
	}

	public void setDelaiOperation(String delaiOperation) {
		this.delaiOperation = delaiOperation;
	}

	public LocalDateTime getInsuredArrivalDate() {
		return insuredArrivalDate;
	}

	public void setInsuredArrivalDate(LocalDateTime insuredArrivalDate) {
		this.insuredArrivalDate = insuredArrivalDate;
	}

	public LocalDateTime getTugArrivalDate() {
		return tugArrivalDate;
	}

	public void setTugArrivalDate(LocalDateTime tugArrivalDate) {
		this.tugArrivalDate = tugArrivalDate;
	}

	public LocalDateTime getTugAssignmentDate() {
		return tugAssignmentDate;
	}

	public void setTugAssignmentDate(LocalDateTime tugAssignmentDate) {
		this.tugAssignmentDate = tugAssignmentDate;
	}

	public String getCancelGroundsDescription() {
		return cancelGroundsDescription;
	}

	public void setCancelGroundsDescription(String cancelGroundsDescription) {
		this.cancelGroundsDescription = cancelGroundsDescription;
	}

	public LocalDateTime getCancelDate() {
		return cancelDate;
	}

	public void setCancelDate(LocalDateTime cancelDate) {
		this.cancelDate = cancelDate;
	}

	@Override
	public String toString() {
		return "ReportTugPerformanceDTO [id=" + id + ", affectedTugId=" + affectedTugId + ", affectedTugLabel="
				+ affectedTugLabel + ", reference=" + reference + ", registrationNumber=" + registrationNumber
				+ ", partnerId=" + partnerId + ", partnerName=" + partnerName + ", serviceTypeLabel=" + serviceTypeLabel
				+ ", usageLabel=" + usageLabel + ", interventionTimeAvgMin=" + interventionTimeAvgMin
				+ ", interventionTimeAvg=" + interventionTimeAvg + ", hasHabillage=" + hasHabillage
				+ ", incidentGovernorateLabel=" + incidentGovernorateLabel + ", priceTtc=" + priceTtc
				+ ", creationDate=" + creationDate + ", zoneId=" + zoneId + "]";
	}
    
}
