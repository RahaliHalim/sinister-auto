package com.gaconnecte.auxilium.service.dto;

import com.gaconnecte.auxilium.domain.RefModeReglement;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.javers.core.metamodel.annotation.DiffIgnore;

/**
 * A DTO for the Reparateur entity.
 */
public class ReparateurDTO implements Serializable {
	private Long id;
	private String raisonSociale;
	private String registreCommerce;
	private String matriculeFiscale;
	private String nomPerVisVis;
	private String prenomPerVisVis;
	private String telPerVisVis;
	private String faxPerVisVis;
	private String emailPerVisVis;
	private Boolean isConventionne;
	private Boolean isCng;
	private Boolean isGaEstimate;
	private LocalDate dateDebutBlocage;
	private LocalDate dateFinBlocage;
	private LocalDate dateEffetConvention;
	private LocalDate dateFinConvention;
	private Boolean isFour;
	private Boolean isActive;
	private Boolean isMarbre;
	private RefModeReglement reglement;
	private String rib;
	private Boolean isBloque;
	private String notation;
	private String observation;
	private Boolean isagentOfficiel;
	private Boolean isMultiMarque;
	private Double remiseMoPeintur;
	private Double remiseIngredientPeinture;
	private Double thRemplacement;
	private Double thReparation;
	private Double thMoPeinture;
	private Double thIngredientPeinture;
	private Double thPetiteFourniture;
	private Double capacite;
	private Double montantIP;
	private Double solvant;
	private Double tauxHoraireMOPeintur;
	private Double tauxHoraireMORemplacement;
	private Double tauxHoraireMOReparation;
	private Double tauxHorairesReparationHauteTechnicite;
	private Double montagePareBrise;
	private Double montagePareBriseAvecJoint;
	private Double montageVitreDePorte;
	private Double montageVoletDairAvecColleOuJoint;
	private Double tauxHoraireMOPeinturRestourne;
	private Double tauxHoraireMORemplacementRestourne;
	private Double tauxHorairesReparationHauteTechniciteRestourne;
	private Double montagePareBriseAvecColle;
	private Double montagePareBriseAvecColleRestourne;
	private Double montagePareBriseRestourne;
	private Double montagePareBriseAvecJointRestourne;
	private Double montageVitreDePorteRestourne;
	private Double montageVoletDairAvecColleOuJointRestourne;
	private Double tauxHoraireMOReparationRestourne;
	private Double montageLunetteAriereAvecColle;
	private Double restourneMontageLunetteAriereAvecColle;
	private Double montageLunetteAriereAvecjoint;
	private Double restourneMontageLunetteAriereAvecjoint;
	private Double petiteFourniture;
	private Double hydro;
	private Long villeId;
	private String villeLibelle;
	private String adresse;
	private String login;
	private String pwd;
	private String raisonBloquageLabel;
	@DiffIgnore
	private Long raisonBloquageId;
	@DiffIgnore
	private Long reglementId;
	private String libelleGouvernorat;
	@DiffIgnore
	private Long gouvernoratId;
	
	private List<OrientationDTO> orientations;

	private List<VisAVisDTO> visAViss;
	
	private List<GarantieImpliqueDTO> garantieImpliques;
	
	@DiffIgnore
	private List<VehicleBrandDTO> specialitePrincipales;
	@DiffIgnore
	private List<VehicleBrandDTO> specialiteSecondaires;

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

	public Long getVilleId() {
		return villeId;
	}

	public void setVilleId(Long villeId) {
		this.villeId = villeId;
	}

	public String getAdresse() {
		return adresse;
	}

	public Boolean getIsMultiMarque() {
		return isMultiMarque;
	}

