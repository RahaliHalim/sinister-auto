package com.gaconnecte.auxilium.domain.view;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.springframework.data.elasticsearch.annotations.Document;



@Entity
@Table(name = "contrat")
@Document(indexName = "ViewContrat")
public class ViewContrat implements Serializable{
    private static final long serialVersionUID = 1L;
    
    
    @Id
    private Long id;

    
    @Column(name = "nom_agent_assurance")
    private String nomAgentAssurance;
    
    
    @Column(name = "id_agent")
    private Long idAgent;
    
    @Column(name = "usage_label")
    private String usageLabel;
    
    
    @Column(name = "zone")
    private String zone;
    
    @Column(name = "governorate_id")
    private Long zoneId;
    
    @Column(name = "pack_id")
    private Long packId;
    
    @Column(name = "nature_pack")
    private String naturePack;
    
    @Column(name = "company_name")
    private String companyName;
    
    
    @Column(name = "company_id")
    private Long companyId;
    
    @Column(name = "creation_date")
    private LocalDate dateCreation;

    
    @Column(name = "nbre_pack")
    private Long nbrePack;


  


	public Long getCompanyId() {
		return companyId;
	}


	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}


	public Long getNbrePack() {
		return nbrePack;
	}


	public void setNbrePack(Long nbrePack) {
		this.nbrePack = nbrePack;
	}


	public Long getZoneId() {
		return zoneId;
	}


	public void setZoneId(Long zoneId) {
		this.zoneId = zoneId;
	}


	
	@Override
	public String toString() {
		return "ViewContrat [id=" + id + ", nomAgentAssurance=" + nomAgentAssurance + ", idAgent=" + idAgent
				+ ", usageLabel=" + usageLabel + ", zone=" + zone + ", zoneId=" + zoneId + ", packId=" + packId
				+ ", naturePack=" + naturePack + ", companyName=" + companyName + ", companyId=" + companyId
				+ ", dateCreation=" + dateCreation + ", nbrePack=" + nbrePack + "]";
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
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


	public String getNaturePack() {
		return naturePack;
	}


	public void setNaturePack(String naturePack) {
		this.naturePack = naturePack;
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
    
    
    
   
    
    
    
    
    
    

}
