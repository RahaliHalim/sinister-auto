package com.gaconnecte.auxilium.service.dto;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;

import com.gaconnecte.auxilium.domain.SinisterPec;

/**
 * A DTO for the PointChoc entity.
 */
public class PointChocDTO implements Serializable {

	private Long id;
	private Long sinisterPecId;
	private Boolean toit;
	private Boolean avant;
	private Boolean arriere;
	private Boolean arriereGauche;
	private Boolean lateraleGauche;
	private Boolean lateraleGauchearriere;
	private Boolean lateraleGaucheAvant;
	private Boolean arriereDroite;
	private Boolean lateraledroite;
	private Boolean lateraleDroiteAvant;
	private Boolean lateraleDroiteArriere;
	private Boolean avantGauche;
	private Boolean avantDroite;
	private Boolean retroviseurGauche;
	private Boolean retroviseurDroite;
	private Boolean lunetteArriere;
	private Boolean pareBriseAvant;
	private Boolean vitreAvantGauche;
	private Boolean vitreAvantDroit;
	private Boolean vitreArriereGauche;
	private Boolean vitreArriereDroit;
	private Boolean triangleArriereGauche;
	private Boolean triangleArriereDroit;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getSinisterPecId() {
		return sinisterPecId;
	}

	public void setSinisterPecId(Long sinisterPecId) {
		this.sinisterPecId = sinisterPecId;
	}

	public Boolean getToit() {
		return toit;
	}

	public void setToit(Boolean toit) {
		this.toit = toit;
	}

	public Boolean getAvant() {
		return avant;
	}

	public void setAvant(Boolean avant) {
		this.avant = avant;
	}

	public Boolean getArriere() {
		return arriere;
	}

	public void setArriere(Boolean arriere) {
		this.arriere = arriere;
	}

	public Boolean getArriereGauche() {
		return arriereGauche;
	}

	public void setArriereGauche(Boolean arriereGauche) {
		this.arriereGauche = arriereGauche;
	}

	public Boolean getLateraleGauche() {
		return lateraleGauche;
	}

	public void setLateraleGauche(Boolean lateraleGauche) {
		this.lateraleGauche = lateraleGauche;
	}

	public Boolean getLateraleGauchearriere() {
		return lateraleGauchearriere;
	}

	public void setLateraleGauchearriere(Boolean lateraleGauchearriere) {
		this.lateraleGauchearriere = lateraleGauchearriere;
	}

	public Boolean getLateraleGaucheAvant() {
		return lateraleGaucheAvant;
	}

	public void setLateraleGaucheAvant(Boolean lateraleGaucheAvant) {
		this.lateraleGaucheAvant = lateraleGaucheAvant;
	}

	public Boolean getArriereDroite() {
		return arriereDroite;
	}

	public void setArriereDroite(Boolean arriereDroite) {
		this.arriereDroite = arriereDroite;
	}

	public Boolean getLateraledroite() {
		return lateraledroite;
	}

	public void setLateraledroite(Boolean lateraledroite) {
		this.lateraledroite = lateraledroite;
	}

	public Boolean getLateraleDroiteAvant() {
		return lateraleDroiteAvant;
	}

	public void setLateraleDroiteAvant(Boolean lateraleDroiteAvant) {
		this.lateraleDroiteAvant = lateraleDroiteAvant;
	}

	public Boolean getLateraleDroiteArriere() {
		return lateraleDroiteArriere;
	}

	public void setLateraleDroiteArriere(Boolean lateraleDroiteArriere) {
		this.lateraleDroiteArriere = lateraleDroiteArriere;
	}

	public Boolean getAvantGauche() {
		return avantGauche;
	}

	public void setAvantGauche(Boolean avantGauche) {
		this.avantGauche = avantGauche;
	}

	public Boolean getAvantDroite() {
		return avantDroite;
	}