	public void setIsMultiMarque(Boolean isMultiMarque) {
		this.isMultiMarque = isMultiMarque;
	}

	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public String getVilleLibelle() {
		return villeLibelle;
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

	public String getLibelleGouvernorat() {
		return libelleGouvernorat;
	}

	public void setLibelleGouvernorat(String libelleGouvernorat) {
		this.libelleGouvernorat = libelleGouvernorat;
	}

	public Long getGouvernoratId() {
		return gouvernoratId;
	}

	public Double getRestourneMontageLunetteAriereAvecjoint() {
		return restourneMontageLunetteAriereAvecjoint;
	}

	public void setRestourneMontageLunetteAriereAvecjoint(Double restourneMontageLunetteAriereAvecjoint) {
		this.restourneMontageLunetteAriereAvecjoint = restourneMontageLunetteAriereAvecjoint;
	}

	public void setGouvernoratId(Long gouvernoratId) {
		this.gouvernoratId = gouvernoratId;
	}

	public void setVilleLibelle(String villeLibelle) {
		this.villeLibelle = villeLibelle;
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

	public Double getPetiteFourniture() {
		return petiteFourniture;
	}

	public void setPetiteFourniture(Double petiteFourniture) {
		this.petiteFourniture = petiteFourniture;
	}

	public void setPrenomPerVisVis(String prenomPerVisVis) {
		this.prenomPerVisVis = prenomPerVisVis;
	}

	public String getTelPerVisVis() {
		return telPerVisVis;
	}

	public List<VisAVisDTO> getVisAViss() {
		return visAViss;
	}

	public void setVisAViss(List<VisAVisDTO> visAViss) {
		this.visAViss = visAViss;
	}

	public void setTelPerVisVis(String telPerVisVis) {
		this.telPerVisVis = telPerVisVis;
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

	public LocalDate getDateDebutBlocage() {
		return dateDebutBlocage;
	}

	public void setDateDebutBlocage(LocalDate dateDebutBlocage) {
		this.dateDebutBlocage = dateDebutBlocage;
	}

	public LocalDate getDateFinBlocage() {
		return dateFinBlocage;
	}

	public void setDateFinBlocage(LocalDate dateFinBlocage) {
		this.dateFinBlocage = dateFinBlocage;
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

	public Double getRemiseMoPeintur() {
		return remiseMoPeintur;
	}

	public void setRemiseMoPeintur(Double remiseMoPeintur) {
		this.remiseMoPeintur = remiseMoPeintur;
	}

	public Double getRemiseIngredient_peinture() {
		return remiseIngredientPeinture;
	}

	public void setRemiseIngredient_peinture(Double remiseIngredientPeinture) {
		this.remiseIngredientPeinture = remiseIngredientPeinture;
	}

	public Double getThRemplacement() {
		return thRemplacement;
	}

	public void setThRemplacement(Double thRemplacement) {
		this.thRemplacement = thRemplacement;
	}

	public Double getThReparation() {
		return thReparation;
	}

	public void setThReparation(Double thReparation) {
		this.thReparation = thReparation;
	}

	public Double getThMoPeinture() {
		return thMoPeinture;
	}

	public void setThMoPeinture(Double thMoPeinture) {
		this.thMoPeinture = thMoPeinture;
	}

	public Double getThIngredientPeinture() {
		return thIngredientPeinture;
	}

	public void setThIngredientPeinture(Double thIngredientPeinture) {
		this.thIngredientPeinture = thIngredientPeinture;
	}

	public Double getThPetiteFourniture() {
		return thPetiteFourniture;
	}

	public void setThPetiteFourniture(Double thPetiteFourniture) {
		this.thPetiteFourniture = thPetiteFourniture;
	}

	public Long getReglementId() {
		return reglementId;
	}

	public void setReglementId(Long reglementId) {
		this.reglementId = reglementId;
	}

	public Double getCapacite() {
		return capacite;
	}

	public void setCapacite(Double capacite) {
		this.capacite = capacite;
	}

	public Double getRemiseIngredientPeinture() {
		return remiseIngredientPeinture;
	}

	public void setRemiseIngredientPeinture(Double remiseIngredientPeinture) {
		this.remiseIngredientPeinture = remiseIngredientPeinture;
	}

	public List<OrientationDTO> getOrientations() {
		return orientations;
	}

	public void setOrientations(List<OrientationDTO> orientations) {
		this.orientations = orientations;
	}

	public List<GarantieImpliqueDTO> getGarantieImpliques() {
		return garantieImpliques;
	}

	public void setGarantieImpliques(List<GarantieImpliqueDTO> garantieImpliques) {
		this.garantieImpliques = garantieImpliques;
	}

	public List<VehicleBrandDTO> getSpecialitePrincipales() {
		return specialitePrincipales;
	}

	public void setSpecialitePrincipales(List<VehicleBrandDTO> specialitePrincipales) {
		this.specialitePrincipales = specialitePrincipales;
	}

	public List<VehicleBrandDTO> getSpecialiteSecondaires() {
		return specialiteSecondaires;
	}

	public void setSpecialiteSecondaires(List<VehicleBrandDTO> specialiteSecondaires) {
		this.specialiteSecondaires = specialiteSecondaires;
	}

	public Double getTauxHoraireMOPeintur() {
		return tauxHoraireMOPeintur;
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

	public Double getTauxHoraireMOPeinturRestourne() {
		return tauxHoraireMOPeinturRestourne;
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

	public void setTauxHorairesReparationHauteTechniciteRestourne(
			Double tauxHorairesReparationHauteTechniciteRestourne) {
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

	public String getRaisonBloquageLabel() {
		return raisonBloquageLabel;
	}

	public void setRaisonBloquageLabel(String raisonBloquageLabel) {
		this.raisonBloquageLabel = raisonBloquageLabel;
	}

	public Long getRaisonBloquageId() {
		return raisonBloquageId;
	}

	public void setRaisonBloquageId(Long raisonBloquageId) {
		this.raisonBloquageId = raisonBloquageId;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		ReparateurDTO reparateurDTO = (ReparateurDTO) o;
		if (reparateurDTO.getId() == null || getId() == null) {
			return false;
		}
		return Objects.equals(getId(), reparateurDTO.getId());
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(getId());
	}

	@Override
	public String toString() {
		return "ReparateurDTO [id=" + id + ", raisonSociale=" + raisonSociale + ", registreCommerce=" + registreCommerce
				+ ", matriculeFiscale=" + matriculeFiscale + ", nomPerVisVis=" + nomPerVisVis + ", prenomPerVisVis="
				+ prenomPerVisVis + ", telPerVisVis=" + telPerVisVis + ", faxPerVisVis=" + faxPerVisVis
				+ ", emailPerVisVis=" + emailPerVisVis + ", isConventionne=" + isConventionne + ", isCng=" + isCng
				+ ", isGaEstimate=" + isGaEstimate + ", dateDebutBlocage=" + dateDebutBlocage + ", dateFinBlocage="
				+ dateFinBlocage + ", dateEffetConvention=" + dateEffetConvention + ", dateFinConvention="
				+ dateFinConvention + ", isFour=" + isFour + ", isMarbre=" + isMarbre + ", reglement=" + reglement
				+ ", rib=" + rib + ", isBloque=" + isBloque + ", notation=" + notation + ", observation=" + observation
				+ ", isagentOfficiel=" + isagentOfficiel + ", remiseMoPeintur=" + remiseMoPeintur
				+ ", remiseIngredientPeinture=" + remiseIngredientPeinture + ", thRemplacement=" + thRemplacement
				+ ", thReparation=" + thReparation + ", thMoPeinture=" + thMoPeinture + ", thIngredientPeinture="
				+ thIngredientPeinture + ", thPetiteFourniture=" + thPetiteFourniture + ", villeId=" + villeId
				+ ", villeLibelle=" + villeLibelle + ", adresse=" + adresse + ", reglementId=" + reglementId
				+ ", libelleGouvernorat=" + libelleGouvernorat + ", gouvernoratId=" + gouvernoratId + ", orientations="
				+ orientations + ", visAViss=" + visAViss + ", garantieImpliques=" + garantieImpliques
				+ ", specialitePrincipales=" + specialitePrincipales + ", specialiteSecondaires="
				+ specialiteSecondaires + "]";
	}

}
