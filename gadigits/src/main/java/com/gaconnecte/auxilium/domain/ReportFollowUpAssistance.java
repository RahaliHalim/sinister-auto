/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gaconnecte.auxilium.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author hannibaal
 */
@Entity
@Table(name = "report_follow_up_assistance")
public class ReportFollowUpAssistance implements Serializable {
    
    @Id
    private Long id; 

    @Column(name = "reference")
    private String reference;

    @Column(name = "registration_number")
    private String registrationNumber;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "partner_id")
    private Long partnerId;
    
    @Column(name = "partner_name")
    private String partnerName;
    
    @Column(name = "pack_label")
    private String packLabel; 

    @Column(name = "usage_label")
    private String usageLabel; 

    @Column(name = "service_type")
    private String serviceType;

    @Column(name = "incident_date")
    private LocalDate incidentDate;
    
    @Column(name = "incident_nature")
    private String incidentNature;
    
    @Column(name = "incident_month")
    private String incidentMonth;
    
    @Column(name = "affected_tug_id")
    private Long affectedTugId;

    @Column(name = "affected_tug_label")
    private String affectedTugLabel; 

    @Column(name = "intervention_time_avg_min")
    private String interventionTimeAvgMin;
    
    @Column(name = "intervention_time_avg")
    private String interventionTimeAvg;

    @Column(name = "incident_location_label")
    private String incidentLocationLabel;

    @Column(name = "destination_location_label")
    private String destinationLocationLabel;

    @Column(name = "prestation_count")
    private Integer prestationCount;

    @Column(name = "price_ttc")
    private BigDecimal priceTtc;
    
    @Column(name = "status_label")
    private String statusLabel;

    @Column(name = "creation_date")
    private LocalDate creationDate;
    
    @Column(name = "insured_arrival_date")
    private LocalDateTime insuredArrivalDate;
    
    @Column(name = "tug_arrival_date")
    private LocalDateTime tugArrivalDate;
    
    @Column(name = "tug_assignment_date")
    private LocalDateTime tugAssignmentDate;
    
    @Column(name = "mileage")
    private Double mileage;
    
    @Column(name = "creation_user")
    private String creationUser;
    
    @Column(name = "closure_user")
    private String closureUser;
    
    @Column(name = "heavy_weights")
    private String heavyWeights;

    @Column(name = "holidays")
    private String holidays;

    @Column(name = "night")
    private String night;

    @Column(name = "half_premium")
    private String halfPremium;
    
    @Column(name = "four_porte_f")
    private String fourPorteF;
    
    @Column(name = "four_porte_k")
    private String fourPorteK;
    
    
    @Column(name = "tug_governorate_label")
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

 

    @Override
	public String toString() {
		return "ReportFollowUpAssistance [id=" + id + ", reference=" + reference + ", registrationNumber="
				+ registrationNumber + ", fullName=" + fullName + ", partnerId=" + partnerId + ", partnerName="
				+ partnerName + ", packLabel=" + packLabel + ", usageLabel=" + usageLabel + ", serviceType="
				+ serviceType + ", incidentDate=" + incidentDate + ", incidentNature=" + incidentNature
				+ ", incidentMonth=" + incidentMonth + ", affectedTugId=" + affectedTugId + ", affectedTugLabel="
				+ affectedTugLabel + ", interventionTimeAvgMin=" + interventionTimeAvgMin + ", interventionTimeAvg="
				+ interventionTimeAvg + ", incidentLocationLabel=" + incidentLocationLabel
				+ ", destinationLocationLabel=" + destinationLocationLabel + ", prestationCount=" + prestationCount
				+ ", priceTtc=" + priceTtc + ", statusLabel=" + statusLabel + ", creationDate=" + creationDate + "]";
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


}
