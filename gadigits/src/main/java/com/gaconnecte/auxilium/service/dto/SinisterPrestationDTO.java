package com.gaconnecte.auxilium.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.gaconnecte.auxilium.domain.RaisonAssistance;
import com.gaconnecte.auxilium.domain.User;

/**
 * A DTO for the SinisterPrestation entity.
 */
public class SinisterPrestationDTO implements Serializable {

    private Long id;

    private Long sinisterId;
    private String reference;
    private LocalDate incidentDate;
    private String stringIncidentDate;
    private String vehicleRegistration;
    private String insuredName;
    private String insuredFirstTel;
    private String contractNumber;
    private Long serviceTypeId;
    private String serviceTypeLabel;
    
    private LocalDateTime tugAssignmentDate;

    private Long statusId;
    private String statusLabel;

    private Long incidentGovernorateId;
    private String incidentGovernorateLabel;

    private Long incidentLocationId;
    private String incidentLocationLabel;

    private Long destinationGovernorateId;
    private String destinationGovernorateLabel;

    private Long destinationLocationId;
    private String destinationLocationLabel;
    
    private Double mileage;
    private Long sinister_id;

    private boolean heavyWeights;
    private boolean holidays;
    private boolean night;
    private boolean halfPremium;
    private Boolean fourPorteF;
    private Boolean fourPorteK;

    private LocalDateTime tugArrivalDate;
    private LocalDateTime insuredArrivalDate;

    private Long affectedTugId;
    private String affectedTugLabel;

    private Long affectedTruckId;
    private String affectedTruckLabel;
    
    private Double vatRate;
    private Double priceHt;
    private Double priceTtc;
    
    private Long cancelGroundsId;
    private String cancelGroundsDescription;
    
    private Long reopenGroundsId;
    private String reopenGroundsDescription;

    private Long notEligibleGroundsId;
    private String notEligibleGroundsDescription;
    
    
    private String partnerName;
    private String pack;
    private String usage;
    private String incidentNature;
    private String incidentMonth;
    private double dmi;
    private String dmiStr;
    private Integer prestationCount;
                
    private LocalDateTime creationDate;
    private LocalDateTime closureDate;
    private LocalDate updateDate;
    private LocalDate assignmentDate;

    private Long creationUserId;
    private Long closureUserId;
    private Long updateUserId;
    private Long assignedToId;
    private Boolean gageo;
    private Set<ObservationDTO> observations = new HashSet<>();
    private LocalDateTime cancelDate;
    private Boolean isU;
    private Boolean isR;

	//private Boolean longDuration;
	
	//private Boolean shortDuration;
	
	//private Double pricePerDay;

	//private Double days;
	
	//private LocalDateTime deliveryDate;
	
	//private LocalDateTime expectedReturnDate;
	
	//private Long loueurId;
	
	//private String loueurLabel;
	
    //private LocalDateTime refusedDate; 
    
    //private Long refusedUserId;
    
    //private LocalDateTime loueurAffectedDate;

	//private Long motifRefusId;
	
   // private Long deliveryGovernorateId;
   // private String deliveryGovernorateLabel;
	
   // private Long deliveryLocationId;
   // private String deliveryLocationLabel;
   // private String immatriculationVr;
	//private String motifRefusLabel;
	
//	private LocalDateTime returnDate ;
	    
   // LocalDateTime acquisitionDate ;
	//private String firstDriver;
	//private String secondDriver;

	//private Boolean isGenerated;

    
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSinisterId() {
        return sinisterId;
    }

