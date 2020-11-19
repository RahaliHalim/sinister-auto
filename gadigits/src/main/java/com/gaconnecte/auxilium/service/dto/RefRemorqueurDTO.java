package com.gaconnecte.auxilium.service.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import javax.persistence.Column;

/**
 * A DTO for the RefRemorqueur entity.
 */
public class RefRemorqueurDTO implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

	private Boolean isBloque;
	private Boolean isConventionne;
	private Boolean isDelete;
	private Boolean hasHabillage;
	private Boolean isFree;

	private String reclamation;

	private String mdp;
	private String telephone;
	private String adresse;
	private String matriculeFiscale;
	private Integer codeCategorie;

	private Integer nombreCamion;

	private Long societeId;
	private String societeRaisonSociale;
	private Double vatRate = 0D;

	private Long gouvernoratId;
	private Double latitude;

	private Double longitude;
	private String mail;

	private String libelleGouvernorat;
	private String registreCommerce;
	private String numEtablissement;
	private Long villeId;
	private String banque;
	private String agence;
	private String rib;
	private Boolean isAssujettieTva;
	private Double codeTVA;
	private Boolean isConnected;
	private String deviceId;
	private List<VisAVisDTO> visAViss;

	public List<VisAVisDTO> getVisAViss() {
		return visAViss;
	}

	public void setVisAViss(List<VisAVisDTO> visAViss) {
		this.visAViss = visAViss;
	}

	public String getRegistreCommerce() {
		return registreCommerce;
	}

	public void setRegistreCommerce(String registreCommerce) {
		this.registreCommerce = registreCommerce;
	}

	public String getNumEtablissement() {
		return numEtablissement;
	}

	public void setNumEtablissement(String numEtablissement) {
		this.numEtablissement = numEtablissement;
	}

	public Long getVilleId() {
		return villeId;
	}

	public void setVilleId(Long villeId) {
		this.villeId = villeId;
	}

	public Boolean getIsConnected() {
		return isConnected;
	}

	public void setIsConnected(Boolean isConnected) {
		this.isConnected = isConnected;
	}

	public String getBanque() {
		return banque;
	}

	public void setBanque(String banque) {
		this.banque = banque;
	}

	public String getAgence() {
		return agence;
	}

	public void setAgence(String agence) {
		this.agence = agence;
	}

	public String getRib() {
		return rib;
	}

	public void setRib(String rib) {
		this.rib = rib;
	}

	public Boolean getIsAssujettieTva() {
		return isAssujettieTva;
	}

	public void setIsAssujettieTva(Boolean isAssujettieTva) {
		this.isAssujettieTva = isAssujettieTva;
	}

	public Double getCodeTVA() {
		return codeTVA;
	}

	public void setCodeTVA(Double codeTVA) {
		this.codeTVA = codeTVA;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Boolean getIsBloque() {
		return isBloque;
	}

	public void setIsBloque(Boolean isBloque) {
		this.isBloque = isBloque;
	}

	public Boolean getIsConventionne() {
		return isConventionne;
	}

	public void setIsConventionne(Boolean isConventionne) {
		this.isConventionne = isConventionne;
	}

	public Boolean getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Boolean isDelete) {
		this.isDelete = isDelete;
	}

	public Boolean isHasHabillage() {
		return hasHabillage != null ? hasHabillage : Boolean.FALSE;
	}

	public Boolean getHasHabillage() {
		return hasHabillage;
	}

	public void setHasHabillage(Boolean hasHabillage) {
		this.hasHabillage = hasHabillage;
	}

	public Boolean getIsFree() {
		return isFree;
	}

	public void setIsFree(Boolean isFree) {
		this.isFree = isFree;
	}

	public String getReclamation() {
		return reclamation;
	}

	public void setReclamation(String reclamation) {
		this.reclamation = reclamation;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getAdresse() {
		return adresse;
	}

	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}

	public String getMatriculeFiscale() {
		return matriculeFiscale;
	}

	public void setMatriculeFiscale(String matriculeFiscale) {
		this.matriculeFiscale = matriculeFiscale;
	}

	public Integer getCodeCategorie() {
		return codeCategorie;
	}

	public void setCodeCategorie(Integer codeCategorie) {
		this.codeCategorie = codeCategorie;
	}

	public Integer getNombreCamion() {
		return nombreCamion;
	}

	public void setNombreCamion(Integer nombreCamion) {
		this.nombreCamion = nombreCamion;
	}

	public Long getSocieteId() {
		return societeId;
	}

	public void setSocieteId(Long societeId) {
		this.societeId = societeId;
	}

	public String getSocieteRaisonSociale() {
		return societeRaisonSociale;
	}

	public void setSocieteRaisonSociale(String societeRaisonSociale) {
		this.societeRaisonSociale = societeRaisonSociale;
	}

	public Double getVatRate() {
		return vatRate;
	}

	public void setVatRate(Double vatRate) {
		this.vatRate = vatRate;
	}

	public Long getGouvernoratId() {
		return gouvernoratId;
	}

	public void setGouvernoratId(Long gouvernoratId) {
		this.gouvernoratId = gouvernoratId;
	}

	public String getLibelleGouvernorat() {
		return libelleGouvernorat;
	}

	public void setLibelleGouvernorat(String libelleGouvernorat) {
		this.libelleGouvernorat = libelleGouvernorat;
	}

	public String getMdp() {
		return mdp;
	}

	public void setMdp(String mdp) {
		this.mdp = mdp;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 13 * hash + Objects.hashCode(this.id);
		return hash;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final RefRemorqueurDTO other = (RefRemorqueurDTO) obj;
		if (!Objects.equals(this.id, other.id)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "RefRemorqueurDTO{" + "id=" + id + ", isBloque=" + isBloque + ", isConventionne=" + isConventionne
				+ ", isDelete=" + isDelete + ", hasHabillage=" + hasHabillage + ", isFree=" + isFree + ", reclamation="
				+ reclamation + ", telephone=" + telephone + ", adresse=" + adresse + ", matriculeFiscale="
				+ matriculeFiscale + ", codeCategorie=" + codeCategorie + ", nombreCamion=" + nombreCamion
				+ ", societeId=" + societeId + ", societeRaisonSociale=" + societeRaisonSociale + ", vatRate=" + vatRate
				+ ", gouvernoratId=" + gouvernoratId + ", libelleGouvernorat=" + libelleGouvernorat + '}';
	}

}
