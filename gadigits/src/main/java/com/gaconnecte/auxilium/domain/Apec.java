package com.gaconnecte.auxilium.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Date;

/**
 * A Apec.
 */
@Entity
@Table(name = "apec")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "apec")
public class Apec implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;
    
    @Column(name = "observation_assure")
    private String observationAssure;

    @Column(name = "description_observation_assure")
    private String decriptionObservationAssure;

    //@NotNull
    @Column(name = "date_generation")
    private LocalDate dateGeneration;

    @Column(name = "is_complementaire")
    private Boolean isComplementaire;

    @NotNull
    @Column(name = "participation_ga", nullable = false)
    private Double participationGa;

    @NotNull
    @Column(name = "participation_assure", nullable = false)
    private Double participationAssure;

    @Column(name = "participation_vetuste")
    private Double participationVetuste;

    @Column(name = "participation_rpc")
    private Double participationRpc;

    @Column(name = "participation_tva")
    private Double participationTva;

    @Column(name = "depacement_plafond")
    private Double depacementPlafond;

    @Column(name = "estimation_franchise")
    private Double estimationFranchise;

    @Size(max = 2000)
    @Column(name = "commentaire", length = 2000)
    private String commentaire;

    @ManyToOne
    @JoinColumn(name = "sinister_pec_id")
    private SinisterPec sinisterPec;

    @Column(name = "frais_dossier")
    private Double fraisDossier;

    @Column(name = "regle_proportionnel")
    private Double regleProportionnel;

    @Column(name = "droit_timbre")
    private Double droitDeTimbre;

    @Column(name = "avance_facture")
    private Double avanceFacture;

    @Column(name = "etat")
    private Integer etat;

    @Column(name = "decision")
    private Integer decision;

    @Column(name = "observation")
    private String observation;

    @Column(name = "solde_reparateur")
    private Double soldeReparateur;

    @Column(name = "surplus_encaisse")
    private Double surplusEncaisse;

    @Column(name = "mo")
    private Double MO;

    @Column(name = "pr")
    private Double PR;

    @Column(name = "ip_pf")
    private Double IPPF;

    @Column(name = "decription_observation")
    private String decriptionObservation;

    @Column(name = "decision_assure")
    private Integer decisionAssure;

    @Column(name = "quotation_id")
    private Long quotationId;

    @ManyToOne
	@JoinColumn(name = "accord_raison__id")
	private RaisonPec accordRaison;

    @Column(name = "accord_number")
    private Integer accordNumber;

    @Column(name = "apec_edit")
    private Boolean apecEdit;

    @Column(name = "to_approuv_date")
    private Date toApprouvDate;

    @Column(name = "approuv_date")
    private LocalDate approuvDate;

    @Column(name = "modif_date")
    private LocalDate modifDate;

    @Column(name = "validation_date")
    private LocalDate validationDate;

    @Column(name = "assure_validation_date")
    private LocalDate assureValidationDate;

    @Column(name = "signature_date")
    private LocalDateTime signatureDate;

    @Column(name = "imprim_date")
    private Date imprimDate;

    @Column(name = "ttc")
    private Double ttc;
    
    @Column(name = "is_confirme")
    private Boolean isConfirme;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDateGeneration() {
        return dateGeneration;
    }

    public Apec dateGeneration(LocalDate dateGeneration) {
        this.dateGeneration = dateGeneration;
        return this;
    }

    public void setDateGeneration(LocalDate dateGeneration) {
        this.dateGeneration = dateGeneration;
    }

    public Boolean isIsComplementaire() {
        return isComplementaire;
    }

    public Apec isComplementaire(Boolean isComplementaire) {
        this.isComplementaire = isComplementaire;
        return this;
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

    public Apec participationGa(Double participationGa) {
        this.participationGa = participationGa;
        return this;
    }

    public void setParticipationGa(Double participationGa) {
        this.participationGa = participationGa;
    }

    public Double getParticipationAssure() {
        return participationAssure;
    }

    public Apec participationAssure(Double participationAssure) {
        this.participationAssure = participationAssure;
        return this;
    }

    public void setParticipationAssure(Double participationAssure) {
        this.participationAssure = participationAssure;
    }

    public Double getParticipationVetuste() {
        return participationVetuste;
    }

    public Apec participationVetuste(Double participationVetuste) {
        this.participationVetuste = participationVetuste;
        return this;
    }

    public void setParticipationVetuste(Double participationVetuste) {
        this.participationVetuste = participationVetuste;
    }

    public Double getParticipationRpc() {
        return participationRpc;
    }

    public Apec participationRpc(Double participationRpc) {
        this.participationRpc = participationRpc;
        return this;
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

    public Apec depacementPlafond(Double depacementPlafond) {
        this.depacementPlafond = depacementPlafond;
        return this;
    }

    public void setDepacementPlafond(Double depacementPlafond) {
        this.depacementPlafond = depacementPlafond;
    }

    public Double getEstimationFranchise() {
        return estimationFranchise;
    }

    public Apec estimationFranchise(Double estimationFranchise) {
        this.estimationFranchise = estimationFranchise;
        return this;
    }

    public void setEstimationFranchise(Double estimationFranchise) {
        this.estimationFranchise = estimationFranchise;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public Apec commentaire(String commentaire) {
        this.commentaire = commentaire;
        return this;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public SinisterPec getSinisterPec() {
        return sinisterPec;
    }

    public Apec sinisterPec(SinisterPec sinisterPec) {
        this.sinisterPec = sinisterPec;
        return this;
    }

    public void setSinisterPec(SinisterPec sinisterPec) {
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

    public Integer getAccordNumber() {
        return accordNumber;
    }

    public void setAccordNumber(Integer accordNumber) {
        this.accordNumber = accordNumber;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
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

	public Long getQuotationId() {
        return quotationId;
    }

    public void setQuotationId(Long quotationId) {
        this.quotationId = quotationId;
    }

    public RaisonPec getAccordRaison() {
		return accordRaison;
	}

	public void setAccordRaison(RaisonPec accordRaison) {
		this.accordRaison = accordRaison;
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

    public Boolean getIsConfirme() {
		return isConfirme;
	}

	public void setIsConfirme(Boolean isConfirme) {
		this.isConfirme = isConfirme;
	}

	public Double getTtc() {
		return ttc;
	}

	public void setTtc(Double ttc) {
		this.ttc = ttc;
	}

	public boolean equals(Object o) {

        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Apec apec = (Apec) o;
        if (apec.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), apec.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

	@Override
	public String toString() {
		return "Apec [id=" + id + ", dateGeneration=" + dateGeneration + ", isComplementaire=" + isComplementaire
				+ ", participationGa=" + participationGa + ", participationAssure=" + participationAssure
				+ ", participationVetuste=" + participationVetuste + ", participationRpc=" + participationRpc
				+ ", participationTva=" + participationTva + ", depacementPlafond=" + depacementPlafond
				+ ", estimationFranchise=" + estimationFranchise + ", commentaire=" + commentaire + ", fraisDossier="
				+ fraisDossier + ", regleProportionnel=" + regleProportionnel + ", droitDeTimbre=" + droitDeTimbre
				+ ", avanceFacture=" + avanceFacture + ", etat=" + etat + ", decision=" + decision + ", observation="
				+ observation + ", soldeReparateur=" + soldeReparateur + ", surplusEncaisse=" + surplusEncaisse
				+ ", MO=" + MO + ", PR=" + PR + ", IPPF=" + IPPF + ", decriptionObservation=" + decriptionObservation
				+ ", decisionAssure=" + decisionAssure + ", observationAssure=" + observationAssure
				+ ", decriptionObservationAssure=" + decriptionObservationAssure + ", quotationId=" + quotationId
				+ ", accordRaison=" + accordRaison + ", accordNumber=" + accordNumber + ", apecEdit=" + apecEdit
				+ ", toApprouvDate=" + toApprouvDate + ", approuvDate=" + approuvDate + ", modifDate=" + modifDate
				+ ", validationDate=" + validationDate + ", assureValidationDate=" + assureValidationDate
				+ ", imprimDate=" + imprimDate + ", ttc=" + ttc + ", isConfirme=" + isConfirme + "]";
	}

	

    
}
