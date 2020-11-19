package com.gaconnecte.auxilium.domain.view;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;


@Entity
@Table(name = "view_bonification")
public class ViewBonification implements Serializable{
    private static final long serialVersionUID = 1L;
    
    
    
    @Id
    private Long id;

    
    @Column(name = "compagnie_id")
    private Long compagnieId;
    
    
    @Column(name = "compagnie")
    private String compagnie;
    
    @Column(name = "numero_contrat")
    private String numContrat;
    
    
    @Column(name = "governorate_id")
    private Long zoneId;
    
    @Column(name = "zoneagent")
    private String zone;
    
    @Column(name = "pack_id")
    private Long packId; 
    
    @Column(name = "pack_souscrit")
    private String pack; 
    
    @Column(name = "referencega")
    private String referenceGa;
    
    @Column(name = "reparateur_id")
    private Long reparateurId;
    
    @Column(name = "nom_reparateur")
    private String reparateurName;
    
    @Column(name = "reparator_code")
    private String reparateurCode;
    
    @Column(name = "ville_id")
    private Long idZoneReparateur;
    
    @Column(name = "zone_reparateur")
    private String zoneReparateur;
    
    @Column(name = "date_bon_sortie")
    private LocalDate dateBonSortie;
    
    @Column(name = "mois_bon_sortie")
    private String moisBonSortie;
    
    @Column(name = "mod_ip")
    private BigDecimal mdIp;
    
    @Column(name = "piece")
    private Long piece;
    
    @Column(name = "part_ga")
    private BigDecimal partGa;
    
    @Column(name = "part_cie")
    private BigDecimal partCie;
    
    @Column(name = "date_creation")
    private LocalDate creationDate;
    
    @Column(name = "service_id")
    private Long serviceId;
    
    @Column(name = "service")
    private String service;
    
    @Column(name = "charge_id")
    private Long chargeId;
    
    @Column(name = "charge")
    private String charge;
    
    @Column(name = "expert_id")
    private Long expertId;
    
    @Column(name = "expert")
    private String expert;
    
    @Column(name = "marque")
    private String marque;

    @Column(name = "brand_code")
    private String marqueCode;

    
    @Column(name = "base_calcul_mod_ip")
    private BigDecimal baseCalculModIp;
    
    @Column(name = "base_calcul_piece")
    private BigDecimal baseCalculPiece;
    

    @Column(name = "date_ouverture")
    private LocalDate dateOuverture;
    

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

	

	public String getNumContrat() {
		return numContrat;
	}

	public void setNumContrat(String numContrat) {
		this.numContrat = numContrat;
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

	public Long getPackId() {
		return packId;
	}

	public void setPackId(Long packId) {
		this.packId = packId;
	}

	public String getPack() {
		return pack;
	}

	public void setPack(String pack) {
		this.pack = pack;
	}

	public String getReferenceGa() {
		return referenceGa;
	}

	public void setReferenceGa(String referenceGa) {
		this.referenceGa = referenceGa;
	}

	public Long getReparateurId() {
		return reparateurId;
	}

	public void setReparateurId(Long reparateurId) {
		this.reparateurId = reparateurId;
	}

	public String getReparateurName() {
		return reparateurName;
	}

	public void setReparateurName(String reparateurName) {
		this.reparateurName = reparateurName;
	}

	public Long getIdZoneReparateur() {
		return idZoneReparateur;
	}

	public void setIdZoneReparateur(Long idZoneReparateur) {
		this.idZoneReparateur = idZoneReparateur;
	}

	public String getZoneReparateur() {
		return zoneReparateur;
	}

	public void setZoneReparateur(String zoneReparateur) {
		this.zoneReparateur = zoneReparateur;
	}

	public LocalDate getDateBonSortie() {
		return dateBonSortie;
	}

	public void setDateBonSortie(LocalDate dateBonSortie) {
		this.dateBonSortie = dateBonSortie;
	}

	public String getMoisBonSortie() {
		return moisBonSortie;
	}

	public void setMoisBonSortie(String moisBonSortie) {
		this.moisBonSortie = moisBonSortie;
	}

	public Long getPiece() {
		return piece;
	}

	public void setPiece(Long piece) {
		this.piece = piece;
	}

	
	
	public BigDecimal getPartGa() {
		return partGa;
	}

	public void setPartGa(BigDecimal partGa) {
		this.partGa = partGa;
	}

	public LocalDate getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(LocalDate creationDate) {
		this.creationDate = creationDate;
	}

	public Long getServiceId() {
		return serviceId;
	}

	public void setServiceId(Long serviceId) {
		this.serviceId = serviceId;
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public Long getChargeId() {
		return chargeId;
	}

	public void setChargeId(Long chargeId) {
		this.chargeId = chargeId;
	}

	public String getCharge() {
		return charge;
	}

	public void setCharge(String charge) {
		this.charge = charge;
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

	public String getMarque() {
		return marque;
	}

	public void setMarque(String marque) {
		this.marque = marque;
	}

	@Override
	public String toString() {
		return "ViewBonification [id=" + id + ", compagnieId=" + compagnieId + ", compagnie=" + compagnie
				+ ", numContrat=" + numContrat + ", zoneId=" + zoneId + ", zone=" + zone + ", packId=" + packId
				+ ", pack=" + pack + ", referenceGa=" + referenceGa + ", reparateurId=" + reparateurId
				+ ", reparateurName=" + reparateurName + ", idZoneReparateur=" + idZoneReparateur + ", zoneReparateur="
				+ zoneReparateur + ", dateBonSortie=" + dateBonSortie + ", moisBonSortie=" + moisBonSortie + ", mdIp="
				+ mdIp + ", piece=" + piece + ", partGa=" + partGa + ", partCie=" + partCie + ", creationDate="
				+ creationDate + ", serviceId=" + serviceId + ", service=" + service + ", chargeId=" + chargeId
				+ ", charge=" + charge + ", expertId=" + expertId + ", expert=" + expert + ", baseCalculModIp="
				+ baseCalculModIp + ", baseCalculPiece=" + baseCalculPiece + ", dateOuverture=" + dateOuverture + "]";
	}

	public LocalDate getDateOuverture() {
		return dateOuverture;
	}

	public void setDateOuverture(LocalDate dateOuverture) {
		this.dateOuverture = dateOuverture;
	}

	public BigDecimal getMdIp() {
		return mdIp;
	}

	public void setMdIp(BigDecimal mdIp) {
		this.mdIp = mdIp;
	}

	public BigDecimal getPartCie() {
		return partCie;
	}

	public void setPartCie(BigDecimal partCie) {
		this.partCie = partCie;
	}

	public BigDecimal getBaseCalculModIp() {
		return baseCalculModIp;
	}

	public void setBaseCalculModIp(BigDecimal baseCalculModIp) {
		this.baseCalculModIp = baseCalculModIp;
	}

	public BigDecimal getBaseCalculPiece() {
		return baseCalculPiece;
	}

	public void setBaseCalculPiece(BigDecimal baseCalculPiece) {
		this.baseCalculPiece = baseCalculPiece;
	}

	
    
    
    
    
    
    
    
    
    
   
    
    
    
    
    

}
