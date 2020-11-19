package com.gaconnecte.auxilium.service.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

public class OrdrePrestationVrDTO implements Serializable {
	
	private String numPrestation;
	
	private String referenceDossier;
	private String cie;
	
	private String nomRaison;
	private String contact;
	
	private String nomPrenom;
	private String premierConducteur;

	private String deuxiemeConducteur;
	private String adresse;
	private String telephone;
	private String immatriculation;
	private String marque;
	
	private Double jours;
	private String date1;
	private String  date2 ;

	
	
	public String getNumPrestation() {
		return numPrestation;
	}
	public void setNumPrestation(String numPrestation) {
		this.numPrestation = numPrestation;
	}
	public String getReferenceDossier() {
		return referenceDossier;
	}
	public void setReferenceDossier(String referenceDossier) {
		this.referenceDossier = referenceDossier;
	}
	public String getCie() {
		return cie;
	}
	public void setCie(String cie) {
		this.cie = cie;
	}
	public String getNomRaison() {
		return nomRaison;
	}
	public void setNomRaison(String nomRaison) {
		this.nomRaison = nomRaison;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public String getNomPrenom() {
		return nomPrenom;
	}
	public void setNomPrenom(String nomPrenom) {
		this.nomPrenom = nomPrenom;
	}
	
	public String getPremierConducteur() {
		return premierConducteur;
	}
	public void setPremierConducteur(String premierConducteur) {
		this.premierConducteur = premierConducteur;
	}
	public String getDeuxiemeConducteur() {
		return deuxiemeConducteur;
	}
	public void setDeuxiemeConducteur(String deuxiemeConducteur) {
		this.deuxiemeConducteur = deuxiemeConducteur;
	}
	public String getAdresse() {
		return adresse;
	}
	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getImmatriculation() {
		return immatriculation;
	}
	public void setImmatriculation(String immatriculation) {
		this.immatriculation = immatriculation;
	}
	public String getMarque() {
		return marque;
	}
	public void setMarque(String marque) {
		this.marque = marque;
	}
	public Double getJours() {
		return jours;
	}
	public void setJours(Double jours) {
		this.jours = jours;
	}
	public String getDate1() {
		return date1;
	}
	public void setDate1(String date1) {
		this.date1 = date1;
	}
	public String getDate2() {
		return date2;
	}
	public void setDate2(String date2) {
		this.date2 = date2;
	}
	
	
	
	


}
