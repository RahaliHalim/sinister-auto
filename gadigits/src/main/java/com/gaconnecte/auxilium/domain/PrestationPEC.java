package com.gaconnecte.auxilium.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gaconnecte.auxilium.domain.enumeration.EtatDossierRmq;
import com.gaconnecte.auxilium.domain.enumeration.PrestationStatus;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Where;

import com.gaconnecte.auxilium.domain.enumeration.Decision;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.transaction.Transaction;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import java.time.LocalDate;
import java.time.ZonedDateTime;

/**
 * A PrestationPEC.
 */
@Entity
@Table(name = "prestation_pec")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "prestation_pec")
public class PrestationPEC implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
	@SequenceGenerator(name = "sequenceGenerator")
	private Long id;

	@Size(max = 256)
	@Column(name = "reference_sinistre", length = 256)
	private String referenceSinistre;

	@Size(max = 256)
	@Column(name = "desc_pts_choc", length = 256)
	private String descPtsChoc;

	// @NotNull
	@Max(value = 99999999)
	@Column(name = "nbr_vehicules", nullable = false)
	private Integer nbrVehicules;

	@Column(name = "date_reception_vehicule")
	private LocalDate dateReceptionVehicule;

	@Enumerated(EnumType.STRING)
	@Column(name = "decision", nullable = false)
	private Decision decision;

	@DecimalMax(value = "100000000000000000")
	@Column(name = "valeur_neuf")
	private Double valeurNeuf;

	@DecimalMax(value = "100000000000000000")
	@Column(name = "franchise")
	private Double franchise;

	@DecimalMax(value = "100000000000000000")
	@Column(name = "capital_dc")
	private Double capitalDc;

	@DecimalMax(value = "100000000000000000")
	@Column(name = "valeur_venale")
	private Double valeurVenale;

	@Column(name = "type_franchise")
	private String typeFranchise;

	@ManyToOne
	private RefModeGestion mode;

	@ManyToOne
	private RefBareme bareme;

	@ManyToOne(optional = true)
	private Reparateur reparateur;

	@ManyToOne(optional = true)
	private Expert expert;

	@ManyToOne
	private RefPositionGa posGa;

	@ManyToOne(optional = false)
	private User user;

	//@OneToMany(mappedBy = "prestationPec")
	//@JsonIgnore
	//@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
	//private Set<Tiers> tiers = new HashSet<>();

	@Column(name = "reparator_facturation")
	private Boolean reparatorFacturation;

	@Column(name = "etat_choc")
	private Long etatChoc;
	@Column(name = "step")
	private Long step;

	@ManyToMany
	@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
	@JoinTable(name = "prestationpec_agent_generale", joinColumns = @JoinColumn(name = "prestationpecs_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "agent_generales_id", referencedColumnName = "id"))
	private Set<AgentGeneral> agentGenerales = new HashSet<>();

	@OneToOne
	@JoinColumn(unique = true)
	private PointChoc pointChoc;

	@Enumerated(EnumType.STRING)
	@Column(name = "status")
	private PrestationStatus status;

	@Column(name = "company_id")
	private Integer companyId;

	@Column(name = "agency_id")
	private Integer agencyId;

	@Transient
	private Long quotationStatusId;

	@Transient
	private Long devisId;

	@Column(name = "date_rdv_reparation")
	private LocalDate dateRDVReparation;

	@Column(name = "time_rdv_reparation")
	private String timeRDVReparation;

	@Column(name = "confirmation_rdv")
	private Boolean confirmationRDV;

	@Column(name = "is_delete")
	private Boolean isDelete;

	@Column(name = "date_derniere_maj", nullable = false)
	private LocalDate dateDerniereMaj;

	@OneToOne(optional = false)
	// @NotNull
	private Dossier dossier;

	// @NotNull
	@Column(name = "date_creation", nullable = false)
	private LocalDate dateCreation;

	@OneToOne
	@JoinColumn(name = "primary_quotation")
	@JsonIgnore
	private Quotation primaryQuotation;

	/*
	 * @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL,mappedBy =
	 * "prestation", orphanRemoval = true) private Set<ComplementaryQuotation>
	 * listComplementaryQuotation = new HashSet<>();
	 */

	private Long activeComplementaryId;

	/**
	 * @return the etatChoc
	 */
	public Long getEtatChoc() {
		return etatChoc;
	}

	/**
	 * @param etatChoc the etatChoc to set
	 */
	public void setEtatChoc(Long etatChoc) {
		this.etatChoc = etatChoc;
	}


	/**
	 * @return the step
	 */
	public Long getStep() {
		return step;
	}

	/**
	 * @param step the step to set
	 */
	public void setStep(Long step) {
		this.step = step;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getReferenceSinistre() {
		return referenceSinistre;
	}

	public PrestationPEC referenceSinistre(String referenceSinistre) {
		this.referenceSinistre = referenceSinistre;
		return this;
	}

	public void setReferenceSinistre(String referenceSinistre) {
		this.referenceSinistre = referenceSinistre;
	}

	public String getDescPtsChoc() {
		return descPtsChoc;
	}

	public PrestationPEC descPtsChoc(String descPtsChoc) {
		this.descPtsChoc = descPtsChoc;
		return this;
	}

	public void setDescPtsChoc(String descPtsChoc) {
		this.descPtsChoc = descPtsChoc;
	}

	public Integer getNbrVehicules() {
		return nbrVehicules;
	}

	public PrestationPEC nbrVehicules(Integer nbrVehicules) {
		this.nbrVehicules = nbrVehicules;
		return this;
	}

	public void setNbrVehicules(Integer nbrVehicules) {
		this.nbrVehicules = nbrVehicules;
	}

	public RefModeGestion getMode() {
		return mode;
	}

	public PrestationPEC mode(RefModeGestion refModeGestion) {
		this.mode = refModeGestion;
		return this;
	}

	public void setMode(RefModeGestion refModeGestion) {
		this.mode = refModeGestion;
	}

	public RefBareme getBareme() {
		return bareme;
	}

	public PrestationPEC bareme(RefBareme bareme) {
		this.bareme = bareme;
		return this;
	}

	public void setBareme(RefBareme bareme) {
		this.bareme = bareme;
	}

	public LocalDate getDateReceptionVehicule() {
		return dateReceptionVehicule;
	}

	public PrestationPEC dateReceptionVehicule(LocalDate dateReceptionVehicule) {
		this.dateReceptionVehicule = dateReceptionVehicule;
		return this;
	}

	public void setDateReceptionVehicule(LocalDate dateReceptionVehicule) {
		this.dateReceptionVehicule = dateReceptionVehicule;
	}

	public Reparateur getReparateur() {
		return reparateur;
	}

	public void setReparateur(Reparateur reparateur) {
		this.reparateur = reparateur;
	}

	public PrestationPEC reparateur(Reparateur reparateur) {
		this.reparateur = reparateur;
		return this;
	}

	public Expert getExpert() {
		return expert;
	}

	public void setExpert(Expert expert) {
		this.expert = expert;
	}

	public PrestationPEC expert(Expert expert) {
		this.expert = expert;
		return this;
	}

	public RefPositionGa getPosGa() {
		return posGa;
	}

	public PrestationPEC posGa(RefPositionGa refPositionGa) {
		this.posGa = refPositionGa;
		return this;
	}

	public void setPosGa(RefPositionGa refPositionGa) {
		this.posGa = refPositionGa;
	}

	public User getUser() {
		return user;
	}

	public PrestationPEC User(User user) {
		this.user = user;
		return this;
	}

	public Boolean getReparatorFacturation() {
		return reparatorFacturation;
	}

	public void setReparatorFacturation(Boolean reparatorFacturation) {
		this.reparatorFacturation = reparatorFacturation;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Set<AgentGeneral> getAgentGenerales() {
		return agentGenerales;
	}

	public PrestationPEC agentGenerales(Set<AgentGeneral> agentGenerals) {
		this.agentGenerales = agentGenerals;
		return this;
	}

	public PrestationPEC addAgentGenerale(AgentGeneral agentGeneral) {
		this.agentGenerales.add(agentGeneral);
		agentGeneral.getDossiers().add(this);
		return this;
	}

	public PrestationPEC removeAgentGenerale(AgentGeneral agentGeneral) {
		this.agentGenerales.remove(agentGeneral);
		agentGeneral.getDossiers().remove(this);
		return this;
	}

	public void setAgentGenerales(Set<AgentGeneral> agentGenerals) {
		this.agentGenerales = agentGenerals;
	}

	public PointChoc getPointChoc() {
		return pointChoc;
	}

	public PrestationPEC pointChoc(PointChoc pointChoc) {
		this.pointChoc = pointChoc;
		return this;
	}

	public void setPointChoc(PointChoc pointChoc) {
		this.pointChoc = pointChoc;
	}

	public Decision getDecision() {
		return decision;
	}

	public PrestationPEC decision(Decision decision) {
		this.decision = decision;
		return this;
	}

	public void setDecision(Decision decision) {
		this.decision = decision;
	}

	public Double getValeurNeuf() {
		return valeurNeuf;
	}

	public PrestationPEC valeurNeuf(Double valeurNeuf) {
		this.valeurNeuf = valeurNeuf;
		return this;
	}

	public void setValeurNeuf(Double valeurNeuf) {
		this.valeurNeuf = valeurNeuf;
	}

	public Double getFranchise() {
		return franchise;
	}

	public PrestationPEC franchise(Double franchise) {
		this.franchise = franchise;
		return this;
	}

	public void setFranchise(Double franchise) {
		this.franchise = franchise;
	}

	public Double getCapitalDc() {
		return capitalDc;
	}

	public PrestationPEC capitalDc(Double capitalDc) {
		this.capitalDc = capitalDc;
		return this;
	}

	public void setCapitalDc(Double capitalDc) {
		this.capitalDc = capitalDc;
	}

	public Double getValeurVenale() {
		return valeurVenale;
	}

	public PrestationPEC valeurVenale(Double valeurVenale) {
		this.valeurVenale = valeurVenale;
		return this;
	}

	public void setValeurVenale(Double valeurVenale) {
		this.valeurVenale = valeurVenale;
	}

	public String getTypeFranchise() {
		return typeFranchise;
	}

	public void setTypeFranchise(String typeFranchise) {
		this.typeFranchise = typeFranchise;
	}

	public Long getQuotationStatusId() {
		return quotationStatusId;
	}

	public void setQuotationStatusId(Long quotationStatusId) {
		this.quotationStatusId = quotationStatusId;
	}

	public Long getDevisId() {
		return devisId;
	}

	public void setDevisId(Long devisId) {
		this.devisId = devisId;
	}

	/*public Set<Tiers> getTiers() {
		return tiers;
	}

	public PrestationPEC tiers(Set<Tiers> tiers) {
		this.tiers = tiers;
		return this;
	}

	public PrestationPEC addTiers(Tiers tiers) {
		this.tiers.add(tiers);
		return this;
	}

	public PrestationPEC removeTiers(Tiers tiers) {
		this.tiers.remove(tiers);
		return this;
	}

	public void setTiers(Set<Tiers> tiers) {
		this.tiers = tiers;
	}
*/
	public LocalDate getDateRDVReparation() {
		return dateRDVReparation;
	}

	public void setDateRDVReparation(LocalDate dateRDVReparation) {
		this.dateRDVReparation = dateRDVReparation;
	}

	public String getTimeRDVReparation() {
		return timeRDVReparation;
	}

	public void setTimeRDVReparation(String timeRDVReparation) {
		this.timeRDVReparation = timeRDVReparation;
	}

	public PrestationStatus getStatus() {
		return status;
	}

	public void setStatus(PrestationStatus status) {
		this.status = status;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public Integer getAgencyId() {
		return agencyId;
	}

	public void setAgencyId(Integer agencyId) {
		this.agencyId = agencyId;
	}

	public Boolean getConfirmationRDV() {
		return confirmationRDV;
	}

	public void setConfirmationRDV(Boolean confirmationRDV) {
		this.confirmationRDV = confirmationRDV;
	}

	public Boolean isIsDelete() {
		return isDelete;
	}

	public PrestationPEC isDelete(Boolean isDelete) {
		this.isDelete = isDelete;
		return this;
	}

	public void setIsDelete(Boolean isDelete) {
		this.isDelete = isDelete;
	}

	public LocalDate getDateCreation() {
		return dateCreation;
	}

	public PrestationPEC dateCreation(LocalDate dateCreation) {
		this.dateCreation = dateCreation;
		return this;
	}

	public void setDateCreation(LocalDate dateCreation) {
		this.dateCreation = dateCreation;
	}

	public LocalDate getDateDerniereMaj() {
		return dateDerniereMaj;
	}

	public PrestationPEC dateDerniereMaj(LocalDate dateDerniereMaj) {
		this.dateDerniereMaj = dateDerniereMaj;
		return this;
	}

	public void setDateDerniereMaj(LocalDate dateDerniereMaj) {
		this.dateDerniereMaj = dateDerniereMaj;
	}

	public Dossier getDossier() {
		return dossier;
	}

	public PrestationPEC dossier(Dossier dossier) {
		this.dossier = dossier;
		return this;
	}

	public void setDossier(Dossier dossier) {
		this.dossier = dossier;
	}

	public Quotation getPrimaryQuotation() {
		return primaryQuotation;
	}

	public void setPrimaryQuotation(Quotation primaryQuotation) {
		this.primaryQuotation = primaryQuotation;
	}

	/*
	 * public Set<ComplementaryQuotation> getListComplementaryQuotation() { return
	 * listComplementaryQuotation; }
	 * 
	 * public void setListComplementaryQuotation(Set<ComplementaryQuotation>
	 * listComplementaryQuotation) { this.listComplementaryQuotation =
	 * listComplementaryQuotation; }
	 */

	public Long getActiveComplementaryId() {
		return activeComplementaryId;
	}

	public void setActiveComplementaryId(Long activeComplementaryId) {
		this.activeComplementaryId = activeComplementaryId;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		PrestationPEC prestationPEC = (PrestationPEC) o;
		if (prestationPEC.getId() == null || getId() == null) {
			return false;
		}
		return Objects.equals(getId(), prestationPEC.getId());
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(getId());
	}

	@Override
	public String toString() {
		return "PrestationPEC{" + "id=" + getId() + ", descPtsChoc='" + getDescPtsChoc() + "'" + ", nbrVehicules='"
				+ getNbrVehicules() + "'" + ", expertID='" + getExpert() + "'" + "}";
	}
}
