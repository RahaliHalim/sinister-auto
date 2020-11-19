package com.gaconnecte.auxilium.domain;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "view_paiment_reparation")
public class ViewPaimentReparation {
    @Id
    private Long id;
    
    @Column(name = "numero_contrat")
    private String numeroContrat;
    
    @Column(name = "reference_ga")
    private String referenceGa;
    

    @Column(name = "compagnie_id")
    private Long compagnieId;
    
    @Column(name = "company_name")
    private String compagnie;
    
    @Column(name = "compagnie_adv_id")
    private Long compagnieAdvId;
    
    @Column(name = "compagnie_adv")
    private String compagnieAdv;
    
    @Column(name = "agency_name_id")
    private Long agencyNameId;
    
    @Column(name = "agency_name")
    private String agencyName;
    
    
    @Column(name = "governorate_id")
    private Long zoneId;
    
    @Column(name = "zoneagent")
    private String zone;
    

    @Column(name = "assure_id")
    private Long assureId;
    
    @Column(name = "insured_name")
    private String assureName;
    
    @Column(name = "date_demande")
    private LocalDate dateDemande;
    
    @Column(name = "date_bon_de_sortie")
    private LocalDate dateBonDeSortie;
    
    @Column(name = "immatriculation_vehicule")
    private String immatriculation;
    
    @Column(name = "usage_id")
    private Long usageId;
    
    @Column(name = "libelle")
    private String usageLabel;
    
    
    @Column(name = "mode_gestion_id")
    private Long modeGestionId;
    
    @Column(name = "mode_gestion")
    private String modeGestion;
    
    @Column(name = "pos_ga_id")
    private Long posGaId;
    
    @Column(name = "position_ga")
    private String positionGa;
    
    
    @Column(name = "reparateur_id")
    private Long reparateurId;
    
    @Column(name = "nom_reparateur")
    private String reparateur;
    
    @Column(name = "code_reparateur")
    private String reparateurCode;
    
    @Column(name = "ville_id")
    private Long villeId;
    
    @Column(name = "zone_reparateur")
    private String zoneReparateur;
    
    
   
    @Column(name = "cng")
    private Boolean cng;
    
    @Column(name = "expert_id")
    private Long expertId;
    
    @Column(name = "expert")
    private String expert;
    
    @Column(name = "state_shock")
    private Boolean chocBoolean;
    
    @Column(name = "light_shock")
    private String typeChoc;
    
    @Column(name = "frais_fixe")
    private String fraisFixe;
    
    
    
    
    @Column(name = "assigned_to_id")
    private Long chargeId;
    
    @Column(name = "charge_nom")
    private String chargeNom;
    
    
    @Column(name = "charge_prenom")
    private String chargePrenom;
    
    
    @Column(name = "marque_id")
    private Long marqueId;
    
    @Column(name = "marque")
    private String marque;

    @Column(name = "marque_code")
    private String marqueCode;
    
    
    @Column(name = "date_creation")
    private LocalDate creationDate;


    @Column(name = "age_vehicule")
    private String ageVehicule;
    
    
    @Column(name = "engagement_ga")
    private Double engagementGA;
    
    @Column(name = "tranche_sinistre")
    private String trancheSinistre;
    
    
    @Column(name = "participation_assure")
    private Double participationAssure;
    
    @Column(name = "avance_facture")
    private Double avanceFacture;
    
    @Column(name = "frais_dossier")
    private Double fraisDossier;
    
    @Column(name = "facture_reparateur")
    private Double factureReparateur;
    
    @Column(name = "mod_ttc")
    private Double modTtc;
    
    @Column(name = "pourcentage_mod")
    private Double pourcentageMod;
    
    @Column(name = "nature_piece")
    private String nature_piece;
    
    @Column(name = "etape_prestation")
    private String etapePrestation;

    
    @Column(name = "pdr_ttc")
    private Double pdrTtc;
    
    @Column(name = "ip_pf")
    private Double ipPf;
    
    @Column(name = "pourcentage_pr")
    private Double pourcentagePr;
    
    @Column(name = "pourcentage_ip_pf")
    private Double pourcentageIpPf;
    
    @Column(name = "nbre_heures_reparation")
    private Double nbreHeuresReparation;
    
    
    @Column(name = "nbre_heures_remplacement")
    private Double nbreHeuresRemplacement;
    
