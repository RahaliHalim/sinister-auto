package com.gaconnecte.auxilium.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Objects;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

/**
 * A PrestationPEC.
 */
@Entity
@Table(name = "prise_en_charge")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "prise_en_charge")
public class PriseEnCharge implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private Long id;
    @Column(name = "sinister_id")
    private Long sinisterId;
    @Column(name = "assigned_to_id")
    private Long assignedToId;
    @Column(name = "reference")
    private String refSinister;
    @Column(name = "reference_ga")
    private String reference;
    @Column(name = "incident_date")
    private LocalDate incidentDate;
    @Column(name = "declaration_date")
    private ZonedDateTime declarationDate;
    @Column(name = "company_name")
    private String companyName;
    @Column(name = "raison_sociale_assure")
    private String raisonSocialeAssure;
    @Column(name = "nom_agent_assurance")
    private String nomAgentAssurance;
    @Column(name = "code_agent_assurance")
    private String codeAgentAssurance;
    @Column(name = "numero_contrat")
    private String numeroContrat;
    @Column(name = "marque")
    private String marque;
    @Column(name = "immatriculation_vehicule")
    private String immatriculationVehicule;
    @Column(name = "immatriculation_tiers")
    private String immatriculationTiers;
    @Column(name = "raison_social_tiers")
    private String raisonSocialTiers;
    @Column(name = "creation_date")
    private ZonedDateTime creationDate;
    @Column(name = "mode_gestion")
    private String modeGestion;
    @Column(name = "casbareme")
    private Integer casbareme;
    @Column(name = "point_choc")
    private String pointChock;
    @Column(name = "state_shock")
    private Boolean stateShock;
    @Column(name = "light_shock")
    private String lightShock;
    @Column(name = "pos_ga_id")
    private Long positionGaId;
    @Column(name = "position_ga")
    private String positionGa;
    @Column(name = "reparateur")
    private String reparateur;
    @Column(name = "expert")
    private String expert;
    @Column(name = "etat_prestation")
    private String etatPrestation;
    @Column(name = "approv_pec")
    private String approvPec;
    @Column(name = "etape_prestation")
    private String etapePrestation;
    @Column(name = "step")
    private Long idEtapePrestation;
    @Column(name = "compagnie_adverse")
    private String compagnieAdverse;
    @Column(name = "montant_total_devis")
    private Double montantTotalDevis;
    @Column(name = "charge_nom")
    private String chargeNom;
    @Column(name = "charge_prenom")
    private String chargePrenom;
    @Column(name = "nom_boss")
    private String bossNom;
    @Column(name = "prenom_boss")
    private String bossPrenom;
    @Column(name = "nom_directeur")
    private String directeurNom;
    @Column(name = "prenom_directeur")
    private String directeurPrenom;
    @Column(name = "generation_bon_sortie_date")
    private LocalDate bonDeSortie;
    @Column(name = "imprim_date")
    private LocalDate imprimDate;
    @Column(name = "responsable_technique_nom")
    private String responsableNom;
    @Column(name = "responsable_technique_prenom")
    private String responsablePrenom;
    @Column(name = "mode_id")
    private Long modeId;
    @Column(name = "partner_id")
    private Long partnerId;
    @Column(name = "agency_id")
    private Long agencyId;
    
    @Column(name = "total_mo")
    private Double totalMo;
   

    @Column(name = "appointment_repair_date")
    private LocalDateTime dateRDVReparation;
    
    @Column(name = "vehicle_receipt_date")
    private LocalDateTime vehicleReceiptDate;
    
    @Column(name = "number_tele_insured")
    private String numberTeleInsured;

    @Column(name = "expert_id")
    private Long expertId;
    @Column(name = "reparateur_id")
    private Long reparateurId;

    @Column(name = "motif_cancel_refus")
    private String motifGeneral;
    
    @Column(name = "date_reprise")
    private LocalDateTime dateReprise;
    
    @Column(name = "signature_date")
    private LocalDateTime signatureDate;
    
    @Column(name = "date_envoi_demande_prise_en_charge")
    private LocalDateTime dateEnvoiDemandePec;
    
    @Column(name = "date_acceptation_de_forme")
    private LocalDateTime dateAcceptationDeForme;
    
    
    
    
    public Double getTotalMo() {
		return totalMo;
	}

	public void setTotalMo(Double totalMo) {
		this.totalMo = totalMo;
	}

	public String getMotifGeneral() {
        return motifGeneral;
    }

    public void setMotifGeneral(String motifGeneral) {
        this.motifGeneral = motifGeneral;
    }

    public String getPointChock() {
        return pointChock;
    }

    public void setPointChock(String pointChock) {
        this.pointChock = pointChock;
    }

    public Long getExpertId() {
        return expertId;
    }

    public void setExpertId(Long expertId) {
        this.expertId = expertId;
    }

    public LocalDateTime getVehicleReceiptDate() {
		return vehicleReceiptDate;
	}

	public void setVehicleReceiptDate(LocalDateTime vehicleReceiptDate) {
		this.vehicleReceiptDate = vehicleReceiptDate;
	}

    public Long getReparateurId() {
        return reparateurId;
    }

    public LocalDateTime getDateRDVReparation() {
		return dateRDVReparation;
	}

	public void setDateRDVReparation(LocalDateTime dateRDVReparation) {
		this.dateRDVReparation = dateRDVReparation;
	}

    public void setReparateurId(Long reparateurId) {
        this.reparateurId = reparateurId;
    }

    public Long getAgencyId() {
        return agencyId;
    }

    public void setAgencyId(Long agencyId) {
        this.agencyId = agencyId;
    }

    public Long getModeId() {
        return modeId;
    }

    public void setModeId(Long modeId) {
        this.modeId = modeId;
    }

    public Long getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(Long partnerId) {
        this.partnerId = partnerId;
    }

    public String getResponsableNom() {
        return responsableNom;
    }

    public void setResponsableNom(String responsableNom) {
        this.responsableNom = responsableNom;
    }

    public String getResponsablePrenom() {
        return responsablePrenom;
    }

    public void setResponsablePrenom(String responsablePrenom) {
        this.responsablePrenom = responsablePrenom;
    }

    public String getNumberTeleInsured() {
        return numberTeleInsured;
    }

    public void setNumberTeleInsured(String numberTeleInsured) {
        this.numberTeleInsured = numberTeleInsured;
    }

    public String getChargeNom() {
        return chargeNom;
    }

    public void setChargeNom(String chargeNom) {
        this.chargeNom = chargeNom;
    }

    public String getChargePrenom() {
        return chargePrenom;
    }

    public void setChargePrenom(String chargePrenom) {
        this.chargePrenom = chargePrenom;
    }

    public String getBossNom() {
        return bossNom;
    }

    public void setBossNom(String bossNom) {
        this.bossNom = bossNom;
    }

    public String getBossPrenom() {
        return bossPrenom;
    }

    public void setBossPrenom(String bossPrenom) {
        this.bossPrenom = bossPrenom;
    }

    public String getDirecteurNom() {
        return directeurNom;
    }

    public void setDirecteurNom(String directeurNom) {
        this.directeurNom = directeurNom;
    }

    public String getDirecteurPrenom() {
        return directeurPrenom;
    }

    public void setDirecteurPrenom(String directeurPrenom) {
        this.directeurPrenom = directeurPrenom;
    }

    public LocalDate getBonDeSortie() {
        return bonDeSortie;
    }

    public void setBonDeSortie(LocalDate bonDeSortie) {
        this.bonDeSortie = bonDeSortie;
    }

    public LocalDate getImprimDate() {
        return imprimDate;
    }

    public void setImprimDate(LocalDate imprimDate) {
        this.imprimDate = imprimDate;
    }

    public Long getIdEtapePrestation() {
        return idEtapePrestation;
    }

    public void setIdEtapePrestation(Long idEtapePrestation) {
        this.idEtapePrestation = idEtapePrestation;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRefSinister() {
        return refSinister;
    }

    public void setRefSinister(String refSinister) {
        this.refSinister = refSinister;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public Long getAssignedToId() {
        return assignedToId;
    }

    public void setAssignedToId(Long assignedToId) {
        this.assignedToId = assignedToId;
    }

    public LocalDate getIncidentDate() {
        return incidentDate;
    }

    public void setIncidentDate(LocalDate incidentDate) {
        this.incidentDate = incidentDate;
    }

    public ZonedDateTime getDeclarationDate() {
        return declarationDate;
    }

    public void setDeclarationDate(ZonedDateTime declarationDate) {
        this.declarationDate = declarationDate;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getNomAgentAssurance() {
        return nomAgentAssurance;
    }

    public void setNomAgentAssurance(String nomAgentAssurance) {
        this.nomAgentAssurance = nomAgentAssurance;
    }

    public String getNumeroContrat() {
        return numeroContrat;
    }

    public void setNumeroContrat(String numeroContrat) {
        this.numeroContrat = numeroContrat;
    }

    public String getMarque() {
        return marque;
    }

    public void setMarque(String marque) {
        this.marque = marque;
    }

    public String getImmatriculationVehicule() {
        return immatriculationVehicule;
    }

    public void setImmatriculationVehicule(String immatriculationVehicule) {
        this.immatriculationVehicule = immatriculationVehicule;
    }

    public String getRaisonSocialTiers() {
        return raisonSocialTiers;
    }

    public void setRaisonSocialTiers(String raisonSocialTiers) {
        this.raisonSocialTiers = raisonSocialTiers;
    }

    public ZonedDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(ZonedDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public String getModeGestion() {
        return modeGestion;
    }

    public void setModeGestion(String modeGestion) {
        this.modeGestion = modeGestion;
    }

    public Integer getCasbareme() {
        return casbareme;
    }

    public void setCasbareme(Integer casbareme) {
        this.casbareme = casbareme;
    }

    public Boolean getStateShock() {
        return stateShock;
    }

    public void setStateShock(Boolean stateShock) {
        this.stateShock = stateShock;
    }

    public String getReparateur() {
        return reparateur;
    }

    public void setReparateur(String reparateur) {
        this.reparateur = reparateur;
    }

    public String getExpert() {
        return expert;
    }

    public void setExpert(String expert) {
        this.expert = expert;
    }

    public String getEtatPrestation() {
        return etatPrestation;
    }

    public void setEtatPrestation(String etatPrestation) {
        this.etatPrestation = etatPrestation;
    }

    public String getApprovPec() {
        return approvPec;
    }

    public void setApprovPec(String approvPec) {
        this.approvPec = approvPec;
    }

    public String getEtapePrestation() {
        return etapePrestation;
    }

    public void setEtapePrestation(String etapePrestation) {
        this.etapePrestation = etapePrestation;
    }

    public String getCodeAgentAssurance() {
		return codeAgentAssurance;
	}

	public void setCodeAgentAssurance(String codeAgentAssurance) {
		this.codeAgentAssurance = codeAgentAssurance;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PriseEnCharge priseEnCharge = (PriseEnCharge) o;
        if (priseEnCharge.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), priseEnCharge.getId());
    }

    public String getImmatriculationTiers() {
        return immatriculationTiers;
    }

    public void setImmatriculationTiers(String immatriculationTiers) {
        this.immatriculationTiers = immatriculationTiers;
    }

    public String getCompagnieAdverse() {
        return compagnieAdverse;
    }

    public void setCompagnieAdverse(String compagnieAdverse) {
        this.compagnieAdverse = compagnieAdverse;
    }

    public Double getMontantTotalDevis() {
        return montantTotalDevis;
    }

    public void setMontantTotalDevis(Double montantTotalDevis) {
        this.montantTotalDevis = montantTotalDevis;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    public Long getSinisterId() {
        return sinisterId;
    }

    public void setSinisterId(Long sinisterId) {
        this.sinisterId = sinisterId;
    }

    public String getRaisonSocialeAssure() {
        return raisonSocialeAssure;
    }

    public void setRaisonSocialeAssure(String raisonSocialeAssure) {
        this.raisonSocialeAssure = raisonSocialeAssure;
    }

    public Long getPositionGaId() {
        return positionGaId;
    }

    public void setPositionGaId(Long positionGaId) {
        this.positionGaId = positionGaId;
    }

    public String getPositionGa() {
        return positionGa;
    }

    public void setPositionGa(String positionGa) {
        this.positionGa = positionGa;
    }

    public String getLightShock() {
        return lightShock;
    }

    public void setLightShock(String lightShock) {
        this.lightShock = lightShock;
    }

	public LocalDateTime getDateReprise() {
		return dateReprise;
	}

	public void setDateReprise(LocalDateTime dateReprise) {
		this.dateReprise = dateReprise;
	}

	public LocalDateTime getSignatureDate() {
		return signatureDate;
	}

	public void setSignatureDate(LocalDateTime signatureDate) {
		this.signatureDate = signatureDate;
	}

	public LocalDateTime getDateEnvoiDemandePec() {
		return dateEnvoiDemandePec;
	}

	public void setDateEnvoiDemandePec(LocalDateTime dateEnvoiDemandePec) {
		this.dateEnvoiDemandePec = dateEnvoiDemandePec;
	}

	public LocalDateTime getDateAcceptationDeForme() {
		return dateAcceptationDeForme;
	}

	public void setDateAcceptationDeForme(LocalDateTime dateAcceptationDeForme) {
		this.dateAcceptationDeForme = dateAcceptationDeForme;
	}
    
    

}
