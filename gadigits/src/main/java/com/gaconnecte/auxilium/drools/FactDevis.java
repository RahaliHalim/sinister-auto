package com.gaconnecte.auxilium.drools;


import java.io.Serializable;
import java.util.Objects;

import com.gaconnecte.auxilium.domain.enumeration.EtatDevis;
import com.gaconnecte.auxilium.service.prm.dto.PartnerRulesDTO;
import java.time.ZonedDateTime;

/**
 * A DTO for the Devis entity.
 */
public class FactDevis implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

    private Long modeId;
    
    private Long garantieId;
    
    private Double totalTtc;

    private Double totHt;
    
    private Double tva;

    private Boolean isComplementaire;

    private Boolean isSupprime;

    private ZonedDateTime dateGeneration;

    private EtatDevis etatDevis;

    private Integer version;

    private Float timbre;
    
    private Float vetuste;
    
    private Boolean isAssujettie;
    
    private Boolean isRachat;
    
    private Float partResponsabilite;
    
    private String commentaire;

    private Float droitTimbre;

    private Long prestationId;

    private Long reparateurId;

    private Long expertId;
    
    private Float totalPartAssuree;
    
    private Float responsabilite; 
    
    private Float ttcDetailsMo;
    
    private Float ttcPieceR;
    
    private Float ttcPieceIP;
    
    private Float thtDetailsMo;
    
    private Float thtPieceR;
    
    private Float thtPieceIP;
    
	private Float franchise;
	
	private Float franchiseInput;
    
    private String typeFranchise;

    private Float avance;
    
	private Float depassplafond;
	
	private Float depassplafondInput;

	private Float depassplafondInputNonAssujettie;
    
    private Float reglepropor;
    
    private Float fraisDossier;

	private Float fraisDossierInput;
    
    private Float surplusEncaisse;
    
    private Float soldeReparateur;
    
    private Float engagementGa;
    
    private Long compagnieId;
    
    private String nomCompagnie;
    
    private Double capitalAssuree;
    
    private Double valeurNeuf;
    
    private Double valeurVulnerable;
    
    private Double valeurReel;
    
    private String typeRachat;
    
    private String typeFraisHida;
    
	private Double fixValue;
	
	private Double minValue;
	
	private Double paramValue;
	
	private Double paramValueHida;
	
	private Double fixValueHida;
	
	private Double supttc;
	
	private PartnerRulesDTO partnerRulesDTO;

	private Double capitalRestant;

	private Double hidaServiceChargesLower;
    private Double hidaServiceChargesHigher;
    private Double hidaServiceChargesBetween;

	private Float rpDV;
	private Float rpVI;

	private Float regleproporAssujettie;

	private Float regleproporNonAssujettie;

	private Double valeurNeufDOMV;

	private Double marketValueVOLIN;

	private Float regleproporAssujettieCarte;

	private Float regleproporNonAssujettieCarte;

	private Float valeurNeufCarte;
    
    public Long getGarantieId() {
		return garantieId;
	}

	public void setGarantieId(Long garantieId) {
		this.garantieId = garantieId;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

	public Long getModeId() {
		return modeId;
	}

	public void setModeId(Long modeId) {
		this.modeId = modeId;
	}

	public Double getTotalTtc() {
        return totalTtc;
    }

    public void setTotalTtc(Double totalTtc) {
        this.totalTtc = totalTtc;
    }

    public Double getTotHt() {
        return totHt;
    }

    public void setTotHt(Double totHt) {
        this.totHt = totHt;
    }

    public Boolean isIsComplementaire() {
        return isComplementaire;
    }

    public void setIsComplementaire(Boolean isComplementaire) {
        this.isComplementaire = isComplementaire;
    }

    public Boolean isIsSupprime() {
        return isSupprime;
    }

    public void setIsSupprime(Boolean isSupprime) {
        this.isSupprime = isSupprime;
    }

    public ZonedDateTime getDateGeneration() {
        return dateGeneration;
    }

    public void setDateGeneration(ZonedDateTime dateGeneration) {
        this.dateGeneration = dateGeneration;
    }

    public EtatDevis getEtatDevis() {
        return etatDevis;
    }

    public void setEtatDevis(EtatDevis etatDevis) {
        this.etatDevis = etatDevis;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Float getTimbre() {
        return timbre;
    }

    public void setTimbre(Float timbre) {
        this.timbre = timbre;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public Float getDroitTimbre() {
        return droitTimbre;
    }

    public void setDroitTimbre(Float droitTimbre) {
        this.droitTimbre = droitTimbre;
    }

    public Long getPrestationId() {
        return prestationId;
    }

    public void setPrestationId(Long prestationPECId) {
        this.prestationId = prestationPECId;
    }

    public Long getReparateurId() {
        return reparateurId;
    }

    public void setReparateurId(Long reparateurId) {
        this.reparateurId = reparateurId;
    }

    public Long getExpertId() {
        return expertId;
    }

    public void setExpertId(Long expertId) {
        this.expertId = expertId;
    }
    
    public Float getVetuste() {
		return vetuste;
	}

	public void setVetuste(Float vetuste) {
		this.vetuste = vetuste;
	}

	public Float getPartResponsabilite() {
		return partResponsabilite;
	}

	public void setPartResponsabilite(Float partResponsabilite) {
		this.partResponsabilite = partResponsabilite;
	}

	public Boolean getIsAssujettie() {
		return isAssujettie;
	}

	public void setIsAssujettie(Boolean isAssujettie) {
		this.isAssujettie = isAssujettie;
	}
    
	public Double getTva() {
		return tva;
	}

	public void setTva(Double tva) {
		this.tva = tva;
	}

	public Float getTotalPartAssuree() {
		return totalPartAssuree;
	}

	public void setTotalPartAssuree(Float totalPartAssuree) {
		this.totalPartAssuree = totalPartAssuree;
	}
    
	public Float getResponsabilite() {
		return responsabilite;
	}

	public void setResponsabilite(Float responsabilite) {
		this.responsabilite = responsabilite;
	}
    
	public Float getTtcDetailsMo() {
		return ttcDetailsMo;
	}

	public void setTtcDetailsMo(Float ttcDetailsMo) {
		this.ttcDetailsMo = ttcDetailsMo;
	}
    
	public Float getTtcPieceR() {
		return ttcPieceR;
	}

	public void setTtcPieceR(Float ttcPieceR) {
		this.ttcPieceR = ttcPieceR;
	}
    
	public Float getTtcPieceIP() {
		return ttcPieceIP;
	}

	public void setTtcPieceIP(Float ttcPieceIP) {
		this.ttcPieceIP = ttcPieceIP;
	}
    
	public Float getAvance() {
		return avance;
	}

	public void setAvance(Float avance) {
		this.avance = avance;
	}

	public Float getFranchise() {
		return franchise;
	}

	public void setFranchise(Float franchise) {
		this.franchise = franchise;
	}

	public Float getFranchiseInput() {
		return franchiseInput;
	}

	public void setFranchiseInput(Float franchiseInput) {
		this.franchiseInput = franchiseInput;
	}
    
	public Float getDepassplafond() {
		return depassplafond;
	}

	public void setDepassplafond(Float depassplafond) {
		this.depassplafond = depassplafond;
	}

	public Float getDepassplafondInput() {
		return depassplafondInput;
	}

	public void setDepassplafondInput(Float depassplafondInput) {
		this.depassplafondInput = depassplafondInput;
	}

	public Float getDepassplafondInputNonAssujettie() {
		return depassplafondInputNonAssujettie;
	}

	public void setDepassplafondInputNonAssujettie(Float depassplafondInputNonAssujettie) {
		this.depassplafondInputNonAssujettie = depassplafondInputNonAssujettie;
	}
    
	public Float getReglepropor() {
		return reglepropor;
	}

	public void setReglepropor(Float reglepropor) {
		this.reglepropor = reglepropor;
	}

	public Float getRegleproporNonAssujettie() {
		return regleproporNonAssujettie;
	}

	public void setRegleproporNonAssujettie(Float regleproporNonAssujettie) {
		this.regleproporNonAssujettie = regleproporNonAssujettie;
	}

	public Float getRegleproporAssujettie() {
		return regleproporAssujettie;
	}

	public void setRegleproporAssujettie(Float regleproporAssujettie) {
		this.regleproporAssujettie = regleproporAssujettie;
	}

	public Float getRegleproporAssujettieCarte() {
		return regleproporAssujettieCarte;
	}
	public void setRegleproporAssujettieCarte(Float regleproporAssujettieCarte) {
		this.regleproporAssujettieCarte = regleproporAssujettieCarte;
	}
	public Float getRegleproporNonAssujettieCarte() {
		return regleproporNonAssujettieCarte;
	}

	public void setRegleproporNonAssujettieCarte(Float regleproporNonAssujettieCarte) {
		this.regleproporNonAssujettieCarte = regleproporNonAssujettieCarte;
	}
    
	public Float getFraisDossier() {
		return fraisDossier;
	}

	public void setFraisDossier(Float fraisDossier) {
		this.fraisDossier = fraisDossier;
	}

	public Float getFraisDossierInput() {
		return fraisDossierInput;
	}

	public void setFraisDossierInput(Float fraisDossierInput) {
		this.fraisDossierInput = fraisDossierInput;
	}
    
	public Float getSurplusEncaisse() {
		return surplusEncaisse;
	}

	public void setSurplusEncaisse(Float surplusEncaisse) {
		this.surplusEncaisse = surplusEncaisse;
	}
     
	public Float getSoldeReparateur() {
		return soldeReparateur;
	}

	public void setSoldeReparateur(Float soldeReparateur) {
		this.soldeReparateur = soldeReparateur;
	}
    
	public String getNomCompagnie() {
		return nomCompagnie;
	}

	public void setNomCompagnie(String nomCompagnie) {
		this.nomCompagnie = nomCompagnie;
	}
    
	public Double getCapitalAssuree() {
		return capitalAssuree;
	}

	public void setCapitalAssuree(Double capitalAssuree) {
		this.capitalAssuree = capitalAssuree;
	}

	public Double getCapitalRestant() {
		return capitalRestant;
	}

	public void setCapitalRestant(Double capitalRestant) {
		this.capitalRestant = capitalRestant;
	}

	public Double getHidaServiceChargesLower() {
		return hidaServiceChargesLower;
	}

	public void setHidaServiceChargesLower(Double hidaServiceChargesLower) {
		this.hidaServiceChargesLower = hidaServiceChargesLower;
	}

	public Double getHidaServiceChargesHigher() {
		return hidaServiceChargesHigher;
	}

	public void setHidaServiceChargesHigher(Double hidaServiceChargesHigher) {
		this.hidaServiceChargesHigher = hidaServiceChargesHigher;
	}

	public Double getHidaServiceChargesBetween() {
		return hidaServiceChargesBetween;
	}

	public void setHidaServiceChargesBetween(Double hidaServiceChargesBetween) {
		this.hidaServiceChargesBetween = hidaServiceChargesBetween;
	}
    
	public Double getValeurNeuf() {
		return valeurNeuf;
	}

	public void setValeurNeuf(Double valeurNeuf) {
		this.valeurNeuf = valeurNeuf;
	}

	public Double getValeurNeufDOMV() {
		return valeurNeufDOMV;
	}

	public void setValeurNeufDOMV(Double valeurNeufDOMV) {
		this.valeurNeufDOMV = valeurNeufDOMV;
	}

	public Double getMarketValueVOLIN() {
		return marketValueVOLIN;
	}

	public void setMarketValueVOLIN(Double marketValueVOLIN) {
		this.marketValueVOLIN = marketValueVOLIN;
	}

	public Double getValeurVulnerable() {
		return valeurVulnerable;
	}

	public void setValeurVulnerable(Double valeurVulnerable) {
		this.valeurVulnerable = valeurVulnerable;
	}
    
	public Float getThtDetailsMo() {
		return thtDetailsMo;
	}

	public void setThtDetailsMo(Float thtDetailsMo) {
		this.thtDetailsMo = thtDetailsMo;
	}

	public Float getThtPieceR() {
		return thtPieceR;
	}

	public void setThtPieceR(Float thtPieceR) {
		this.thtPieceR = thtPieceR;
	}

	public Float getThtPieceIP() {
		return thtPieceIP;
	}

	public void setThtPieceIP(Float thtPieceIP) {
		this.thtPieceIP = thtPieceIP;
	}
    
	public Boolean getIsRachat() {
		return isRachat;
	}

	public void setIsRachat(Boolean isRachat) {
		this.isRachat = isRachat;
	}

	public Float getEngagementGa() {
		return engagementGa;
	}

	public void setEngagementGa(Float engagementGa) {
		this.engagementGa = engagementGa;
	}

	public Float getRpDV() {
		return rpDV;
	}

	public void setRpDV(Float rpDV) {
		this.rpDV = rpDV;
	}

	public Float getRpVI() {
		return rpVI;
	}

	public void setRpVI(Float rpVI) {
		this.rpVI = rpVI;
	}

	public String getTypeFranchise() {
		return typeFranchise;
	}
    
	public void setTypeFranchise(String typeFranchise) {
		this.typeFranchise = typeFranchise;
	}
    
	public Long getCompagnieId() {
		return compagnieId;
	}

	public void setCompagnieId(Long compagnieId) {
		this.compagnieId = compagnieId;
	}
    
	public Double getFixValue() {
		return fixValue;
	}

	public void setFixValue(Double fixValue) {
		this.fixValue = fixValue;
	}
    
	public Double getParamValue() {
		return paramValue;
	}

	public void setParamValue(Double paramValue) {
		this.paramValue = paramValue;
	}
    
	public Double getMinValue() {
		return minValue;
	}

	public void setMinValue(Double minValue) {
		this.minValue = minValue;
	}
    
	public String getTypeRachat() {
		return typeRachat;
	}

	public void setTypeRachat(String typeRachat) {
		this.typeRachat = typeRachat;
	}
    
	public String getTypeFraisHida() {
		return typeFraisHida;
	}

	public void setTypeFraisHida(String typeFraisHida) {
		this.typeFraisHida = typeFraisHida;
	}
    
	public Double getParamValueHida() {
		return paramValueHida;
	}

	public void setParamValueHida(Double paramValueHida) {
		this.paramValueHida = paramValueHida;
	}

	public Double getFixValueHida() {
		return fixValueHida;
	}

	public void setFixValueHida(Double fixValueHida) {
		this.fixValueHida = fixValueHida;
	}
    
	public Double getSupttc() {
		return supttc;
	}

	public void setSupttc(Double supttc) {
		this.supttc = supttc;
	}

	public PartnerRulesDTO getPartnerRulesDTO() {
		return partnerRulesDTO;
	}

	public void setPartnerRulesDTO(PartnerRulesDTO partnerRulesDTO) {
		this.partnerRulesDTO = partnerRulesDTO;
	}
    
	public Double getValeurReel() {
		return valeurReel;
	}

	public void setValeurReel(Double valeurReel) {
		this.valeurReel = valeurReel;
	}

	public Float getValeurNeufCarte() {
		return valeurNeufCarte;
	}

	public void setValeurNeufCarte(Float valeurNeufCarte) {
		this.valeurNeufCarte = valeurNeufCarte;
	}



	@Override  
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        FactDevis devisDTO = (FactDevis) o;
        if(devisDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), devisDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

	@Override
	public String toString() {
		return "FactDevis [id=" + id + ", modeId=" + modeId + ", garantieId=" + garantieId + ", totalTtc=" + totalTtc
				+ ", totHt=" + totHt + ", tva=" + tva + ", isComplementaire=" + isComplementaire + ", isSupprime="
				+ isSupprime + ", dateGeneration=" + dateGeneration + ", etatDevis=" + etatDevis + ", version="
				+ version + ", timbre=" + timbre + ", vetuste=" + vetuste + ", isAssujettie=" + isAssujettie
				+ ", isRachat=" + isRachat + ", partResponsabilite=" + partResponsabilite + ", commentaire="
				+ commentaire + ", droitTimbre=" + droitTimbre + ", prestationId=" + prestationId + ", reparateurId="
				+ reparateurId + ", expertId=" + expertId + ", totalPartAssuree=" + totalPartAssuree
				+ ", responsabilite=" + responsabilite + ", ttcDetailsMo=" + ttcDetailsMo + ", ttcPieceR=" + ttcPieceR
				+ ", ttcPieceIP=" + ttcPieceIP + ", thtDetailsMo=" + thtDetailsMo + ", thtPieceR=" + thtPieceR
				+ ", thtPieceIP=" + thtPieceIP + ", franchise=" + franchise + ", franchiseInput=" + franchiseInput
				+ ", typeFranchise=" + typeFranchise + ", avance=" + avance + ", depassplafond=" + depassplafond
				+ ", depassplafondInput=" + depassplafondInput + ", depassplafondInputNonAssujettie="
				+ depassplafondInputNonAssujettie + ", reglepropor=" + reglepropor + ", fraisDossier=" + fraisDossier
				+ ", fraisDossierInput=" + fraisDossierInput + ", surplusEncaisse=" + surplusEncaisse
				+ ", soldeReparateur=" + soldeReparateur + ", engagementGa=" + engagementGa + ", compagnieId="
				+ compagnieId + ", nomCompagnie=" + nomCompagnie + ", capitalAssuree=" + capitalAssuree
				+ ", valeurNeuf=" + valeurNeuf + ", valeurVulnerable=" + valeurVulnerable + ", valeurReel=" + valeurReel
				+ ", typeRachat=" + typeRachat + ", typeFraisHida=" + typeFraisHida + ", fixValue=" + fixValue
				+ ", minValue=" + minValue + ", paramValue=" + paramValue + ", paramValueHida=" + paramValueHida
				+ ", fixValueHida=" + fixValueHida + ", supttc=" + supttc + ", partnerRulesDTO=" + partnerRulesDTO
				+ ", capitalRestant=" + capitalRestant + ", hidaServiceChargesLower=" + hidaServiceChargesLower
				+ ", hidaServiceChargesHigher=" + hidaServiceChargesHigher + ", hidaServiceChargesBetween="
				+ hidaServiceChargesBetween + ", rpDV=" + rpDV + ", rpVI=" + rpVI + ", regleproporAssujettie="
				+ regleproporAssujettie + ", regleproporNonAssujettie=" + regleproporNonAssujettie + ", valeurNeufDOMV="
				+ valeurNeufDOMV + ", marketValueVOLIN=" + marketValueVOLIN + ", regleproporAssujettieCarte="
				+ regleproporAssujettieCarte + ", regleproporNonAssujettieCarte=" + regleproporNonAssujettieCarte
				+ ", valeurNeufCarte=" + valeurNeufCarte + "]";
	}

    
}
