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
public class ReportFollowUpAssistanceDTO implements Serializable {

    private Long id; 

    private String reference;

    private String registrationNumber;

    private String fullName;

    private Long partnerId;
    
    private String partnerName;
    
    private String packLabel; 

    private String usageLabel; 

    private String serviceType;

    private LocalDate incidentDate;
        
    private String incidentNature;
    
    private String incidentMonth;
    
    private Long affectedTugId;

    private String affectedTugLabel; 

    private String interventionTimeAvgMin;
    
    private String interventionTimeAvg;

    private String incidentLocationLabel;

    private String destinationLocationLabel;

    private Integer prestationCount;

    private BigDecimal priceTtc;
    
    private String statusLabel;

    private LocalDate creationDate;
    
    private LocalDateTime insuredArrivalDate;
    
    private LocalDateTime tugArrivalDate;
    
    private LocalDateTime tugAssignmentDate;
    
    private Double mileage;
    
    private String creationUser;
    private String closureUser;
    
    private String heavyWeights;
    private String holidays;
    private String night;
    private String halfPremium;
    private String fourPorteF;
    private String fourPorteK;

    private String tugGovernorateLabel;

    public String getHeavyWeights() {
		return heavyWeights;
	}

	public void setHeavyWeights(String heavyWeights) {
		this.heavyWeights = heavyWeights;
	}

	public String getHolidays() {
		return holidays;
	}

	public void setHolidays(String holidays) {
		this.holidays = holidays;
	}

	public String getNight() {
		return night;
	}

	public void setNight(String night) {
		this.night = night;
	}

	public String getHalfPremium() {
		return halfPremium;
	}

	public void setHalfPremium(String halfPremium) {
		this.halfPremium = halfPremium;
	}

	public String getFourPorteF() {
		return fourPorteF;
	}

	public void setFourPorteF(String fourPorteF) {
		this.fourPorteF = fourPorteF;
	}

	public String getFourPorteK() {
		return fourPorteK;
	}

	public void setFourPorteK(String fourPorteK) {
		this.fourPorteK = fourPorteK;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
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

    public String getPackLabel() {
        return packLabel;
    }

    public void setPackLabel(String packLabel) {
        this.packLabel = packLabel;
    }

    public String getUsageLabel() {
        return usageLabel;
    }

    public void setUsageLabel(String usageLabel) {
        this.usageLabel = usageLabel;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public LocalDate getIncidentDate() {
        return incidentDate;
    }

    public void setIncidentDate(LocalDate incidentDate) {
        this.incidentDate = incidentDate;
    }

    public String getIncidentNature() {
        return incidentNature;
    }

    public void setIncidentNature(String incidentNature) {
        this.incidentNature = incidentNature;
    }

    public String getIncidentMonth() {
        return incidentMonth;
    }

    public void setIncidentMonth(String incidentMonth) {
        this.incidentMonth = incidentMonth;
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



    public String getInterventionTimeAvgMin() {
		return interventionTimeAvgMin;
	}

	public void setInterventionTimeAvgMin(String interventionTimeAvgMin) {
		this.interventionTimeAvgMin = interventionTimeAvgMin;
	}

	public String getInterventionTimeAvg() {
        return interventionTimeAvg;
    }

    public void setInterventionTimeAvg(String interventionTimeAvg) {
        this.interventionTimeAvg = interventionTimeAvg;
    }

    public String getIncidentLocationLabel() {
        return incidentLocationLabel;
    }

    public void setIncidentLocationLabel(String incidentLocationLabel) {
        this.incidentLocationLabel = incidentLocationLabel;
    }

    public String getDestinationLocationLabel() {
        return destinationLocationLabel;
    }

    public void setDestinationLocationLabel(String destinationLocationLabel) {
        this.destinationLocationLabel = destinationLocationLabel;
    }

    public Integer getPrestationCount() {
        return prestationCount;
    }

    public void setPrestationCount(Integer prestationCount) {
        this.prestationCount = prestationCount;
    }

    public BigDecimal getPriceTtc() {
        return priceTtc;
    }

    public void setPriceTtc(BigDecimal priceTtc) {
        this.priceTtc = priceTtc;
    }

    public String getStatusLabel() {
        return statusLabel;
    }

    public void setStatusLabel(String statusLabel) {
        this.statusLabel = statusLabel;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
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

	public Double getMileage() {
		return mileage;
	}

	public void setMileage(Double mileage) {
		this.mileage = mileage;
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
	

	public String getTugGovernorateLabel() {
		return tugGovernorateLabel;
	}

	public void setTugGovernorateLabel(String tugGovernorateLabel) {
		this.tugGovernorateLabel = tugGovernorateLabel;
	}

	@Override
	public String toString() {
		return "ReportFollowUpAssistanceDTO [id=" + id + ", reference=" + reference + ", registrationNumber="
				+ registrationNumber + ", fullName=" + fullName + ", partnerId=" + partnerId + ", partnerName="
				+ partnerName + ", packLabel=" + packLabel + ", usageLabel=" + usageLabel + ", serviceType="
				+ serviceType + ", incidentDate=" + incidentDate + ", incidentNature=" + incidentNature
				+ ", incidentMonth=" + incidentMonth + ", affectedTugId=" + affectedTugId + ", affectedTugLabel="
				+ affectedTugLabel + ", interventionTimeAvgMin=" + interventionTimeAvgMin + ", interventionTimeAvg="
				+ interventionTimeAvg + ", incidentLocationLabel=" + incidentLocationLabel
				+ ", destinationLocationLabel=" + destinationLocationLabel + ", prestationCount=" + prestationCount
				+ ", priceTtc=" + priceTtc + ", statusLabel=" + statusLabel + ", creationDate=" + creationDate + "]";
	}
    
}
