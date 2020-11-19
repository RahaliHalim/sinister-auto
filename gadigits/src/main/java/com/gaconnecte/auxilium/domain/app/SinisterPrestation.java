package com.gaconnecte.auxilium.domain.app;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.gaconnecte.auxilium.domain.Delegation;
import com.gaconnecte.auxilium.domain.Governorate;
import com.gaconnecte.auxilium.domain.Observation;
import com.gaconnecte.auxilium.domain.RaisonAssistance;
import com.gaconnecte.auxilium.domain.RefNaturePanne;
import com.gaconnecte.auxilium.domain.RefTypeService;
import com.gaconnecte.auxilium.domain.User;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.io.Serializable;
import org.springframework.data.elasticsearch.annotations.Document;
import com.gaconnecte.auxilium.domain.referential.RefStatusSinister;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * A SinisterPrestation.
 */
@Entity
@Table(name = "app_sinister_prestation")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "app_sinister_prestation")
public class SinisterPrestation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "service_type_id")
    private RefTypeService serviceType;

    @ManyToOne
    @JoinColumn(name = "sinister_id")
    private Sinister sinister;

    @ManyToOne
    @JoinColumn(name = "status_id")
    private RefStatusSinister status; // TODO: separate siniter and service

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "incident_governorate_id")
    private Governorate incidentGovernorate;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "incident_location_id")
    private Delegation incidentLocation;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "destination_governorate_id")
    private Governorate destinationGovernorate;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "destination_location_id")
    private Delegation destinationLocation;

    @Column(name = "mileage")
    private Double mileage;

    @Column(name = "heavy_weights")
    private boolean heavyWeights;

    @Column(name = "holidays")
    private boolean holidays;

    @Column(name = "night")
    private boolean night;

    @Column(name = "half_premium")
    private boolean halfPremium;
    
    @Column(name = "four_porte_f")
    private Boolean fourPorteF;
    
    @Column(name = "four_porte_k")
    private Boolean fourPorteK;

    @Column(name = "tug_arrival_date")
    private LocalDateTime tugArrivalDate;

    @Column(name = "insured_arrival_date")
    private LocalDateTime insuredArrivalDate;

    @Column(name = "affected_tug_id")
    private Long affectedTugId;

    @Column(name = "affected_tug_label")
    private String affectedTugLabel;

    @Column(name = "affected_truck_id")
    private Long affectedTruckId;

    @Column(name = "affected_truck_label")
    private String affectedTruckLabel;

    @Column(name = "vat_rate")
    private Double vatRate;
    
    @Column(name = "price_ht")
    private Double priceHt;

    @Column(name = "price_ttc")
    private Double priceTtc;
    
    @Column(name = "gageo")
    private Boolean gageo;
    
    @ManyToOne
    @JoinColumn(name = "cancel_grounds_id")
    private RaisonAssistance cancelGrounds;

    @ManyToOne
    @JoinColumn(name = "reopen_grounds_id")
    private RaisonAssistance reopenGrounds;

    @ManyToOne
    @JoinColumn(name = "not_eligible_grounds_id")
    private RaisonAssistance notEligibleGrounds;
    
    @Column(name = "tug_assignment_date")
    private LocalDateTime tugAssignmentDate;

    @Column(name = "creation_date", nullable = false)
    private LocalDateTime creationDate;

    @Column(name = "closure_date")
    private LocalDateTime closureDate;

    @Column(name = "update_date")
    private LocalDate updateDate;

    @Column(name = "assignment_date")
    private LocalDate assignmentDate;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "creation_user_id")
    private User creationUser;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "closure_user_id")
    private User closureUser;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "update_user_id")
    private User updateUser;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "assigned_to_id")
    private User assignedTo;
    
    @Column(name = "cancel_date")
    private LocalDateTime cancelDate;
    
    @OneToMany(mappedBy = "sinisterPrestation", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Observation> observations = new HashSet<>();
    
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "canceled_user_id")
    private User cancelededUser;
    
	//@Column(name = "long_duration")
	//private Boolean longDuration;
	
	//@Column(name = "short_duration")
	//private Boolean shortDuration;
	
	//@Column(name = "price_per_day")
	//private Double pricePerDay;

	
	//@Column(name = "days")
	//private Double days;
	
	//@Column(name = "delivery_date")
	//private LocalDateTime deliveryDate;
	
	//@Column(name = "expected_return_date")
	//private LocalDateTime expectedReturnDate;
	
	//@Column(name = "loueur_id")
	//private Long loueurId;
	
	//@Column(name = "loueur_label")
	//private String loueurLabel;
	
	
   // @Column(name = "refused_date")
    //private LocalDateTime refusedDate; 
    
   // @ManyToOne(fetch=FetchType.LAZY)
   // @JoinColumn(name = "refused_user_id")
   // private User refusedUser;
    
    //@Column(name = "loueur_affected_date")
    //private LocalDateTime loueurAffectedDate;

	//@ManyToOne
	//@JoinColumn(name = "motif_refus_id")
	//private RaisonAssistance motifRefus;
	
	
	
	
	//@ManyToOne(fetch = FetchType.LAZY)
	//@JoinColumn(name = "delivery_governorate_id")
	///private Governorate deliveryGovernorate;
    //@Column(name = "return_date")
    //private LocalDateTime returnDate ;
    
    //@Column(name = "acquisition_date")
    //private LocalDateTime acquisitionDate ;
    

	//@ManyToOne(fetch = FetchType.LAZY)
	//@JoinColumn(name = "delivery_location_id")
	//private Delegation deliveryLocation;
	
	
	//@Column(name = "immatriculation_vr")
	//private String immatriculationVr;
	

	//@Column(name = "first_driver")
	//private String firstDriver;

	
	 
	//@Column(name = "is_generated")
	//private Boolean isGenerated;
		
	//@Column(name = "second_driver")
	//private String secondDriver;
	
    @Column(name = "is_u")
    private Boolean isU;
    
    @Column(name = "is_r")
    private Boolean isR;

    

	
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RefTypeService getServiceType() {
        return serviceType;
    }

    public void setServiceType(RefTypeService serviceType) {
        this.serviceType = serviceType;
    }

    public Sinister getSinister() {
        return sinister;
    }

    public void setSinister(Sinister sinister) {
        this.sinister = sinister;
    }

    public RefStatusSinister getStatus() {
        return status;
    }

    public void setStatus(RefStatusSinister status) {
        this.status = status;
    }

    public Governorate getIncidentGovernorate() {
        return incidentGovernorate;
    }

    public void setIncidentGovernorate(Governorate incidentGovernorate) {
        this.incidentGovernorate = incidentGovernorate;
    }

    public Delegation getIncidentLocation() {
        return incidentLocation;
    }

    public void setIncidentLocation(Delegation incidentLocation) {
        this.incidentLocation = incidentLocation;
    }

    public Governorate getDestinationGovernorate() {
        return destinationGovernorate;
    }

    public void setDestinationGovernorate(Governorate destinationGovernorate) {
        this.destinationGovernorate = destinationGovernorate;
    }

    public Delegation getDestinationLocation() {
        return destinationLocation;
    }

    public void setDestinationLocation(Delegation destinationLocation) {
        this.destinationLocation = destinationLocation;
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

    public RaisonAssistance getCancelGrounds() {
        return cancelGrounds;
    }

    public void setCancelGrounds(RaisonAssistance cancelGrounds) {
        this.cancelGrounds = cancelGrounds;
    }

    public RaisonAssistance getReopenGrounds() {
        return reopenGrounds;
    }

    public void setReopenGrounds(RaisonAssistance reopenGrounds) {
        this.reopenGrounds = reopenGrounds;
    }

    public RaisonAssistance getNotEligibleGrounds() {
        return notEligibleGrounds;
    }

    public void setNotEligibleGrounds(RaisonAssistance notEligibleGrounds) {
        this.notEligibleGrounds = notEligibleGrounds;
    }

    public LocalDateTime getTugAssignmentDate() {
        return tugAssignmentDate;
    }

    public void setTugAssignmentDate(LocalDateTime tugAssignmentDate) {
        this.tugAssignmentDate = tugAssignmentDate;
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

    public User getCreationUser() {
        return creationUser;
    }

    public void setCreationUser(User creationUser) {
        this.creationUser = creationUser;
    }

    public User getClosureUser() {
        return closureUser;
    }

    public void setClosureUser(User closureUser) {
        this.closureUser = closureUser;
    }

    public User getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(User updateUser) {
        this.updateUser = updateUser;
    }

    public User getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(User assignedTo) {
        this.assignedTo = assignedTo;
    }

    public Boolean getGageo() {
		return gageo;
	}

	public void setGageo(Boolean gageo) {
		this.gageo = gageo;
	}

	public Set<Observation> getObservations() {
		return observations;
	}

	public void setObservations(Set<Observation> observations) {
		this.observations = observations;
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

	public Boolean getFourPorteF() {
		return fourPorteF;
	}

	public Boolean getFourPorteK() {
		return fourPorteK;
	}

	public User getCancelededUser() {
		return cancelededUser;
	}

	public void setCancelededUser(User cancelededUser) {
		this.cancelededUser = cancelededUser;
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
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.id);
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
        final SinisterPrestation other = (SinisterPrestation) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "SinisterPrestation{" + "id=" + id + ", serviceType=" + serviceType + ", sinister=" + sinister + ", status=" + status + ", incidentGovernorate=" + incidentGovernorate + ", incidentLocation=" + incidentLocation + ", destinationGovernorate=" + destinationGovernorate + ", destinationLocation=" + destinationLocation + ", mileage=" + mileage + ", heavyWeights=" + heavyWeights + ", holidays=" + holidays + ", night=" + night + ", halfPremium=" + halfPremium + ", tugArrivalDate=" + tugArrivalDate + ", insuredArrivalDate=" + insuredArrivalDate + ", affectedTugId=" + affectedTugId + ", affectedTugLabel=" + affectedTugLabel + ", affectedTruckId=" + affectedTruckId + ", affectedTruckLabel=" + affectedTruckLabel + ", priceHt=" + priceHt + ", priceTtc=" + priceTtc + '}';
    }
    
}
