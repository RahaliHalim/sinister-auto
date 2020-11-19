/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gaconnecte.auxilium.domain.view;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author hannibaal
 */
@Entity
@Table(name = "view_sinister_pec_monitoring")
public class ViewSinisterPecMonitoring {

    @Id
    private Long id;
    @Column(name = "reference_ga")
    private String referenceGa;
    
    @Column(name = "reference_cmp")
    private String referenceCmp;
    
    @Column(name = "partner_id")
    private Long partnerId;
    
    @Column(name = "partner_name")
    private String partnerName;
    
    @Column(name = "agency_id")
    private Long agencyId;
    
    @Column(name = "agency_code")
    private String agencyCode;
    
    @Column(name = "agency_name")
    private String agencyName;
    
    @Column(name = "insured_name")
    private String insuredName;
    
    @Column(name = "vehicle_id")
    private Long vehicleId;
    
    @Column(name = "immatriculation")
    private String immatriculation;
    
    @Column(name = "brand_id")
    private Long brandId;
    
    @Column(name = "brand_label")
    private String brandLabel;
    
    @Column(name = "brand_code")
    private String marqueCode;
    
    @Column(name = "model_id")
    private Long modelId;
    
    @Column(name = "model_label")
    private String modelLabel;
    
    @Column(name = "usage_id")
    private Long usageId;
    
    @Column(name = "usage_label")
    private String usageLabel;
    
    @Column(name = "reparator_rs")
    private String reparatorRs;
    
    
    @Column(name = "reparator_code")
    private String reparateurCode;
    
   
    
    @Column(name = "cng")
    private Boolean cng;
    
    @Column(name = "expert_rs")
    private String expertRs;
    
    @Column(name = "pack_name")
    private String packName;
    
    @Column(name = "agency_governorate_id")
    private Long agencyGovernorateId;
    
    @Column(name = "agency_governorate_label")
    private String agencyGovernorateLabel;
    
    @Column(name = "incident_date")
    private LocalDate incidentDate;
    
    @Column(name = "date_affect_expert")
    private LocalDateTime dateAffectExpert;
    
    @Column(name = "date_affect_reparateur")
    private LocalDateTime dateAffectReparateur;
    
    @Column(name = "sending_date")
    private LocalDateTime sendingDate;
    
    @Column(name = "receipt_vehicle_date")
    private LocalDateTime receiptVehicleDate;
    
    @Column(name = "rdv_reparateur_date")
    private LocalDateTime rdvReparateurDate;
    
    @Column(name = "managment_mode_id")
    private Long managmentModeId;
    
    @Column(name = "managment_mode_label")
    private String managmentModeLabel;
    
    @Column(name = "ga_position_id")
    private Long gaPositionId;
    
    @Column(name = "ga_position_label")
    private String gaPositionLabel;
    
    @Column(name = "tierse_partner_id")
    private Long tiersePartnerId;
    
    @Column(name = "tierse_partner_name")
    private String tiersePartnerName;
    
    @Column(name = "assigned_to_id")
    private Long assignedToId;
    
    @Column(name = "assigned_to_name")
    private String assignedToName;
    
    @Column(name = "bareme_id")
    private Long baremeId;
    
    @Column(name = "bareme_rate")
    private Integer baremeRate;

    @Column(name = "step_id")
    private Long stepId;
    
    @Column(name = "step_name")
    private String stepName;
    
  
    @Column(name = "decision")
    private String decision;
    
    @Column(name = "delai_moyen_leve_reserve")
    private String delaiMoyenLeveReserve;
    
    @Column(name = "date_change_statut")
    private String dateChangeStatut;
    
    @Column(name = "date_reserve")
    private String dateReserves;

    @Column(name = "vr_oui_non")
    private String vr;
    
    @Column(name = "vr_days_number")
    private Long vrDays;
    