	public void setAvantDroite(Boolean avantDroite) {
		this.avantDroite = avantDroite;
	}

	public Boolean getRetroviseurGauche() {
		return retroviseurGauche;
	}

	public void setRetroviseurGauche(Boolean retroviseurGauche) {
		this.retroviseurGauche = retroviseurGauche;
	}

	public Boolean getRetroviseurDroite() {
		return retroviseurDroite;
	}

	public void setRetroviseurDroite(Boolean retroviseurDroite) {
		this.retroviseurDroite = retroviseurDroite;
	}

	public Boolean getLunetteArriere() {
		return lunetteArriere;
	}

	public void setLunetteArriere(Boolean lunetteArriere) {
		this.lunetteArriere = lunetteArriere;
	}

	public Boolean getPareBriseAvant() {
		return pareBriseAvant;
	}

	public void setPareBriseAvant(Boolean pareBriseAvant) {
		this.pareBriseAvant = pareBriseAvant;
	}

	public Boolean getVitreAvantGauche() {
		return vitreAvantGauche;
	}

	public void setVitreAvantGauche(Boolean vitreAvantGauche) {
		this.vitreAvantGauche = vitreAvantGauche;
	}

	public Boolean getVitreAvantDroit() {
		return vitreAvantDroit;
	}

	public void setVitreAvantDroit(Boolean vitreAvantDroit) {
		this.vitreAvantDroit = vitreAvantDroit;
	}

	public Boolean getVitreArriereGauche() {
		return vitreArriereGauche;
	}

	public void setVitreArriereGauche(Boolean vitreArriereGauche) {
		this.vitreArriereGauche = vitreArriereGauche;
	}

	public Boolean getVitreArriereDroit() {
		return vitreArriereDroit;
	}

	public void setVitreArriereDroit(Boolean vitreArriereDroit) {
		this.vitreArriereDroit = vitreArriereDroit;
	}

	public Boolean getTriangleArriereGauche() {
		return triangleArriereGauche;
	}

	public void setTriangleArriereGauche(Boolean triangleArriereGauche) {
		this.triangleArriereGauche = triangleArriereGauche;
	}

	public Boolean getTriangleArriereDroit() {
		return triangleArriereDroit;
	}

	public void setTriangleArriereDroit(Boolean triangleArriereDroit) {
		this.triangleArriereDroit = triangleArriereDroit;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		PointChocDTO pointChocDTO = (PointChocDTO) o;
		if (pointChocDTO.getId() == null || getId() == null) {
			return false;
		}
		return Objects.equals(getId(), pointChocDTO.getId());
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(getId());
	}

	@Override
	public String toString() {
		return "PointChocDTO [toit=" + toit + ", avant=" + avant + ", arriere=" + arriere + ", arriereGauche="
				+ arriereGauche + ", lateraleGauche=" + lateraleGauche + ", lateraleGauchearriere="
				+ lateraleGauchearriere + ", lateraleGaucheAvant=" + lateraleGaucheAvant + ", arriereDroite="
				+ arriereDroite + ", lateraledroite=" + lateraledroite + ", lateraleDroiteAvant=" + lateraleDroiteAvant
				+ ", lateraleDroiteArriere=" + lateraleDroiteArriere + ", avantGauche=" + avantGauche + ", avantDroite="
				+ avantDroite + ", retroviseurGauche=" + retroviseurGauche + ", retroviseurDroite=" + retroviseurDroite
				+ ", lunetteArriere=" + lunetteArriere + ", pareBriseAvant=" + pareBriseAvant + ", vitreAvantGauche="
				+ vitreAvantGauche + ", vitreAvantDroit=" + vitreAvantDroit + ", vitreArriereGauche="
				+ vitreArriereGauche + ", vitreArriereDroit=" + vitreArriereDroit + ", triangleArriereGauche="
				+ triangleArriereGauche + ", triangleArriereDroit=" + triangleArriereDroit + "]";
	}

}