    public void setSinisterId(Long sinisterId) {
        this.sinisterId = sinisterId;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public LocalDate getIncidentDate() {
        return incidentDate;
    }

    public void setIncidentDate(LocalDate incidentDate) {
        this.incidentDate = incidentDate;
    }

    public String getVehicleRegistration() {
        return vehicleRegistration;
    }

    public void setVehicleRegistration(String vehicleRegistration) {
        this.vehicleRegistration = vehicleRegistration;
    }

    public String getInsuredName() {
        return insuredName;
    }

    public void setInsuredName(String insuredName) {
        this.insuredName = insuredName;
    }

    public Long getServiceTypeId() {
        return serviceTypeId;
    }

    public void setServiceTypeId(Long serviceTypeId) {
        this.serviceTypeId = serviceTypeId;
    }

    public String getServiceTypeLabel() {
        return serviceTypeLabel;
    }

    public void setServiceTypeLabel(String serviceTypeLabel) {
        this.serviceTypeLabel = serviceTypeLabel;
    }

    public Long getStatusId() {
        return statusId;
    }
    
    

    public Long getSinister_id() {
		return sinister_id;
	}

	public void setSinister_id(Long sinister_id) {
		this.sinister_id = sinister_id;
	}

	public void setStatusId(Long statusId) {
        this.statusId = statusId;
    }

    public String getStatusLabel() {
        return statusLabel;
    }

    public void setStatusLabel(String statusLabel) {
        this.statusLabel = statusLabel;
    }

    public Long getIncidentGovernorateId() {
        return incidentGovernorateId;
    }

    public void setIncidentGovernorateId(Long incidentGovernorateId) {
        this.incidentGovernorateId = incidentGovernorateId;
    }

    public String getIncidentGovernorateLabel() {
        return incidentGovernorateLabel;
    }

    public void setIncidentGovernorateLabel(String incidentGovernorateLabel) {
        this.incidentGovernorateLabel = incidentGovernorateLabel;
    }

    public Long getIncidentLocationId() {
        return incidentLocationId;
    }

    public void setIncidentLocationId(Long incidentLocationId) {
        this.incidentLocationId = incidentLocationId;
    }

    public String getIncidentLocationLabel() {
        return incidentLocationLabel;
    }

    public void setIncidentLocationLabel(String incidentLocationLabel) {
        this.incidentLocationLabel = incidentLocationLabel;
    }

    public Long getDestinationGovernorateId() {
        return destinationGovernorateId;
    }

    public void setDestinationGovernorateId(Long destinationGovernorateId) {
        this.destinationGovernorateId = destinationGovernorateId;
    }

    public String getDestinationGovernorateLabel() {
        return destinationGovernorateLabel;
    }

    public void setDestinationGovernorateLabel(String destinationGovernorateLabel) {
        this.destinationGovernorateLabel = destinationGovernorateLabel;
    }

    public Long getDestinationLocationId() {
        return destinationLocationId;
    }

    public void setDestinationLocationId(Long destinationLocationId) {
        this.destinationLocationId = destinationLocationId;
    }

    public String getDestinationLocationLabel() {
        return destinationLocationLabel;
    }

    public void setDestinationLocationLabel(String destinationLocationLabel) {
        this.destinationLocationLabel = destinationLocationLabel;
    }

    public Double getMileage() {
        return mileage;
    }

    public void setMileage(Double mileage) {
        this.mileage = mileage;
    }

    public boolean isHeavyWeights() {
        return heavyWeights;
    }

    public void setHeavyWeights(boolean heavyWeights) {
        this.heavyWeights = heavyWeights;
    }

    public boolean isHolidays() {
        return holidays;
    }

    public void setHolidays(boolean holidays) {
        this.holidays = holidays;
    }

    public boolean isNight() {
        return night;
    }

    public void setNight(boolean night) {
        this.night = night;
    }

    public boolean isHalfPremium() {
        return halfPremium;
    }

    public void setHalfPremium(boolean halfPremium) {
        this.halfPremium = halfPremium;
    }

    public LocalDateTime getTugArrivalDate() {
        return tugArrivalDate;
    }

    public void setTugArrivalDate(LocalDateTime tugArrivalDate) {
        this.tugArrivalDate = tugArrivalDate;
    }

    public LocalDateTime getInsuredArrivalDate() {
        return insuredArrivalDate;
    }

    public void setInsuredArrivalDate(LocalDateTime insuredArrivalDate) {
        this.insuredArrivalDate = insuredArrivalDate;
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

    public Long getAffectedTruckId() {
        return affectedTruckId;
    }

    public void setAffectedTruckId(Long affectedTruckId) {
        this.affectedTruckId = affectedTruckId;
    }

    public String getAffectedTruckLabel() {
        return affectedTruckLabel;
    }

    public void setAffectedTruckLabel(String affectedTruckLabel) {
        this.affectedTruckLabel = affectedTruckLabel;
    }

    public Double getVatRate() {
        return vatRate;
    }

    public void setVatRate(Double vatRate) {
        this.vatRate = vatRate;
    }

    public Double getPriceHt() {
        return priceHt;
    }

    public void setPriceHt(Double priceHt) {
        this.priceHt = priceHt;
    }

    public Double getPriceTtc() {
        return priceTtc;
    }

    public void setPriceTtc(Double priceTtc) {
        this.priceTtc = priceTtc;
    }

    public Long getCancelGroundsId() {
        return cancelGroundsId;
    }

    public void setCancelGroundsId(Long cancelGroundsId) {
        this.cancelGroundsId = cancelGroundsId;
    }

    public String getCancelGroundsDescription() {
        return cancelGroundsDescription;
    }

    public void setCancelGroundsDescription(String cancelGroundsDescription) {
        this.cancelGroundsDescription = cancelGroundsDescription;
    }

    public Long getReopenGroundsId() {
        return reopenGroundsId;
    }

    public void setReopenGroundsId(Long reopenGroundsId) {
        this.reopenGroundsId = reopenGroundsId;
    }

    public Long getNotEligibleGroundsId() {
        return notEligibleGroundsId;
    }

    public void setNotEligibleGroundsId(Long notEligibleGroundsId) {
        this.notEligibleGroundsId = notEligibleGroundsId;
    }

    public String getNotEligibleGroundsDescription() {
        return notEligibleGroundsDescription;
    }

    public void setNotEligibleGroundsDescription(String notEligibleGroundsDescription) {
        this.notEligibleGroundsDescription = notEligibleGroundsDescription;
    }

    public String getPartnerName() {
        return partnerName;
    }

    public void setPartnerName(String partnerName) {
        this.partnerName = partnerName;
    }

    public String getPack() {
        return pack;
    }

    public void setPack(String pack) {
        this.pack = pack;
    }

    public String getUsage() {
        return usage;
    }

    public void setUsage(String usage) {
        this.usage = usage;
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

    public double getDmi() {
        return dmi;
    }

    public void setDmi(double dmi) {
        this.dmi = dmi;
    }

    public String getDmiStr() {
        return dmiStr;
    }

    public void setDmiStr(String dmiStr) {
        this.dmiStr = dmiStr;
    }

    public Integer getPrestationCount() {
        return prestationCount;
    }

    public void setPrestationCount(Integer prestationCount) {
        this.prestationCount = prestationCount;
    }

    public LocalDateTime getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(LocalDateTime creationDate) {
		this.creationDate = creationDate;
	}

	public LocalDateTime getClosureDate() {
        return closureDate;
    }

    public void setClosureDate(LocalDateTime closureDate) {
        this.closureDate = closureDate;
    }

    public LocalDate getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(LocalDate updateDate) {
        this.updateDate = updateDate;
    }

    public LocalDate getAssignmentDate() {
        return assignmentDate;
    }

    public void setAssignmentDate(LocalDate assignmentDate) {
        this.assignmentDate = assignmentDate;
    }

    public Long getCreationUserId() {
        return creationUserId;
    }

    public void setCreationUserId(Long creationUserId) {
        this.creationUserId = creationUserId;
    }

    public Long getClosureUserId() {
        return closureUserId;
    }

    public void setClosureUserId(Long closureUserId) {
        this.closureUserId = closureUserId;
    }

    public Long getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(Long updateUserId) {
        this.updateUserId = updateUserId;
    }

    public Long getAssignedToId() {
        return assignedToId;
    }

    public void setAssignedToId(Long assignedToId) {
        this.assignedToId = assignedToId;
    }

    public Boolean getGageo() {
		return gageo;
	}

	public void setGageo(Boolean gageo) {
		this.gageo = gageo;
	}

	public Set<ObservationDTO> getObservations() {
		return observations;
	}

	public void setObservations(Set<ObservationDTO> observations) {
		this.observations = observations;
	}

	public String getReopenGroundsDescription() {
		return reopenGroundsDescription;
	}

	public void setReopenGroundsDescription(String reopenGroundsDescription) {
		this.reopenGroundsDescription = reopenGroundsDescription;
	}

    public String getStringIncidentDate() {
        return stringIncidentDate;
    }

    public void setStringIncidentDate(String stringIncidentDate) {
        this.stringIncidentDate = stringIncidentDate;
    }

    public String getInsuredFirstTel() {
        return insuredFirstTel;
    }

    public void setInsuredFirstTel(String insuredFirstTel) {
        this.insuredFirstTel = insuredFirstTel;
    }

    public String getContractNumber() {
        return contractNumber;
    }

    public void setContractNumber(String contractNumber) {
        this.contractNumber = contractNumber;
    }

	public Boolean isFourPorteF() {
		return fourPorteF;
	}

	public void setFourPorteF(Boolean fourPorteF) {
		this.fourPorteF = fourPorteF;
	}

	public Boolean isFourPorteK() {
		return fourPorteK;
	}

	public void setFourPorteK(Boolean fourPorteK) {
		this.fourPorteK = fourPorteK;
	}

	public LocalDateTime getCancelDate() {
		return cancelDate;
	}

	public void setCancelDate(LocalDateTime cancelDate) {
		this.cancelDate = cancelDate;
	}

	public LocalDateTime getTugAssignmentDate() {
		return tugAssignmentDate;
	}

	public void setTugAssignmentDate(LocalDateTime tugAssignmentDate) {
		this.tugAssignmentDate = tugAssignmentDate;
	}


	public Boolean getIsU() {
		return isU;
	}

	public void setIsU(Boolean isU) {
		this.isU = isU;
	}

	public Boolean getIsR() {
		return isR;
	}

	public void setIsR(Boolean isR) {
		this.isR = isR;
	}

	@Override
    public int hashCode() {
        int hash = 3;
        hash = 67 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final SinisterPrestationDTO other = (SinisterPrestationDTO) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "SinisterPrestationDTO{" + "id=" + id + ", serviceTypeId=" + serviceTypeId + ", serviceTypeLabel=" + serviceTypeLabel + ", statusId=" + statusId + ", statusLabel=" + statusLabel + ", incidentGovernorateId=" + incidentGovernorateId + ", incidentGovernorateLabel=" + incidentGovernorateLabel + ", incidentLocationId=" + incidentLocationId + ", incidentLocationLabel=" + incidentLocationLabel + ", destinationGovernorateId=" + destinationGovernorateId + ", destinationGovernorateLabel=" + destinationGovernorateLabel + ", destinationLocationId=" + destinationLocationId + ", destinationLocationLabel=" + destinationLocationLabel + ", mileage=" + mileage + ", heavyWeights=" + heavyWeights + ", holidays=" + holidays + ", night=" + night + ", halfPremium=" + halfPremium + ", tugArrivalDate=" + tugArrivalDate + ", insuredArrivalDate=" + insuredArrivalDate + ", affectedTugId=" + affectedTugId + ", affectedTugLabel=" + affectedTugLabel + ", affectedTruckId=" + affectedTruckId + ", affectedTruckLabel=" + affectedTruckLabel + ", priceHt=" + priceHt + ", priceTtc=" + priceTtc +",R= "+isR+", U= "+isU+ '}';
    }

    
}