    @Column(name = "nbre_heures_peinture")
    private Double nbreHeuresPeinture;
    
    
    
    
    @Column(name = "base_calcul_mod_ip")
    private Double baseCalculModIp;
    
    @Column(name = "base_calcul_piece")
    private Double baseCalculPiece;
    
  
    @Column(name = "total_ristourne")
    private Double totalRistourne;
    
    @Column(name = "total")
    private Double total;
    
    @Column(name = "remboursement_reparateur")
    private Double remboursementReparateur;
    
    @Column(name = "date_p_circulation")
    private LocalDate datePCirculation;
    
    @Column(name = "decision")
    private String decision;
    
    
	

	public String getDecision() {
		return decision;
	}

	public void setDecision(String decision) {
		this.decision = decision;
	}

	public String getReparateurCode() {
		return reparateurCode;
	}



	public void setReparateurCode(String reparateurCode) {
		this.reparateurCode = reparateurCode;
	}



	public String getMarqueCode() {
		return marqueCode;
	}



	public void setMarqueCode(String marqueCode) {
		this.marqueCode = marqueCode;
	}



	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public String getNumeroContrat() {
		return numeroContrat;
	}



	public void setNumeroContrat(String numeroContrat) {
		this.numeroContrat = numeroContrat;
	}



	public String getReferenceGa() {
		return referenceGa;
	}



	public void setReferenceGa(String referenceGa) {
		this.referenceGa = referenceGa;
	}



	public Long getCompagnieId() {
		return compagnieId;
	}



	public void setCompagnieId(Long compagnieId) {
		this.compagnieId = compagnieId;
	}



	public String getCompagnie() {
		return compagnie;
	}



	public void setCompagnie(String compagnie) {
		this.compagnie = compagnie;
	}



	public Long getCompagnieAdvId() {
		return compagnieAdvId;
	}



	public void setCompagnieAdvId(Long compagnieAdvId) {
		this.compagnieAdvId = compagnieAdvId;
	}



	public String getCompagnieAdv() {
		return compagnieAdv;
	}



	public void setCompagnieAdv(String compagnieAdv) {
		this.compagnieAdv = compagnieAdv;
	}



	public Long getAgencyNameId() {
		return agencyNameId;
	}



	public void setAgencyNameId(Long agencyNameId) {
		this.agencyNameId = agencyNameId;
	}



	public String getAgencyName() {
		return agencyName;
	}