    @Column(name = "delai_moyen_dispo_assure")
    private String delaiMoyenDispoAssure;
    @Column(name = "type_shock")
    private String typeShock;
    @Column(name = "delai_reparation")
    private String delaiReparation;
    
   
    @Column(name = "delai_moyen_affectation_rep")
    private String delaiMoyenAffectationRep;
    @Column(name = "delai_administratif")
    private String delaiAdministratif;
    
  @Column(name = "delai_moyen_decision")
    private String delaiMoyenDecision;
  
  
    @Column(name = "delai_envoi_estimation")
    private String delaiEnvoiEstimation;
    @Column(name = "delai_etablissement_devis")
    private String delaiEtablissementDevis;
    
    @Column(name = "delai_affectation_expert")
    private String delaiAffectationExpert;
    
    @Column(name = "delai_avis_expert")
    private String delaiAvisExpert;
    
    @Column(name = "date_p_circulation")
    private LocalDate datePCirculation;
    
    @Column(name = "part_assure")
    private Double partAssure;
    
    @Column(name = "delai_verification_devis")
    private String delaiVerificationDevis;
    
    @Column(name = "date_facturation")
    private LocalDateTime dateFacturation; 
    
    @Column(name = "delai_bon_sortie")
    private String delaiBonSortie;
    
    @Column(name = "delaiApec")
    private String delaiApec;
    

    
    @Column(name = "ecart_reparation")
    private String ecartReparation;
      
    @Column(name = "ecart_acceptation_fond")
    private String ecartAcceptationFond;
    
    @Column(name = "ecart_acceptation_forme")
    private String ecartAcceptationForme;
    
	public String getMarqueCode() {
		return marqueCode;
	}

	public void setMarqueCode(String marqueCode) {
		this.marqueCode = marqueCode;
	}

	public String getReparateurCode() {
		return reparateurCode;
	}

	public void setReparateurCode(String reparateurCode) {
		this.reparateurCode = reparateurCode;
	}

	public LocalDateTime getDateAffectExpert() {
		return dateAffectExpert;
	}

	public void setDateAffectExpert(LocalDateTime dateAffectExpert) {
		this.dateAffectExpert = dateAffectExpert;
	}

	public LocalDateTime getDateAffectReparateur() {
		return dateAffectReparateur;
	}

	public void setDateAffectReparateur(LocalDateTime dateAffectReparateur) {
		this.dateAffectReparateur = dateAffectReparateur;
	}

	public String getDelaiMoyenDispoAssure() {
		return delaiMoyenDispoAssure;
	}

	public void setDelaiMoyenDispoAssure(String delaiMoyenDispoAssure) {
		this.delaiMoyenDispoAssure = delaiMoyenDispoAssure;
	}

	public String getDelaiMoyenAffectationRep() {
		return delaiMoyenAffectationRep;
	}

	public void setDelaiMoyenAffectationRep(String delaiMoyenAffectationRep) {
		this.delaiMoyenAffectationRep = delaiMoyenAffectationRep;
	}

	public String getDelaiAdministratif() {
		return delaiAdministratif;
	}

	public void setDelaiAdministratif(String delaiAdministratif) {
		this.delaiAdministratif = delaiAdministratif;
	}

	public String getDelaiEnvoiEstimation() {
		return delaiEnvoiEstimation;
	}

	public void setDelaiEnvoiEstimation(String delaiEnvoiEstimation) {
		this.delaiEnvoiEstimation = delaiEnvoiEstimation;
	}

	public String getDelaiEtablissementDevis() {
		return delaiEtablissementDevis;
	}

	public void setDelaiEtablissementDevis(String delaiEtablissementDevis) {
		this.delaiEtablissementDevis = delaiEtablissementDevis;
	}

	public String getDelaiAffectationExpert() {
		return delaiAffectationExpert;
	}

	public void setDelaiAffectationExpert(String delaiAffectationExpert) {
		this.delaiAffectationExpert = delaiAffectationExpert;
	}

	public String getDelaiAvisExpert() {
		return delaiAvisExpert;
	}

	public void setDelaiAvisExpert(String delaiAvisExpert) {
		this.delaiAvisExpert = delaiAvisExpert;
	}

