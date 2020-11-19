package com.gaconnecte.auxilium.service.dto;


import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.validation.constraints.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import java.util.Date;

/**
 * A DTO for the Apec entity.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ApecDTO")
@XmlRootElement(name = "ApecDTO")
public class ApecDTO implements Serializable {

    private Long id;
    
    private String observationAssure;

    private String decriptionObservationAssure;

    //@NotNull
    private LocalDate dateGeneration;

    private Boolean isComplementaire;

    @NotNull
    private Double participationGa;

    @NotNull
    private Double participationAssure;

    private Double participationVetuste;

    private Double participationRpc;

    private Double participationTva;

    private Double depacementPlafond;

    private Double estimationFranchise;

    @Size(max = 2000)
    private String commentaire;

    private Long affectReparateurId;

    private Long sinisterPecId;

    private SinisterPecDTO sinisterPec ;

    private Double fraisDossier;

    private Double regleProportionnel;

    private Double droitDeTimbre;

    private Double avanceFacture;
    
    private Integer etat;

    private Integer decision;

    private String observation;
    
    private Double soldeReparateur;
    
    private Double surplusEncaisse;
    
    private Double MO;
    
    private Double PR;
    
    private Double IPPF;

    private String decriptionObservation;

    private Integer decisionAssure;

    private Long quotationId;

    private Long accordRaisonId;

    private Integer accordNumber;

    private Boolean apecEdit;

    private Date toApprouvDate;

    private LocalDate approuvDate;

    private LocalDate modifDate;

    private LocalDate validationDate;

    private LocalDate assureValidationDate;

    private LocalDateTime signatureDate;

    private Date imprimDate;

    private Double ttc;
    
    private Boolean isConfirme;

    private Boolean testStep;
    private Boolean testModifPrix;
    private Boolean testDevis;
    private Boolean testImprim;
    private Boolean testAttAccord;

    
    
    public Boolean getTestAttAccord() {
		return testAttAccord;
	}

	public void setTestAttAccord(Boolean testAttAccord) {
		this.testAttAccord = testAttAccord;
	}

	public Boolean getTestImprim() {
		return testImprim;
	}

	public void setTestImprim(Boolean testImprim) {
		this.testImprim = testImprim;
	}

	public Boolean getTestDevis() {
		return testDevis;
	}

	public void setTestDevis(Boolean testDevis) {
		this.testDevis = testDevis;
	}

	public Long getId() {
        return id;
    }

    public Boolean getTestModifPrix() {
		return testModifPrix;
	}

	public void setTestModifPrix(Boolean testModifPrix) {
		this.testModifPrix = testModifPrix;
	}

	public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDateGeneration() {
        return dateGeneration;
    }

    public void setDateGeneration(LocalDate dateGeneration) {
        this.dateGeneration = dateGeneration;
    }

    public Boolean isIsComplementaire() {
        return isComplementaire;
    }

    public void setIsComplementaire(Boolean isComplementaire) {
        this.isComplementaire = isComplementaire;
    }

    public Boolean getIsComplementaire() {
        return isComplementaire;
    }

    public Double getParticipationGa() {
        return participationGa;
    }

    public void setParticipationGa(Double participationGa) {
        this.participationGa = participationGa;
    }

    public Double getParticipationAssure() {
        return participationAssure;
    }

    public void setParticipationAssure(Double participationAssure) {
        this.participationAssure = participationAssure;
    }

    public Double getParticipationVetuste() {
        return participationVetuste;
    }

    public void setParticipationVetuste(Double participationVetuste) {
        this.participationVetuste = participationVetuste;
    }

    public Double getParticipationRpc() {
        return participationRpc;
    }

    public void setParticipationRpc(Double participationRpc) {
        this.participationRpc = participationRpc;
    }

    public Double getParticipationTva() {
        return participationTva;
    }

    public void setParticipationTva(Double participationTva) {
        this.participationTva = participationTva;
    }

    public Double getDepacementPlafond() {
        return depacementPlafond;
    }

    public void setDepacementPlafond(Double depacementPlafond) {
        this.depacementPlafond = depacementPlafond;
    }

    public Double getEstimationFranchise() {
        return estimationFranchise;
    }

    public void setEstimationFranchise(Double estimationFranchise) {
        this.estimationFranchise = estimationFranchise;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public Long getAffectReparateurId() {
        return affectReparateurId;
    }

    public void setAffectReparateurId(Long affectReparateurId) {
        this.affectReparateurId = affectReparateurId;
    }

    public Long getSinisterPecId() {
        return sinisterPecId;
    }

    public void setSinisterPecId(Long sinisterPecId) {
        this.sinisterPecId = sinisterPecId;
    }

    public Long getQuotationId() {
        return quotationId;
    }

    public void setQuotationId(Long quotationId) {
        this.quotationId = quotationId;
    }

    public Long getAccordRaisonId() {
        return accordRaisonId;
    }

    public void setAccordRaisonId(Long accordRaisonId) {
        this.accordRaisonId = accordRaisonId;
    }

    public SinisterPecDTO getSinisterPec() {
        return sinisterPec;
    }

    public void setSinisterPec(SinisterPecDTO sinisterPec) {
        this.sinisterPec = sinisterPec;
    }

    public Double getFraisDossier() {
        return fraisDossier;
    }

    public void setFraisDossier(Double fraisDossier) {
        this.fraisDossier = fraisDossier;
    }

    public Double getRegleProportionnel() {
        return regleProportionnel;
    }

    public void setRegleProportionnel(Double regleProportionnel) {
        this.regleProportionnel = regleProportionnel;
    }

    public Double getDroitDeTimbre() {
        return droitDeTimbre;
    }

    public void setDroitDeTimbre(Double droitDeTimbre) {
        this.droitDeTimbre = droitDeTimbre;
    }

    public Double getAvanceFacture() {
        return avanceFacture;
    }

    public void setAvanceFacture(Double avanceFacture) {
        this.avanceFacture = avanceFacture;
    }

    public Integer getDecision() {
        return decision;
    }

    public void setDecision(Integer decision) {
        this.decision = decision;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public String getDecriptionObservation() {
        return decriptionObservation;
    }

    public void setDecriptionObservation(String decriptionObservation) {
        this.decriptionObservation = decriptionObservation;
    }
    public Integer getDecisionAssure() {
        return decisionAssure;
    }

    public void setDecisionAssure(Integer decisionAssure) {
        this.decisionAssure = decisionAssure;
    }

    public String getObservationAssure() {
		return observationAssure;
	}

	public void setObservationAssure(String observationAssure) {
		this.observationAssure = observationAssure;
	}

	public String getDecriptionObservationAssure() {
		return decriptionObservationAssure;
	}

	public void setDecriptionObservationAssure(String decriptionObservationAssure) {
		this.decriptionObservationAssure = decriptionObservationAssure;
	}

	public Integer getEtat() {
		return etat;
	}

	public void setEtat(Integer etat) {
		this.etat = etat;
	}


    public Double getSoldeReparateur() {
		return soldeReparateur;
	}

	public void setSoldeReparateur(Double soldeReparateur) {
		this.soldeReparateur = soldeReparateur;
	}

	public Double getSurplusEncaisse() {
		return surplusEncaisse;
	}

	public void setSurplusEncaisse(Double surplusEncaisse) {
		this.surplusEncaisse = surplusEncaisse;
	}

	public Double getMO() {
		return MO;
	}

	public void setMO(Double mO) {
		MO = mO;
	}

	public Double getPR() {
		return PR;
	}

	public void setPR(Double pR) {
		PR = pR;
	}

	public Double getIPPF() {
		return IPPF;
	}

	public void setIPPF(Double iPPF) {
		IPPF = iPPF;
	}

    public Integer getAccordNumber() {
        return accordNumber;
    }

    public void setAccordNumber(Integer accordNumber) {
        this.accordNumber = accordNumber;
    }

     public Boolean getApecEdit() {
		return apecEdit;
	}

	public void setApecEdit(Boolean apecEdit) {
		this.apecEdit = apecEdit;
	}

    public Date getToApprouvDate() { 
		return toApprouvDate;
	}

	public void setToApprouvDate(Date toApprouvDate) {
		this.toApprouvDate = toApprouvDate;
	}

    public LocalDate getApprouvDate() { 
		return approuvDate;
	}

	public void setApprouvDate(LocalDate approuvDate) {
		this.approuvDate = approuvDate;
	}

    public LocalDate getModifDate() { 
		return modifDate;
	}

	public void setModifDate(LocalDate modifDate) {
		this.modifDate = modifDate;
	}
    public LocalDate getValidationDate() { 
		return validationDate;
	}

	public void setValidationDate(LocalDate validationDate) {
		this.validationDate = validationDate;
	}

    public LocalDate getAssureValidationDate() { 
		return assureValidationDate;
	}

	public void setAssureValidationDate(LocalDate assureValidationDate) {
		this.assureValidationDate = assureValidationDate;
    }
    
    public LocalDateTime getSignatureDate() { 
		return signatureDate;
	}

	public void setSignatureDate(LocalDateTime signatureDate) {
		this.signatureDate = signatureDate;
	}

    public Date getImprimDate() { 
		return imprimDate;
	}

	public void setImprimDate(Date imprimDate) {
		this.imprimDate = imprimDate;
	}

    public Double getTtc() {
		return ttc;
	}

	public void setTtc(Double ttc) {
		this.ttc = ttc;
	}

	public Boolean getIsConfirme() {
		return isConfirme;
	}

	public void setIsConfirme(Boolean isConfirme) {
		this.isConfirme = isConfirme;
	}

	public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ApecDTO apecDTO = (ApecDTO) o;
        if(apecDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), apecDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    
    
	public Boolean getTestStep() {
		return testStep;
	}

	public void setTestStep(Boolean testStep) {
		this.testStep = testStep;
	}

	@Override
	public String toString() {
		return "ApecDTO [id=" + id + ", observationAssure=" + observationAssure + ", decriptionObservationAssure="
				+ decriptionObservationAssure + ", dateGeneration=" + dateGeneration + ", isComplementaire="
				+ isComplementaire + ", participationGa=" + participationGa + ", participationAssure="
				+ participationAssure + ", participationVetuste=" + participationVetuste + ", participationRpc="
				+ participationRpc + ", participationTva=" + participationTva + ", depacementPlafond="
				+ depacementPlafond + ", estimationFranchise=" + estimationFranchise + ", commentaire=" + commentaire
				+ ", affectReparateurId=" + affectReparateurId + ", sinisterPecId=" + sinisterPecId + ", sinisterPec="
				+ sinisterPec + ", fraisDossier=" + fraisDossier + ", regleProportionnel=" + regleProportionnel
				+ ", droitDeTimbre=" + droitDeTimbre + ", avanceFacture=" + avanceFacture + ", etat=" + etat
				+ ", decision=" + decision + ", observation=" + observation + ", soldeReparateur=" + soldeReparateur
				+ ", surplusEncaisse=" + surplusEncaisse + ", MO=" + MO + ", PR=" + PR + ", IPPF=" + IPPF
				+ ", decriptionObservation=" + decriptionObservation + ", decisionAssure=" + decisionAssure
				+ ", quotationId=" + quotationId + ", accordRaisonId=" + accordRaisonId + ", accordNumber="
				+ accordNumber + ", apecEdit=" + apecEdit + ", toApprouvDate=" + toApprouvDate + ", approuvDate="
				+ approuvDate + ", modifDate=" + modifDate + ", validationDate=" + validationDate
				+ ", assureValidationDate=" + assureValidationDate + ", imprimDate=" + imprimDate + ", ttc=" + ttc
				+ ", isConfirme=" + isConfirme + ", testStep=" + testStep + ", testModifPrix=" + testModifPrix
				+ ", testDevis=" + testDevis + "]";
	}

    
}
