package com.gaconnecte.auxilium.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A PointChoc.
 */
@Entity
@Table(name = "point_choc")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "pointchoc")
public class PointChoc implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
	@SequenceGenerator(name = "sequenceGenerator")
	private Long id;

	 @OneToOne(fetch = FetchType.LAZY)
	 @JoinColumn(name = "sinister_pec_id")
	 private SinisterPec sinisterPec;
	
	@Column(name = "toit")
	private Boolean toit;
	@Column(name = "avant")
	private Boolean avant;
	@Column(name = "arriere")
	private Boolean arriere;
	@Column(name = "arriere_gauche")
	private Boolean arriereGauche;
	@Column(name = "laterale_gauche")
	private Boolean lateraleGauche;
	@Column(name = "laterale_gauche_arriere")
	private Boolean lateraleGauchearriere;
	@Column(name = "laterale_gauche_avant")
	private Boolean lateraleGaucheAvant;
	@Column(name = "arriere_droite")
	private Boolean arriereDroite;
	@Column(name = "laterale_droite")
	private Boolean lateraledroite;
	@Column(name = "laterale_droite_avant")
	private Boolean lateraleDroiteAvant;
	@Column(name = "laterale_droite_arriere")
	private Boolean lateraleDroiteArriere;
	@Column(name = "avant_gauche")
	private Boolean avantGauche;
	@Column(name = "avant_droite")
	private Boolean avantDroite;
	@Column(name = "retroviseur_gauche")
	private Boolean retroviseurGauche;
	@Column(name = "retroviseur_droite")
	private Boolean retroviseurDroite;
	@Column(name = "lunette_arriere")
	private Boolean lunetteArriere;
	@Column(name = "pare_brise_avant")
	private Boolean pareBriseAvant;
	@Column(name = "vitre_avant_gauche")
	private Boolean vitreAvantGauche;
	@Column(name = "vitre_avant_droit")
	private Boolean vitreAvantDroit;
	@Column(name = "vitre_arriere_gauche")
	private Boolean vitreArriereGauche;
	@Column(name = "vitre_arriere_droit")
	private Boolean vitreArriereDroit;
	@Column(name = "triangle_arriere_gauche")
	private Boolean triangleArriereGauche;
	@Column(name = "triangle_arriere_droit")
	private Boolean triangleArriereDroit;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public SinisterPec getSinisterPec() {
		return sinisterPec;
	}

	public void setSinisterPec(SinisterPec sinisterPec) {
		this.sinisterPec = sinisterPec;
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
		PointChoc pointChoc = (PointChoc) o;
		if (pointChoc.getId() == null || getId() == null) {
			return false;
		}
		return Objects.equals(getId(), pointChoc.getId());
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(getId());
	}

	@Override
	public String toString() {
		return "PointChoc [toit=" + toit + ", avant=" + avant + ", arriere=" + arriere + ", arriereGauche="
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