	public String getDelaiMoyenDecision() {
		return delaiMoyenDecision;
	}

	public void setDelaiMoyenDecision(String delaiMoyenDecision) {
		this.delaiMoyenDecision = delaiMoyenDecision;
	}

	public String getDecision() {
		return decision;
	}

	public void setDecision(String decision) {
		this.decision = decision;
	}

	public String getDelaiMoyenLeveReserve() {
		return delaiMoyenLeveReserve;
	}

	public void setDelaiMoyenLeveReserve(String delaiMoyenLeveReserve) {
		this.delaiMoyenLeveReserve = delaiMoyenLeveReserve;
	}

	public String getVr() {
		return vr;
	}

	public void setVr(String vr) {
		this.vr = vr;
	}

	public Long getVrDays() {
		return vrDays;
	}

	public void setVrDays(Long vrDays) {
		this.vrDays = vrDays;
	}

	

	public String getTypeShock() {
		return typeShock;
	}

	public void setTypeShock(String typeShock) {
		this.typeShock = typeShock;
	}

	public String getDelaiReparation() {
		return delaiReparation;
	}

	public void setDelaiReparation(String delaiReparation) {
		this.delaiReparation = delaiReparation;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReferenceGa() {
        return referenceGa;
    }

    public void setReferenceGa(String referenceGa) {
        this.referenceGa = referenceGa;
    }

    public String getReferenceCmp() {
        return referenceCmp;
    }

    public void setReferenceCmp(String referenceCmp) {
        this.referenceCmp = referenceCmp;
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

    public Long getAgencyId() {
        return agencyId;
    }

    public void setAgencyId(Long agencyId) {
        this.agencyId = agencyId;
    }

    public String getAgencyCode() {
        return agencyCode;
    }

    public void setAgencyCode(String agencyCode) {
        this.agencyCode = agencyCode;
    }

    public String getAgencyName() {
        return agencyName;
    }

    public void setAgencyName(String agencyName) {
        this.agencyName = agencyName;
    }

    public String getInsuredName() {
        return insuredName;
    }

    public void setInsuredName(String insuredName) {
        this.insuredName = insuredName;
    }

    public Long getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(Long vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getImmatriculation() {
        return immatriculation;
    }

    public void setImmatriculation(String immatriculation) {
        this.immatriculation = immatriculation;
    }

    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }

    public String getBrandLabel() {
        return brandLabel;
    }

    public void setBrandLabel(String brandLabel) {
        this.brandLabel = brandLabel;
    }

    public Long getModelId() {
        return modelId;
    }

    public void setModelId(Long modelId) {
        this.modelId = modelId;
    }

    public String getModelLabel() {
        return modelLabel;
    }

    public void setModelLabel(String modelLabel) {
        this.modelLabel = modelLabel;
    }

    public Long getUsageId() {
        return usageId;
    }

    public void setUsageId(Long usageId) {
        this.usageId = usageId;
    }

    public String getUsageLabel() {
        return usageLabel;
    }

    public void setUsageLabel(String usageLabel) {
        this.usageLabel = usageLabel;
    }

    public String getReparatorRs() {
        return reparatorRs;
    }

    public void setReparatorRs(String reparatorRs) {
        this.reparatorRs = reparatorRs;
    }

    public Boolean getCng() {
        return cng;
    }

    public void setCng(Boolean cng) {
        this.cng = cng;
    }

    public String getExpertRs() {
        return expertRs;
    }

    public void setExpertRs(String expertRs) {
        this.expertRs = expertRs;
    }

    public String getPackName() {
        return packName;
    }

    public void setPackName(String packName) {
        this.packName = packName;
    }

    public Long getAgencyGovernorateId() {
        return agencyGovernorateId;
    }

    public void setAgencyGovernorateId(Long agencyGovernorateId) {
        this.agencyGovernorateId = agencyGovernorateId;
    }

    public String getAgencyGovernorateLabel() {
        return agencyGovernorateLabel;
    }

    public void setAgencyGovernorateLabel(String agencyGovernorateLabel) {
        this.agencyGovernorateLabel = agencyGovernorateLabel;
    }

    public LocalDate getIncidentDate() {
        return incidentDate;
    }

    public void setIncidentDate(LocalDate incidentDate) {
        this.incidentDate = incidentDate;
    }

    public LocalDateTime getSendingDate() {
        return sendingDate;
    }

    public void setSendingDate(LocalDateTime sendingDate) {
        this.sendingDate = sendingDate;
    }

    public Long getManagmentModeId() {
        return managmentModeId;
    }

    public void setManagmentModeId(Long managmentModeId) {
        this.managmentModeId = managmentModeId;
    }

    public String getManagmentModeLabel() {
        return managmentModeLabel;
    }

    public void setManagmentModeLabel(String managmentModeLabel) {
        this.managmentModeLabel = managmentModeLabel;
    }

    public Long getGaPositionId() {
        return gaPositionId;
    }

    public void setGaPositionId(Long gaPositionId) {
        this.gaPositionId = gaPositionId;
    }

    public String getGaPositionLabel() {
        return gaPositionLabel;
    }

    public void setGaPositionLabel(String gaPositionLabel) {
        this.gaPositionLabel = gaPositionLabel;
    }

    public Long getTiersePartnerId() {
        return tiersePartnerId;
    }

    public void setTiersePartnerId(Long tiersePartnerId) {
        this.tiersePartnerId = tiersePartnerId;
    }

    public String getTiersePartnerName() {
        return tiersePartnerName;
    }

    public void setTiersePartnerName(String tiersePartnerName) {
        this.tiersePartnerName = tiersePartnerName;
    }

    public Long getAssignedToId() {
        return assignedToId;
    }

    public void setAssignedToId(Long assignedToId) {
        this.assignedToId = assignedToId;
    }

    public String getAssignedToName() {
        return assignedToName;
    }

    public void setAssignedToName(String assignedToName) {
        this.assignedToName = assignedToName;
    }

    public Long getBaremeId() {
        return baremeId;
    }

    public void setBaremeId(Long baremeId) {
        this.baremeId = baremeId;
    }

    public Integer getBaremeRate() {
        return baremeRate;
    }

    public void setBaremeRate(Integer baremeRate) {
        this.baremeRate = baremeRate;
    }

    public Long getStepId() {
        return stepId;
    }

    public void setStepId(Long stepId) {
        this.stepId = stepId;
    }

    public String getStepName() {
        return stepName;
    }

    public void setStepName(String stepName) {
        this.stepName = stepName;
    }
    
    


	public String getDateChangeStatut() {
		return dateChangeStatut;
	}

	public void setDateChangeStatut(String dateChangeStatut) {
		this.dateChangeStatut = dateChangeStatut;
	}

	public String getDateReserves() {
		return dateReserves;
	}

	public void setDateReserves(String dateReserves) {
		this.dateReserves = dateReserves;
	}

	public LocalDateTime getReceiptVehicleDate() {
		return receiptVehicleDate;
	}

	public void setReceiptVehicleDate(LocalDateTime receiptVehicleDate) {
		this.receiptVehicleDate = receiptVehicleDate;
	}

	public LocalDateTime getRdvReparateurDate() {
		return rdvReparateurDate;
	}

	public void setRdvReparateurDate(LocalDateTime rdvReparateurDate) {
		this.rdvReparateurDate = rdvReparateurDate;
	}

	public LocalDate getDatePCirculation() {
		return datePCirculation;
	}

	public void setDatePCirculation(LocalDate datePCirculation) {
		this.datePCirculation = datePCirculation;
	}

	public Double getPartAssure() {
		return partAssure;
	}

	public void setPartAssure(Double partAssure) {
		this.partAssure = partAssure;
	}

	public String getDelaiVerificationDevis() {
		return delaiVerificationDevis;
	}

	public void setDelaiVerificationDevis(String delaiVerificationDevis) {
		this.delaiVerificationDevis = delaiVerificationDevis;
	}

	public LocalDateTime getDateFacturation() {
		return dateFacturation;
	}

	public void setDateFacturation(LocalDateTime dateFacturation) {
		this.dateFacturation = dateFacturation;
	}

	public String getDelaiBonSortie() {
		return delaiBonSortie;
	}

	public void setDelaiBonSortie(String delaiBonSortie) {
		this.delaiBonSortie = delaiBonSortie;
	}

	public String getDelaiApec() {
		return delaiApec;
	}

	public void setDelaiApec(String delaiApec) {
		this.delaiApec = delaiApec;
	}

	public String getEcartReparation() {
		return ecartReparation;
	}

	public void setEcartReparation(String ecartReparation) {
		this.ecartReparation = ecartReparation;
	}

	public String getEcartAcceptationFond() {
		return ecartAcceptationFond;
	}

	public void setEcartAcceptationFond(String ecartAcceptationFond) {
		this.ecartAcceptationFond = ecartAcceptationFond;
	}

	public String getEcartAcceptationForme() {
		return ecartAcceptationForme;
	}

	public void setEcartAcceptationForme(String ecartAcceptationForme) {
		this.ecartAcceptationForme = ecartAcceptationForme;
	}

	@Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.id);
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
        final ViewSinisterPecMonitoring other = (ViewSinisterPecMonitoring) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
	public String toString() {
		return "ViewSinisterPecMonitoring [id=" + id + ", referenceGa=" + referenceGa + ", referenceCmp=" + referenceCmp
				+ ", partnerId=" + partnerId + ", partnerName=" + partnerName + ", agencyId=" + agencyId
				+ ", agencyCode=" + agencyCode + ", agencyName=" + agencyName + ", insuredName=" + insuredName
				+ ", vehicleId=" + vehicleId + ", immatriculation=" + immatriculation + ", brandId=" + brandId
				+ ", brandLabel=" + brandLabel + ", modelId=" + modelId + ", modelLabel=" + modelLabel + ", usageId="
				+ usageId + ", usageLabel=" + usageLabel + ", reparatorRs=" + reparatorRs + ", cng=" + cng
				+ ", expertRs=" + expertRs + ", packName=" + packName + ", agencyGovernorateId=" + agencyGovernorateId
				+ ", agencyGovernorateLabel=" + agencyGovernorateLabel + ", incidentDate=" + incidentDate
				+ ", sendingDate=" + sendingDate + ", managmentModeId=" + managmentModeId + ", managmentModeLabel="
				+ managmentModeLabel + ", gaPositionId=" + gaPositionId + ", gaPositionLabel=" + gaPositionLabel
				+ ", tiersePartnerId=" + tiersePartnerId + ", tiersePartnerName=" + tiersePartnerName
				+ ", assignedToId=" + assignedToId + ", assignedToName=" + assignedToName + ", baremeId=" + baremeId
				+ ", baremeRate=" + baremeRate + ", stepId=" + stepId + ", stepName=" + stepName + ", decision="
				+ decision + ", delaiMoyenLeveReserve=" + delaiMoyenLeveReserve + ", dateChangeStatut="
				+ dateChangeStatut + ", dateReserves=" + dateReserves + ", vr=" + vr + ", vrDays=" + vrDays
				+ ", delaiMoyenDispoAssure=" + delaiMoyenDispoAssure + ", typeShock=" + typeShock + ", delaiReparation="
				+ delaiReparation + ", delaiMoyenAffectationRep=" + delaiMoyenAffectationRep + ", delaiAdministratif="
				+ delaiAdministratif + ", delaiMoyenDecision=" + delaiMoyenDecision + ", delaiEnvoiEstimation="
				+ delaiEnvoiEstimation + ", delaiEtablissementDevis=" + delaiEtablissementDevis
				+ ", delaiAffectationExpert=" + delaiAffectationExpert + ", delaiAvisExpert=" + delaiAvisExpert + "]";
	}
    
    
}
