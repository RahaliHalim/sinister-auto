package com.gaconnecte.auxilium.service.dto;

import java.time.LocalDate;

import javax.persistence.Column;

public class ViewPaimentReparationDTO {
	
private Long id;
    private String numeroContrat;
    private String referenceGa;
    private Long compagnieId;
    private String compagnie;
    private Long compagnieAdvId;
    private String compagnieAdv;
    private Long agencyNameId;
    private String agencyName;
    private Long zoneId;
    private String zone;
    private Long assureId;
    private String assureName;
    private LocalDate dateDemande;
    private LocalDate dateBonDeSortie;
    private String immatriculation;
    private Long usageId;
    private String usageLabel;
    private Long modeGestionId;
    private String modeGestion;
    private Long posGaId;
    private String positionGa;
    private Long reparateurId;
    private String reparateur;
    private String reparateurCode;

    private Long villeId;
    private String zoneReparateur;
    private Boolean cng;
    private Long expertId;
    private String expert;
    private Boolean chocBoolean;
    private String typeChoc;
    private String fraisFixe;
    private Long chargeId;
    private String chargeNom;
    private String chargePrenom;
    private Long marqueId;
    private String marque;
    private String marqueCode;

    private LocalDate creationDate;
    private String ageVehicule;
    private Double engagementGA;
    private String trancheSinistre;
    private Double participationAssure;
    private Double avanceFacture;
    private Double fraisDossier;
    private Double factureReparateur;
    private Double modTtc;
    private Double pourcentageMod;
    private String nature_piece;
    private Double pdrTtc;
    private Double ipPf;
    private Double pourcentagePr;
    private Double pourcentageIpPf;
    private Double baseCalculModIp;
    private Double baseCalculPiece;
    private Double nbreHeuresReparation;
    private Double nbreHeuresRemplacement;
    private Double nbreHeuresPeinture;
    private Double totalRistourne;
    private Double total;
    private Double remboursementReparateur;
    private String etapePrestation;
    private LocalDate datePCirculation;

    private String  decision;


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
		return "ViewPaimentReparationDTO [id=" + id + ", numeroContrat=" + numeroContrat + ", referenceGa="
				+ referenceGa + ", compagnieId=" + compagnieId + ", compagnie=" + compagnie + ", compagnieAdvId="
				+ compagnieAdvId + ", compagnieAdv=" + compagnieAdv + ", agencyNameId=" + agencyNameId + ", agencyName="
				+ agencyName + ", zoneId=" + zoneId + ", zone=" + zone + ", assureId=" + assureId + ", assureName="
				+ assureName + ", dateDemande=" + dateDemande + ", dateBonDeSortie=" + dateBonDeSortie
				+ ", immatriculation=" + immatriculation + ", usageId=" + usageId + ", usageLabel=" + usageLabel
				+ ", modeGestionId=" + modeGestionId + ", modeGestion=" + modeGestion + ", posGaId=" + posGaId
				+ ", positionGa=" + positionGa + ", reparateurId=" + reparateurId + ", reparateur=" + reparateur
				+ ", villeId=" + villeId + ", zoneReparateur=" + zoneReparateur + ", cng=" + cng + ", expertId="
				+ expertId + ", expert=" + expert + ", chocBoolean=" + chocBoolean + ", typeChoc=" + typeChoc
				+ ", fraisFixe=" + fraisFixe + ", chargeId=" + chargeId + ", chargeNom=" + chargeNom + ", chargePrenom="
				+ chargePrenom + ", marqueId=" + marqueId + ", marque=" + marque + ", creationDate=" + creationDate
				+ ", ageVehicule=" + ageVehicule + ", engagementGA=" + engagementGA + ", trancheSinistre="
				+ trancheSinistre + ", participationAssure=" + participationAssure + ", avanceFacture=" + avanceFacture
				+ ", fraisDossier=" + fraisDossier + ", factureReparateur=" + factureReparateur + ", modTtc=" + modTtc
				+ ", pourcentageMod=" + pourcentageMod + ", nature_piece=" + nature_piece + ", pdrTtc=" + pdrTtc
				+ ", ipPf=" + ipPf + ", pourcentagePr=" + pourcentagePr + ", pourcentageIpPf=" + pourcentageIpPf
				+ ", baseCalculModIp=" + baseCalculModIp + ", baseCalculPiece=" + baseCalculPiece
				+ ", nbreHeuresReparation=" + nbreHeuresReparation + ", nbreHeuresRemplacement="
				+ nbreHeuresRemplacement + ", nbreHeuresPeinture=" + nbreHeuresPeinture + ", totalRistourne="
				+ totalRistourne + ", total=" + total + ", remboursementReparateur=" + remboursementReparateur + "]";
	}


}