	public void setAgencyName(String agencyName) {
		this.agencyName = agencyName;
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



	public Long getAssureId() {
		return assureId;
	}



	public void setAssureId(Long assureId) {
		this.assureId = assureId;
	}



	public String getAssureName() {
		return assureName;
	}



	public void setAssureName(String assureName) {
		this.assureName = assureName;
	}



	public LocalDate getDateDemande() {
		return dateDemande;
	}



	public void setDateDemande(LocalDate dateDemande) {
		this.dateDemande = dateDemande;
	}



	public LocalDate getDateBonDeSortie() {
		return dateBonDeSortie;
	}



	public void setDateBonDeSortie(LocalDate dateBonDeSortie) {
		this.dateBonDeSortie = dateBonDeSortie;
	}



	public String getImmatriculation() {
		return immatriculation;
	}



	public void setImmatriculation(String immatriculation) {
		this.immatriculation = immatriculation;
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



	public Long getModeGestionId() {
		return modeGestionId;
	}



	public void setModeGestionId(Long modeGestionId) {
		this.modeGestionId = modeGestionId;
	}



	public String getModeGestion() {
		return modeGestion;
	}



	public void setModeGestion(String modeGestion) {
		this.modeGestion = modeGestion;
	}



	public Long getPosGaId() {
		return posGaId;
	}



	public void setPosGaId(Long posGaId) {
		this.posGaId = posGaId;
	}



	public String getPositionGa() {
		return positionGa;
	}



	public void setPositionGa(String positionGa) {
		this.positionGa = positionGa;
	}



	public Long getReparateurId() {
		return reparateurId;
	}



	public void setReparateurId(Long reparateurId) {
		this.reparateurId = reparateurId;
	}



	public String getReparateur() {
		return reparateur;
	}



	public void setReparateur(String reparateur) {
		this.reparateur = reparateur;
	}



	public Long getVilleId() {
		return villeId;
	}



	public void setVilleId(Long villeId) {
		this.villeId = villeId;
	}



	public String getZoneReparateur() {
		return zoneReparateur;
	}



	public void setZoneReparateur(String zoneReparateur) {
		this.zoneReparateur = zoneReparateur;
	}



	public Boolean getCng() {
		return cng;
	}



	public void setCng(Boolean cng) {
		this.cng = cng;
	}



	public Long getExpertId() {
		return expertId;
	}



	public void setExpertId(Long expertId) {
		this.expertId = expertId;
	}



	public String getExpert() {
		return expert;
	}



	public void setExpert(String expert) {
		this.expert = expert;
	}



	public Boolean getChocBoolean() {
		return chocBoolean;
	}



	public void setChocBoolean(Boolean chocBoolean) {
		this.chocBoolean = chocBoolean;
	}



	public String getTypeChoc() {
		return typeChoc;
	}



	public void setTypeChoc(String typeChoc) {
		this.typeChoc = typeChoc;
	}



	public String getFraisFixe() {
		return fraisFixe;
	}



	public void setFraisFixe(String fraisFixe) {
		this.fraisFixe = fraisFixe;
	}



	public Long getChargeId() {
		return chargeId;
	}



	public void setChargeId(Long chargeId) {
		this.chargeId = chargeId;
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



	public Long getMarqueId() {
		return marqueId;
	}



	public void setMarqueId(Long marqueId) {
		this.marqueId = marqueId;
	}



	public String getMarque() {
		return marque;
	}



	public void setMarque(String marque) {
		this.marque = marque;
	}



	public LocalDate getCreationDate() {
		return creationDate;
	}



	public void setCreationDate(LocalDate creationDate) {
		this.creationDate = creationDate;
	}



	public String getAgeVehicule() {
		return ageVehicule;
	}



	public void setAgeVehicule(String ageVehicule) {
		this.ageVehicule = ageVehicule;
	}



	public Double getEngagementGA() {
		return engagementGA;
	}



	public void setEngagementGA(Double engagementGA) {
		this.engagementGA = engagementGA;
	}



	public String getTrancheSinistre() {
		return trancheSinistre;
	}



	public void setTrancheSinistre(String trancheSinistre) {
		this.trancheSinistre = trancheSinistre;
	}



	public Double getParticipationAssure() {
		return participationAssure;
	}



	public void setParticipationAssure(Double participationAssure) {
		this.participationAssure = participationAssure;
	}



	public Double getAvanceFacture() {
		return avanceFacture;
	}



	public void setAvanceFacture(Double avanceFacture) {
		this.avanceFacture = avanceFacture;
	}



	public Double getFraisDossier() {
		return fraisDossier;
	}



	public void setFraisDossier(Double fraisDossier) {
		this.fraisDossier = fraisDossier;
	}



	public Double getFactureReparateur() {
		return factureReparateur;
	}



	public void setFactureReparateur(Double factureReparateur) {
		this.factureReparateur = factureReparateur;
	}



	public Double getModTtc() {
		return modTtc;
	}



	public void setModTtc(Double modTtc) {
		this.modTtc = modTtc;
	}



	public Double getPourcentageMod() {
		return pourcentageMod;
	}



	public void setPourcentageMod(Double pourcentageMod) {
		this.pourcentageMod = pourcentageMod;
	}



	public String getNature_piece() {
		return nature_piece;
	}



	public void setNature_piece(String nature_piece) {
		this.nature_piece = nature_piece;
	}



	public Double getPdrTtc() {
		return pdrTtc;
	}



	public void setPdrTtc(Double pdrTtc) {
		this.pdrTtc = pdrTtc;
	}



	public Double getIpPf() {
		return ipPf;
	}



	public void setIpPf(Double ipPf) {
		this.ipPf = ipPf;
	}



	public Double getPourcentagePr() {
		return pourcentagePr;
	}



	public void setPourcentagePr(Double pourcentagePr) {
		this.pourcentagePr = pourcentagePr;
	}



	public Double getPourcentageIpPf() {
		return pourcentageIpPf;
	}



	public void setPourcentageIpPf(Double pourcentageIpPf) {
		this.pourcentageIpPf = pourcentageIpPf;
	}



	public Double getNbreHeuresReparation() {
		return nbreHeuresReparation;
	}



	public void setNbreHeuresReparation(Double nbreHeuresReparation) {
		this.nbreHeuresReparation = nbreHeuresReparation;
	}



	public Double getNbreHeuresRemplacement() {
		return nbreHeuresRemplacement;
	}



	public void setNbreHeuresRemplacement(Double nbreHeuresRemplacement) {
		this.nbreHeuresRemplacement = nbreHeuresRemplacement;
	}



	public Double getNbreHeuresPeinture() {
		return nbreHeuresPeinture;
	}



	public void setNbreHeuresPeinture(Double nbreHeuresPeinture) {
		this.nbreHeuresPeinture = nbreHeuresPeinture;
	}



	public Double getBaseCalculModIp() {
		return baseCalculModIp;
	}



	public void setBaseCalculModIp(Double baseCalculModIp) {
		this.baseCalculModIp = baseCalculModIp;
	}



	public Double getBaseCalculPiece() {
		return baseCalculPiece;
	}



	public void setBaseCalculPiece(Double baseCalculPiece) {
		this.baseCalculPiece = baseCalculPiece;
	}



	public Double getTotalRistourne() {
		return totalRistourne;
	}



	public void setTotalRistourne(Double totalRistourne) {
		this.totalRistourne = totalRistourne;
	}



	public Double getTotal() {
		return total;
	}



	public void setTotal(Double total) {
		this.total = total;
	}



	public Double getRemboursementReparateur() {
		return remboursementReparateur;
	}



	public void setRemboursementReparateur(Double remboursementReparateur) {
		this.remboursementReparateur = remboursementReparateur;
	}



	public String getEtapePrestation() {
		return etapePrestation;
	}



	public void setEtapePrestation(String etapePrestation) {
		this.etapePrestation = etapePrestation;
	}



	public LocalDate getDatePCirculation() {
		return datePCirculation;
	}



	public void setDatePCirculation(LocalDate datePCirculation) {
		this.datePCirculation = datePCirculation;
	}



	@Override
	public String toString() {
		return "ViewPaimentReparation [id=" + id + ", numeroContrat=" + numeroContrat + ", referenceGa=" + referenceGa
				+ ", compagnieId=" + compagnieId + ", compagnie=" + compagnie + ", compagnieAdvId=" + compagnieAdvId
				+ ", compagnieAdv=" + compagnieAdv + ", agencyNameId=" + agencyNameId + ", agencyName=" + agencyName
				+ ", zoneId=" + zoneId + ", zone=" + zone + ", assureId=" + assureId + ", assureName=" + assureName
				+ ", dateDemande=" + dateDemande + ", dateBonDeSortie=" + dateBonDeSortie + ", immatriculation="
				+ immatriculation + ", usageId=" + usageId + ", usageLabel=" + usageLabel + ", modeGestionId="
				+ modeGestionId + ", modeGestion=" + modeGestion + ", posGaId=" + posGaId + ", positionGa=" + positionGa
				+ ", reparateurId=" + reparateurId + ", reparateur=" + reparateur + ", villeId=" + villeId
				+ ", zoneReparateur=" + zoneReparateur + ", cng=" + cng + ", expertId=" + expertId + ", expert="
				+ expert + ", chocBoolean=" + chocBoolean + ", typeChoc=" + typeChoc + ", fraisFixe=" + fraisFixe
				+ ", chargeId=" + chargeId + ", chargeNom=" + chargeNom + ", chargePrenom=" + chargePrenom
				+ ", marqueId=" + marqueId + ", marque=" + marque + ", creationDate=" + creationDate + ", ageVehicule="
				+ ageVehicule + ", engagementGA=" + engagementGA + ", trancheSinistre=" + trancheSinistre
				+ ", participationAssure=" + participationAssure + ", avanceFacture=" + avanceFacture
				+ ", fraisDossier=" + fraisDossier + ", factureReparateur=" + factureReparateur + ", modTtc=" + modTtc
				+ ", pourcentageMod=" + pourcentageMod + ", nature_piece=" + nature_piece + ", pdrTtc=" + pdrTtc
				+ ", ipPf=" + ipPf + ", pourcentagePr=" + pourcentagePr + ", pourcentageIpPf=" + pourcentageIpPf
				+ ", nbreHeuresReparation=" + nbreHeuresReparation + ", nbreHeuresRemplacement="
				+ nbreHeuresRemplacement + ", nbreHeuresPeinture=" + nbreHeuresPeinture + ", baseCalculModIp="
				+ baseCalculModIp + ", baseCalculPiece=" + baseCalculPiece + ", totalRistourne=" + totalRistourne
				+ ", total=" + total + ", remboursementReparateur=" + remboursementReparateur + "]";
	}
    
    
}
