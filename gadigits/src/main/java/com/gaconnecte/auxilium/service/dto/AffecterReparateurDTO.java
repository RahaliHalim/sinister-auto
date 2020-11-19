package com.gaconnecte.auxilium.service.dto;

import java.io.Serializable;

public class AffecterReparateurDTO implements Serializable{
	
	/**
	 * 
	 */
	
	private PrestationPECDTO perstationPEC;
	private VehiculeAssureDTO vehiculeAssure;
	private String assureNom;
	private String assureGsm;
	private String reparateurNom;
	private String reparateurContactNom;
	private String reparateurContactGsm;
	
	public PrestationPECDTO getPerstationPEC() {
		return perstationPEC;
	}
	public void setPerstationPEC(PrestationPECDTO perstationPEC) {
		this.perstationPEC = perstationPEC;
	}
	public VehiculeAssureDTO getVehiculeAssure() {
		return vehiculeAssure;
	}
	public void setVehiculeAssure(VehiculeAssureDTO vehiculeAssure) {
		this.vehiculeAssure = vehiculeAssure;
	}
	public String getAssureNom() {
		return assureNom;
	}
	public void setAssureNom(String assureNom) {
		this.assureNom = assureNom;
	}
	public String getAssureGsm() {
		return assureGsm;
	}
	public void setAssureGsm(String assureGsm) {
		this.assureGsm = assureGsm;
	}
	public String getReparateurNom() {
		return reparateurNom;
	}
	public void setReparateurNom(String reparateurNom) {
		this.reparateurNom = reparateurNom;
	}
	public String getReparateurContactNom() {
		return reparateurContactNom;
	}
	public void setReparateurContactNom(String reparateurContactNom) {
		this.reparateurContactNom = reparateurContactNom;
	}
	public String getReparateurContactGsm() {
		return reparateurContactGsm;
	}
	public void setReparateurContactGsm(String reparateurContactGsm) {
		this.reparateurContactGsm = reparateurContactGsm;
	}
	public String getReparateurAdresse() {
		return reparateurAdresse;
	}
	public void setReparateurAdresse(String reparateurAdresse) {
		this.reparateurAdresse = reparateurAdresse;
	}
	private String reparateurAdresse;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((assureGsm == null) ? 0 : assureGsm.hashCode());
		result = prime * result + ((assureNom == null) ? 0 : assureNom.hashCode());
		result = prime * result + ((perstationPEC == null) ? 0 : perstationPEC.hashCode());
		result = prime * result + ((reparateurAdresse == null) ? 0 : reparateurAdresse.hashCode());
		result = prime * result + ((reparateurContactGsm == null) ? 0 : reparateurContactGsm.hashCode());
		result = prime * result + ((reparateurContactNom == null) ? 0 : reparateurContactNom.hashCode());
		result = prime * result + ((reparateurNom == null) ? 0 : reparateurNom.hashCode());
		result = prime * result + ((vehiculeAssure == null) ? 0 : vehiculeAssure.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AffecterReparateurDTO other = (AffecterReparateurDTO) obj;
		if (assureGsm == null) {
			if (other.assureGsm != null)
				return false;
		} else if (!assureGsm.equals(other.assureGsm))
			return false;
		if (assureNom == null) {
			if (other.assureNom != null)
				return false;
		} else if (!assureNom.equals(other.assureNom))
			return false;
		if (perstationPEC == null) {
			if (other.perstationPEC != null)
				return false;
		} else if (!perstationPEC.equals(other.perstationPEC))
			return false;
		if (reparateurAdresse == null) {
			if (other.reparateurAdresse != null)
				return false;
		} else if (!reparateurAdresse.equals(other.reparateurAdresse))
			return false;
		if (reparateurContactGsm == null) {
			if (other.reparateurContactGsm != null)
				return false;
		} else if (!reparateurContactGsm.equals(other.reparateurContactGsm))
			return false;
		if (reparateurContactNom == null) {
			if (other.reparateurContactNom != null)
				return false;
		} else if (!reparateurContactNom.equals(other.reparateurContactNom))
			return false;
		if (reparateurNom == null) {
			if (other.reparateurNom != null)
				return false;
		} else if (!reparateurNom.equals(other.reparateurNom))
			return false;
		if (vehiculeAssure == null) {
			if (other.vehiculeAssure != null)
				return false;
		} else if (!vehiculeAssure.equals(other.vehiculeAssure))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "AffecterReparateurDTO [perstationPEC=" + perstationPEC + ", vehiculeAssure=" + vehiculeAssure
				+ ", assureNom=" + assureNom + ", assureGsm=" + assureGsm + ", reparateurNom=" + reparateurNom
				+ ", reparateurContactNom=" + reparateurContactNom + ", reparateurContactGsm=" + reparateurContactGsm
				+ ", reparateurAdresse=" + reparateurAdresse + "]";
	}
	

	

}
