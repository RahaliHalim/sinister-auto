package com.gaconnecte.auxilium.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Objects;

/**
 * A Reparateur.
 */
@Entity
@Table(name = "reparateur")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "reparateur")
public class Reparateur implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
	@SequenceGenerator(name = "sequenceGenerator")
	private Long id;
	@Column(name = "raison_sociale")
	private String raisonSociale;
	@Column(name = "registreCommerce")
	private String registreCommerce;
	@Column(name = "matricule_fiscale")
	private String matriculeFiscale;
	@Column(name = "nom_per_vis_vis")
	private String nomPerVisVis;
	@Column(name = "prenom_per_vis_vis")
	private String prenomPerVisVis;
	@Column(name = "tel_per_vis_vis")
	private String telPerVisVis;
	@Column(name = "fax_per_vis_vis")
	private String faxPerVisVis;
	@Column(name = "email_per_vis_vis")
	private String emailPerVisVis;
	@Column(name = "is_conventionne")
	private Boolean isConventionne;
	@Column(name = "is_cng")
	private Boolean isCng;
	@Column(name = "is_ga_estimate")
	private Boolean isGaEstimate;
	@Column(name = "date_effet_convention")
	private LocalDate dateEffetConvention;
	@Column(name = "date_fin_convention")
	private LocalDate dateFinConvention;
	@Column(name = "is_four")
	private Boolean isFour;
	@Column(name = "is_marbre")
	private Boolean isMarbre;
	@Column(name = "login")
	private String login;
	@Column(name = "pwd")
	private String pwd;
	@ManyToOne
	private RefModeReglement reglement;
	@Column(name = "rib")
	private String rib;
	@Column(name = "is_bloque")
	private Boolean isBloque;
	@Column(name = "is_active")
	private Boolean isActive;
	@Column(name = "notation")
	private String notation;
	@Column(name = "adresse")
	private String adresse;
	@Column(name = "observation")
	private String observation;
	@Column(name = "is_agent_officiel")
	private Boolean isagentOfficiel;
	@Column(name = "is_multi_marque")
	private Boolean isMultiMarque;
	@Column(name = "capacite")
	private Double capacite;
	@Column(name = "montant_i_p")
	private Double montantIP;
	@Column(name = "solvant")
	private Double solvant;
	@Column(name = "hydro")
	private Double hydro;
	@Column(name = "t_h_m_o_p")
	private Double tauxHoraireMOPeintur;
	@Column(name = "t_h_m_o_r_t")
	private Double tauxHoraireMORemplacement;
	@Column(name = "t_h_m_o_r")
	private Double tauxHoraireMOReparation;
	@Column(name = "t_h_r_h_t")
	private Double tauxHorairesReparationHauteTechnicite;
	@Column(name = "m_p_b")
	private Double montagePareBrise;
	@Column(name = "m_p_b_a_j")
	private Double montagePareBriseAvecJoint;
	@Column(name = "m_v_p")
	private Double montageVitreDePorte;
	@Column(name = "m_v_a_a_c_j")
	private Double montageVoletDairAvecColleOuJoint;
	@Column(name = "t_h_m_o_p_r")
	private Double tauxHoraireMOPeinturRestourne;
	@Column(name = "t_h_m_o_r_t_r")
	private Double tauxHoraireMORemplacementRestourne;
	@Column(name = "t_h_r_h_t_r")
	private Double tauxHorairesReparationHauteTechniciteRestourne;
	@Column(name = "m_p_b_r")
	private Double  montagePareBriseRestourne;
	@Column(name = "m_p_b_j_r")
	private Double montagePareBriseAvecJointRestourne;
	@Column(name = "m_v_p_r")
	private Double montageVitreDePorteRestourne;
	@Column(name = "m_v_a_c_j_r")
	private Double montageVoletDairAvecColleOuJointRestourne;
	@Column(name = "t_h_m_o_r_r")
	private Double tauxHoraireMOReparationRestourne;
	@Column(name = "m_p_b_a_c")
	private Double montagePareBriseAvecColle;
	@Column(name = "m_p_b_a_c_r")
	private Double montagePareBriseAvecColleRestourne;
	@Column(name = "petite_fourniture")
	private Double petiteFourniture;
	@Column(name = "m_l_a_a_c")
	private Double montageLunetteAriereAvecColle;
	@Column(name = "r_m_l_a_a_c")
	private Double restourneMontageLunetteAriereAvecColle;
	@Column(name = "m_l_a_a_j")
	private Double montageLunetteAriereAvecjoint;
	@Column(name = "r_m_l_a_a_j")
	private Double restourneMontageLunetteAriereAvecjoint;
	
	
	@ManyToOne
	private Delegation ville;
	@Column(name = "date_debut_blocage")
	private LocalDate dateDebutBlocage;
	@Column(name = "date_fin_blocage")
	private LocalDate dateFinBlocage;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "reason_bloquage_id")
	private RaisonPec raisonBloquage;

	@OneToMany(mappedBy = "reparateur", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Orientation> orientations = new ArrayList<>();

	public Double getTauxHoraireMOPeintur() {
		return tauxHoraireMOPeintur;
	}

	public void setTauxHoraireMOPeintur(Double tauxHoraireMOPeintur) {
		this.tauxHoraireMOPeintur = tauxHoraireMOPeintur;
	}

	public Double getTauxHoraireMORemplacement() {
		return tauxHoraireMORemplacement;
	}

	public void setTauxHoraireMORemplacement(Double tauxHoraireMORemplacement) {
		this.tauxHoraireMORemplacement = tauxHoraireMORemplacement;
	}

	public Double getTauxHoraireMOReparation() {
		return tauxHoraireMOReparation;
	}

	public void setTauxHoraireMOReparation(Double tauxHoraireMOReparation) {
		this.tauxHoraireMOReparation = tauxHoraireMOReparation;
	}

	public Double getTauxHorairesReparationHauteTechnicite() {
		return tauxHorairesReparationHauteTechnicite;
	}

	public void setTauxHorairesReparationHauteTechnicite(Double tauxHorairesReparationHauteTechnicite) {
		this.tauxHorairesReparationHauteTechnicite = tauxHorairesReparationHauteTechnicite;
	}

	public Double getMontagePareBrise() {
		return montagePareBrise;
	}

	public void setMontagePareBrise(Double montagePareBrise) {
		this.montagePareBrise = montagePareBrise;
	}

	public Double getMontagePareBriseAvecJoint() {
		return montagePareBriseAvecJoint;
	}

	public void setMontagePareBriseAvecJoint(Double montagePareBriseAvecJoint) {
		this.montagePareBriseAvecJoint = montagePareBriseAvecJoint;
	}

	public Double getMontageVitreDePorte() {
		return montageVitreDePorte;
	}

	public void setMontageVitreDePorte(Double montageVitreDePorte) {
		this.montageVitreDePorte = montageVitreDePorte;
	}

	public Double getMontageVoletDairAvecColleOuJoint() {
		return montageVoletDairAvecColleOuJoint;
	}

	public void setMontageVoletDairAvecColleOuJoint(Double montageVoletDairAvecColleOuJoint) {
		this.montageVoletDairAvecColleOuJoint = montageVoletDairAvecColleOuJoint;
	}

	public Boolean getIsMultiMarque() {
		return isMultiMarque;
	}

	public void setIsMultiMarque(Boolean isMultiMarque) {
		this.isMultiMarque = isMultiMarque;
	}

	public Double getTauxHoraireMOPeinturRestourne() {
		return tauxHoraireMOPeinturRestourne;
	}

	public void setTauxHoraireMOPeinturRestourne(Double tauxHoraireMOPeinturRestourne) {
		this.tauxHoraireMOPeinturRestourne = tauxHoraireMOPeinturRestourne;
	}

	public Double getTauxHoraireMORemplacementRestourne() {
		return tauxHoraireMORemplacementRestourne;
	}

	public void setTauxHoraireMORemplacementRestourne(Double tauxHoraireMORemplacementRestourne) {
		this.tauxHoraireMORemplacementRestourne = tauxHoraireMORemplacementRestourne;
	}

	public Double getTauxHorairesReparationHauteTechniciteRestourne() {
		return tauxHorairesReparationHauteTechniciteRestourne;
	}

	public void setTauxHorairesReparationHauteTechniciteRestourne(Double tauxHorairesReparationHauteTechniciteRestourne) {
		this.tauxHorairesReparationHauteTechniciteRestourne = tauxHorairesReparationHauteTechniciteRestourne;
	}

	public Double getMontagePareBriseRestourne() {
		return montagePareBriseRestourne;
	}

	public void setMontagePareBriseRestourne(Double montagePareBriseRestourne) {
		this.montagePareBriseRestourne = montagePareBriseRestourne;
	}

	public Double getMontagePareBriseAvecJointRestourne() {
		return montagePareBriseAvecJointRestourne;
	}

	public void setMontagePareBriseAvecJointRestourne(Double montagePareBriseAvecJointRestourne) {
		this.montagePareBriseAvecJointRestourne = montagePareBriseAvecJointRestourne;
	}

	public Double getMontageVitreDePorteRestourne() {
		return montageVitreDePorteRestourne;
	}

	public void setMontageVitreDePorteRestourne(Double montageVitreDePorteRestourne) {
		this.montageVitreDePorteRestourne = montageVitreDePorteRestourne;
	}

	public Double getMontageVoletDairAvecColleOuJointRestourne() {
		return montageVoletDairAvecColleOuJointRestourne;
	}

	public void setMontageVoletDairAvecColleOuJointRestourne(Double montageVoletDairAvecColleOuJointRestourne) {
		this.montageVoletDairAvecColleOuJointRestourne = montageVoletDairAvecColleOuJointRestourne;
	}

	public Double getTauxHoraireMOReparationRestourne() {
		return tauxHoraireMOReparationRestourne;
	}

	public void setTauxHoraireMOReparationRestourne(Double tauxHoraireMOReparationRestourne) {
		this.tauxHoraireMOReparationRestourne = tauxHoraireMOReparationRestourne;
	}
	

	public Double getMontagePareBriseAvecColle() {
		return montagePareBriseAvecColle;
	}

	public void setMontagePareBriseAvecColle(Double montagePareBriseAvecColle) {
		this.montagePareBriseAvecColle = montagePareBriseAvecColle;
	}

	public Double getMontagePareBriseAvecColleRestourne() {
		return montagePareBriseAvecColleRestourne;
	}

	public void setMontagePareBriseAvecColleRestourne(Double montagePareBriseAvecColleRestourne) {
		this.montagePareBriseAvecColleRestourne = montagePareBriseAvecColleRestourne;
	}

	public Double getPetiteFourniture() {
		return petiteFourniture;
	}

	public void setPetiteFourniture(Double petiteFourniture) {
		this.petiteFourniture = petiteFourniture;
	}

	public Double getMontageLunetteAriereAvecColle() {
		return montageLunetteAriereAvecColle;
	}

	public void setMontageLunetteAriereAvecColle(Double montageLunetteAriereAvecColle) {
		this.montageLunetteAriereAvecColle = montageLunetteAriereAvecColle;
	}

	public Double getRestourneMontageLunetteAriereAvecColle() {
		return restourneMontageLunetteAriereAvecColle;
	}

	public void setRestourneMontageLunetteAriereAvecColle(Double restourneMontageLunetteAriereAvecColle) {
		this.restourneMontageLunetteAriereAvecColle = restourneMontageLunetteAriereAvecColle;
	}

	public Double getMontageLunetteAriereAvecjoint() {
		return montageLunetteAriereAvecjoint;
	}

	public void setMontageLunetteAriereAvecjoint(Double montageLunetteAriereAvecjoint) {
		this.montageLunetteAriereAvecjoint = montageLunetteAriereAvecjoint;
	}

	public Double getRestourneMontageLunetteAriereAvecjoint() {
		return restourneMontageLunetteAriereAvecjoint;
	}

	public void setRestourneMontageLunetteAriereAvecjoint(Double restourneMontageLunetteAriereAvecjoint) {
		this.restourneMontageLunetteAriereAvecjoint = restourneMontageLunetteAriereAvecjoint;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public List<VisAVis> getVisAViss() {
		return visAViss;
	}

	public void setVisAViss(List<VisAVis> visAViss) {
		this.visAViss = visAViss;
	}

	@OneToMany(mappedBy = "reparateur", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<VisAVis> visAViss = new ArrayList<>();

	@OneToMany(mappedBy = "reparateur", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<GarantieImplique> garantieImpliques = new ArrayList<>();

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "reparateur_marque_principale", joinColumns = {
			@JoinColumn(name = "reparateurs_id") }, inverseJoinColumns = {
					@JoinColumn(name = "marque_principales_id") })
	private Set<VehicleBrand> specialitePrincipales = new HashSet<>();

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "reparateur_autre_marque", joinColumns = {
			@JoinColumn(name = "reparateurs_id") }, inverseJoinColumns = { @JoinColumn(name = "autre_marques_id") })
	private Set<VehicleBrand> specialiteSecondaires = new HashSet<>();

	public Set<VehicleBrand> getSpecialitePrincipales() {
		return specialitePrincipales;
	}

	public void setSpecialitePrincipales(Set<VehicleBrand> specialitePrincipales) {
		this.specialitePrincipales = specialitePrincipales;
	}

	public Set<VehicleBrand> getSpecialiteSecondaires() {
		return specialiteSecondaires;
	}

	public void setSpecialiteSecondaires(Set<VehicleBrand> specialiteSecondaires) {
		this.specialiteSecondaires = specialiteSecondaires;
	}

	public LocalDate getDateDebutBlocage() {
		return dateDebutBlocage;
	}

	public void setDateDebutBlocage(LocalDate dateDebutBlocage) {
		this.dateDebutBlocage = dateDebutBlocage;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public LocalDate getDateFinBlocage() {
		return dateFinBlocage;
	}

	public void setDateFinBlocage(LocalDate dateFinBlocage) {
		this.dateFinBlocage = dateFinBlocage;
	}

	public String getAdresse() {
		return adresse;
	}

	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}
	

	public Double getMontantIP() {
		return montantIP;
	}

	public void setMontantIP(Double montantIP) {
		this.montantIP = montantIP;
	}

	public Double getSolvant() {
		return solvant;
	}

	public void setSolvant(Double solvant) {
		this.solvant = solvant;
	}

	public Double getHydro() {
		return hydro;
	}

	public void setHydro(Double hydro) {
		this.hydro = hydro;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRaisonSociale() {
		return raisonSociale;
	}

	public void setRaisonSociale(String raisonSociale) {
		this.raisonSociale = raisonSociale;
	}

	public String getRegistreCommerce() {
		return registreCommerce;
	}

	public void setRegistreCommerce(String registreCommerce) {
		this.registreCommerce = registreCommerce;
	}

	public String getMatriculeFiscale() {
		return matriculeFiscale;
	}

	public void setMatriculeFiscale(String matriculeFiscale) {
		this.matriculeFiscale = matriculeFiscale;
	}

	public String getNomPerVisVis() {
		return nomPerVisVis;
	}

	public void setNomPerVisVis(String nomPerVisVis) {
		this.nomPerVisVis = nomPerVisVis;
	}

	public String getPrenomPerVisVis() {
		return prenomPerVisVis;
	}

	public void setPrenomPerVisVis(String prenomPerVisVis) {
		this.prenomPerVisVis = prenomPerVisVis;
	}

	public String getTelPerVisVis() {
		return telPerVisVis;
	}

	public void setTelPerVisVis(String telPerVisVis) {
		this.telPerVisVis = telPerVisVis;
	}

	public Double getCapacite() {
		return capacite;
	}

	public void setCapacite(Double capacite) {
		this.capacite = capacite;
	}

	public String getFaxPerVisVis() {
		return faxPerVisVis;
	}

	public void setFaxPerVisVis(String faxPerVisVis) {
		this.faxPerVisVis = faxPerVisVis;
	}

	public String getEmailPerVisVis() {
		return emailPerVisVis;
	}

	public void setEmailPerVisVis(String emailPerVisVis) {
		this.emailPerVisVis = emailPerVisVis;
	}

	public Boolean getIsConventionne() {
		return isConventionne;
	}

	public void setIsConventionne(Boolean isConventionne) {
		this.isConventionne = isConventionne;
	}

	public Boolean getIsCng() {
		return isCng;
	}

	public void setIsCng(Boolean isCng) {
		this.isCng = isCng;
	}

	public Boolean getIsGaEstimate() {
		return isGaEstimate;
	}

	public void setIsGaEstimate(Boolean isGaEstimate) {
		this.isGaEstimate = isGaEstimate;
	}

	public LocalDate getDateEffetConvention() {
		return dateEffetConvention;
	}

	public void setDateEffetConvention(LocalDate dateEffetConvention) {
		this.dateEffetConvention = dateEffetConvention;
	}

	public LocalDate getDateFinConvention() {
		return dateFinConvention;
	}

	public void setDateFinConvention(LocalDate dateFinConvention) {
		this.dateFinConvention = dateFinConvention;
	}

	public Boolean getIsFour() {
		return isFour;
	}

	public void setIsFour(Boolean isFour) {
		this.isFour = isFour;
	}

	public Boolean getIsMarbre() {
		return isMarbre;
	}

	public void setIsMarbre(Boolean isMarbre) {
		this.isMarbre = isMarbre;
	}

	public RefModeReglement getReglement() {
		return reglement;
	}

	public void setReglement(RefModeReglement reglement) {
		this.reglement = reglement;
	}

	public String getRib() {
		return rib;
	}

	public void setRib(String rib) {
		this.rib = rib;
	}

	public Boolean getIsBloque() {
		return isBloque;
	}

	public void setIsBloque(Boolean isBloque) {
		this.isBloque = isBloque;
	}

	public String getNotation() {
		return notation;
	}

	public void setNotation(String notation) {
		this.notation = notation;
	}

	public String getObservation() {
		return observation;
	}

	public void setObservation(String observation) {
		this.observation = observation;
	}

	public Boolean getIsagentOfficiel() {
		return isagentOfficiel;
	}

	public void setIsagentOfficiel(Boolean isagentOfficiel) {
		this.isagentOfficiel = isagentOfficiel;
	}



	public Delegation getVille() {
		return ville;
	}

	public void setVille(Delegation ville) {
		this.ville = ville;
	}

	public List<Orientation> getOrientations() {
		return orientations;
	}

	public void setOrientations(List<Orientation> orientations) {
		this.orientations = orientations;
	}

	public List<GarantieImplique> getGarantieImpliques() {
		return garantieImpliques;
	}

	public void setGarantieImpliques(List<GarantieImplique> garantieImpliques) {
		this.garantieImpliques = garantieImpliques;
	}

	public RaisonPec getRaisonBloquage() {
		return raisonBloquage;
	}

	public void setRaisonBloquage(RaisonPec raisonBloquage) {
		this.raisonBloquage = raisonBloquage;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		Reparateur reparateur = (Reparateur) o;
		if (reparateur.getId() == null || getId() == null) {
			return false;
		}
		return Objects.equals(getId(), reparateur.getId());
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(getId());
	}

	@Override
	public String toString() {
		return "Reparateur [id=" + id + ", raisonSociale=" + raisonSociale + ", registreCommerce=" + registreCommerce
				+ ", matriculeFiscale=" + matriculeFiscale + ", nomPerVisVis=" + nomPerVisVis + ", prenomPerVisVis="
				+ prenomPerVisVis + ", telPerVisVis=" + telPerVisVis + ", faxPerVisVis=" + faxPerVisVis
				+ ", emailPerVisVis=" + emailPerVisVis + ", isConventionne=" + isConventionne + ", isCng=" + isCng
				+ ", isGaEstimate=" + isGaEstimate + ", dateEffetConvention=" + dateEffetConvention
				+ ", dateFinConvention=" + dateFinConvention + ", isFour=" + isFour + ", isMarbre=" + isMarbre
				+ ", reglement=" + reglement + ", rib=" + rib + ", isBloque=" + isBloque + ", isActive=" + isActive
				+ ", notation=" + notation + ", adresse=" + adresse + ", observation=" + observation
				+ ", isagentOfficiel=" + isagentOfficiel + ", capacite=" + capacite + ", ville=" + ville
				+ ", dateDebutBlocage=" + dateDebutBlocage + ", dateFinBlocage=" + dateFinBlocage + ", orientations="
				+ orientations + ", visAViss=" + visAViss + ", garantieImpliques=" + garantieImpliques
				+ ", specialitePrincipales=" + specialitePrincipales + ", specialiteSecondaires="
				+ specialiteSecondaires + "]";
	}



}
