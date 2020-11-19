/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gaconnecte.auxilium.domain;

import java.io.Serializable;
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
@Table(name = "report_frequency_rate")
public class ReportFrequencyRate implements Serializable {
  
    @Id
    private Long id; 

    @Column(name = "partner_id")
    private Long partnerId;
    
    @Column(name = "partner_name")
    private String partnerName;
    
    @Column(name = "usage_label")
    private String usageLabel;

    @Column(name = "incident_nature")
    private String incidentNature;

    @Column(name = "contract_count")
    private Integer contractCount;
     
    @Column(name = "creation_date")
    private LocalDate creationDate;
    
    @Column(name = "frequency_rate")
    private double frequencyRate;
    
    @Column(name = "type_service")
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




    


}
