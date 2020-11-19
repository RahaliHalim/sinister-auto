package com.gaconnecte.auxilium.domain.referential;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import com.gaconnecte.auxilium.domain.AbstractElementaryEntity;
import com.gaconnecte.auxilium.domain.Apec;
import com.gaconnecte.auxilium.domain.RefTypeService;
import com.gaconnecte.auxilium.domain.RefModeGestion;
import com.gaconnecte.auxilium.domain.RefExclusion;
import com.gaconnecte.auxilium.domain.Reinsurer;
import com.gaconnecte.auxilium.domain.VehicleUsage;
import com.gaconnecte.auxilium.domain.prm.ApecSettings;
import com.gaconnecte.auxilium.domain.prm.Convention;
import com.gaconnecte.auxilium.domain.prm.ConventionAmendment;
import com.gaconnecte.auxilium.domain.prm.SinisterTypeSetting;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.springframework.data.elasticsearch.annotations.Field;

/**
 * cities references.
 */
@Entity
@Table(name = "ref_pack")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "ref_pack")
public class RefPack extends AbstractElementaryEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "mileage")
    private Double mileage;

    @Column(name = "price")
    private Double price;
    
    @Column(name = "grant_timing_list")
    private String grantTimingList;
    
    @Column(name = "intervention_number")
    private Double interventionNumber;

    @Column(name = "vr_days_number")
    private Double vrDaysNumber;

    @Column(name = "ceiling_to_consume")
    private Double ceilingToConsume;

    @Column(name = "combinable")
    private Boolean combinable;

    @Column(name = "home_service")
    private Boolean homeService;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "ref_pack_service_type", joinColumns = {
        @JoinColumn(name = "pack_id")}, inverseJoinColumns = {
        @JoinColumn(name = "service_type_id")})
    private Set<RefTypeService> serviceTypes = new HashSet<>();

    @Column(name = "blocked")
    private Boolean blocked;

    @ManyToOne
    @JoinColumn(name = "prm_convention_id")
    @Field(store = false)
    @JsonBackReference
    private Convention convention;

    @OneToOne
    @JoinColumn(name = "prm_amendment_id")
    private ConventionAmendment amendment;

    @ManyToOne
    @JoinColumn(name = "ref_reinsurer_id")
    private Reinsurer reinsurer;
 
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "ref_pack_exclusion", joinColumns = {
        @JoinColumn(name = "pack_id")}, inverseJoinColumns = {
        @JoinColumn(name = "exclusion_id")})
    private Set<RefExclusion> exclusions = new HashSet<>();
 
	@ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "ref_pack_usage", joinColumns = {
        @JoinColumn(name = "pack_id")}, inverseJoinColumns = {
        @JoinColumn(name = "usage_id")})
    private Set<VehicleUsage> usages = new HashSet<>(); 
    
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "ref_pack_mode_gestion", joinColumns = {
        @JoinColumn(name = "pack_id")}, inverseJoinColumns = {
        @JoinColumn(name = "mode_gestion_id")})
    private Set<RefModeGestion> modeGestions = new HashSet<>();
    
    @OneToMany(mappedBy = "pack", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<SinisterTypeSetting> sinisterTypeSettings = new ArrayList<>();

    @OneToMany(mappedBy = "pack", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<ApecSettings> apecSettings = new LinkedList<>();
    
    @Column(name = "company_part")
    private Double companyPart;

    @Column(name = "reinsurer_part")
    private Double reinsurerPart;

    @Column(name = "partner_part")
    private Double partnerPart;

    @Column(name = "apec_validation")
    private Boolean apecValidation;
    
    public RefPack() {
    }
              
    public Double getMileage() {
        return mileage;
    }

    public void setMileage(Double mileage) {
        this.mileage = mileage;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getGrantTimingList() {
		return grantTimingList;
	}

	public void setGrantTimingList(String grantTimingList) {
		this.grantTimingList = grantTimingList;
	}

	public Set<RefModeGestion> getModeGestions() {
		return modeGestions;
	}

	public void setModeGestions(Set<RefModeGestion> modeGestions) {
		this.modeGestions = modeGestions;
	}

	public Double getInterventionNumber() {
        return interventionNumber;
    }

    public void setInterventionNumber(Double interventionNumber) {
        this.interventionNumber = interventionNumber;
    }

    public Double getVrDaysNumber() {
        return vrDaysNumber;
    }

    public void setVrDaysNumber(Double vrDaysNumber) {
        this.vrDaysNumber = vrDaysNumber;
    }

    public Double getCeilingToConsume() {
        return ceilingToConsume;
    }

    public void setCeilingToConsume(Double ceilingToConsume) {
        this.ceilingToConsume = ceilingToConsume;
    }

    public Boolean getCombinable() {
        return combinable;
    }

    public void setCombinable(Boolean combinable) {
        this.combinable = combinable;
    }

    public Boolean getHomeService() {
        return homeService;
    }

    public void setHomeService(Boolean homeService) {
        this.homeService = homeService;
    }

    public Set<RefTypeService> getServiceTypes() {
        return serviceTypes;
    }

    public void setServiceTypes(Set<RefTypeService> serviceTypes) {
        this.serviceTypes = serviceTypes;
    }


    public Boolean getBlocked() {
        return blocked;
    }

    public void setBlocked(Boolean blocked) {
        this.blocked = blocked;
    }

    public Convention getConvention() {
        return convention;
    }

    public void setConvention(Convention convention) {
        this.convention = convention;
    }

    public ConventionAmendment getAmendment() {
        return amendment;
    }

    public void setAmendment(ConventionAmendment amendment) {
        this.amendment = amendment;
    }

    public Reinsurer getReinsurer() {
        return reinsurer;
    }

    public void setReinsurer(Reinsurer reinsurer) {
        this.reinsurer = reinsurer;
    }

    public Set<VehicleUsage> getUsages() {
        return usages;
    }

    public void setUsages(Set<VehicleUsage> usages) {
        this.usages = usages;
    }
    public Set<RefExclusion> getExclusions() {
        return exclusions;
    }

    public void setExclusions(Set<RefExclusion> exclusions) {
        this.exclusions = exclusions;
    }

    public List<SinisterTypeSetting> getSinisterTypeSettings() {
        return sinisterTypeSettings;
    }

    public void setSinisterTypeSettings(List<SinisterTypeSetting> sinisterTypeSettings) {
        this.sinisterTypeSettings = sinisterTypeSettings;
    }
    
    public Double getCompanyPart() {
        return companyPart;
    }

    public void setCompanyPart(Double companyPart) {
        this.companyPart = companyPart;
    }

    public Double getReinsurerPart() {
        return reinsurerPart;
    }

    public void setReinsurerPart(Double reinsurerPart) {
        this.reinsurerPart = reinsurerPart;
    }

    public Double getPartnerPart() {
        return partnerPart;
    }

    public void setPartnerPart(Double partnerPart) {
        this.partnerPart = partnerPart;
    }

    public List<ApecSettings> getApecSettings() {
        return apecSettings;
    }

    public void setApecSettings(List<ApecSettings> apecSettings) {
        this.apecSettings = apecSettings;
    }

    public Boolean getApecValidation() {
        return apecValidation;
    }

    public void setApecValidation(Boolean apecValidation) {
        this.apecValidation = apecValidation;
    }

    @Override
	public String toString() {
		return "RefPack [mileage=" + mileage + ", grantTimingList=" + grantTimingList + ", interventionNumber="
				+ interventionNumber + ", vrDaysNumber=" + vrDaysNumber + ", ceilingToConsume=" + ceilingToConsume
				+ ", serviceTypes=" + serviceTypes + ", blocked=" + blocked + ", convention=" + convention
				+ ", exclusions=" + exclusions + ", usages=" + usages + ", modeGestions=" + modeGestions + "]";
	}

}
