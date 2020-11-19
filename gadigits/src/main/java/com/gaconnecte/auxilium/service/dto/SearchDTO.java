/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gaconnecte.auxilium.service.dto;

import java.time.LocalDate;

/**
 *
 * @author hannibaal
 */
public class SearchDTO {

    private static final long serialVersionUID = 1L;

    private Long partnerId;
    private String partnerLabel;

    private Long tugId;
    private String tugLabel;

    private LocalDate startDate;
    private LocalDate endDate;
    
    private String zone ;
    private Long zoneId;
    
    private Long packId;
    private Long idAgent;
    
    private Long compagnieId;
    private Long serviceId;
    private Long agentId;
    private Long modeGestionId;
    private Long chargeId;
    private Long idEtatDossier;
    
    private Long expertId;
    private Long idZoneReparateur;
    private Long reparateurId;
    
    private Long idEtapePrestation ;

    private String immatriculation;
	private String reference;
	private Long statusId ;
	
    private Long motifAnnulationId;
    private Long assignedToId ; 
    private DatatablesRequest dataTablesParameters;
    private  Long marqueId ;
    private String typeService;
 
 
	public Long getMarqueId() {
		return marqueId;
	}

	public void setMarqueId(Long marqueId) {
		this.marqueId = marqueId;
	}

	public DatatablesRequest getDataTablesParameters() {
	return dataTablesParameters;
}

public void setDataTablesParameters(DatatablesRequest dataTablesParameters) {
	this.dataTablesParameters = dataTablesParameters;
}

	public Long getAssignedToId() {
	return assignedToId;
}

public void setAssignedToId(Long assignedToId) {
	this.assignedToId = assignedToId;
}

	public Long getMotifAnnulationId() {
		return motifAnnulationId;
	}

	public void setMotifAnnulationId(Long motifAnnulationId) {
		this.motifAnnulationId = motifAnnulationId;
	}

	public Long getStatusId() {
		return statusId;
	}

	public void setStatusId(Long statusId) {
		this.statusId = statusId;
	}

	public String getImmatriculation() {
		return immatriculation;
	}

	public void setImmatriculation(String immatriculation) {
		this.immatriculation = immatriculation;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public Long getIdEtapePrestation() {
		return idEtapePrestation;
	}

	public void setIdEtapePrestation(Long idEtapePrestation) {
		this.idEtapePrestation = idEtapePrestation;
	}

	public Long getExpertId() {
		return expertId;
	}

	public void setExpertId(Long expertId) {
		this.expertId = expertId;
	}

	public Long getIdZoneReparateur() {
		return idZoneReparateur;
	}

	public void setIdZoneReparateur(Long idZoneReparateur) {
		this.idZoneReparateur = idZoneReparateur;
	}

	public Long getReparateurId() {
		return reparateurId;
	}

	public void setReparateurId(Long reparateurId) {
		this.reparateurId = reparateurId;
	}

	public Long getIdEtatDossier() {
		return idEtatDossier;
	}

	public void setIdEtatDossier(Long idEtatDossier) {
		this.idEtatDossier = idEtatDossier;
	}

	public Long getAgentId() {
		return agentId;
	}

	public void setAgentId(Long agentId) {
		this.agentId = agentId;
	}

	public Long getModeGestionId() {
		return modeGestionId;
	}

	public void setModeGestionId(Long modeGestionId) {
		this.modeGestionId = modeGestionId;
	}

	public Long getChargeId() {
		return chargeId;
	}

	public void setChargeId(Long chargeId) {
		this.chargeId = chargeId;
	}

	public Long getCompagnieId() {
		return compagnieId;
	}

	public void setCompagnieId(Long compagnieId) {
		this.compagnieId = compagnieId;
	}

	public Long getServiceId() {
		return serviceId;
	}

	public void setServiceId(Long serviceId) {
		this.serviceId = serviceId;
	}

	public Long getZoneId() {
		return zoneId;
	}

	public void setZoneId(Long zoneId) {
		this.zoneId = zoneId;
	}

	public String getZone() {
		return zone;
	}

	public void setZone(String zone) {
		this.zone = zone;
	}

	public Long getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(Long partnerId) {
        this.partnerId = partnerId;
    }

    public String getPartnerLabel() {
        return partnerLabel;
    }

    public void setPartnerLabel(String partnerLabel) {
        this.partnerLabel = partnerLabel;
    }

    public Long getTugId() {
        return tugId;
    }

    public void setTugId(Long tugId) {
        this.tugId = tugId;
    }

    public String getTugLabel() {
        return tugLabel;
    }

    public void setTugLabel(String tugLabel) {
        this.tugLabel = tugLabel;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Long getPackId() {
		return packId;
	}

	public void setPackId(Long packId) {
		this.packId = packId;
	}

	public Long getIdAgent() {
		return idAgent;
	}

	public void setIdAgent(Long idAgent) {
		this.idAgent = idAgent;
	}

	public String getTypeService() {
		return typeService;
	}

	public void setTypeService(String typeService) {
		this.typeService = typeService;
	}

	@Override
	public String toString() {
		return "SearchDTO [partnerId=" + partnerId + ", tugId=" + tugId + ", startDate=" + startDate + ", endDate="
				+ endDate + ", zoneId=" + zoneId + ", packId=" + packId + ", compagnieId=" + compagnieId
				+ ", modeGestionId=" + modeGestionId + "]";
	}

	

	

}
