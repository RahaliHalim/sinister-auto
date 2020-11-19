package com.gaconnecte.auxilium.domain;

import java.time.LocalDate;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "view_extract_piece")
public class ViewExtractPiece {
	
	 @Id
	 private Long id;
	 
	@Column(name = "date_accident")
    private LocalDate dateAccident;
	
	@Column(name = "operation_date")
    private LocalDate operationDate;
	
	@Column(name = "point_de_choc")
    private String pointChoc;
	
	@Column(name = "reparateur")
    private String reparateur;
	
	@Column(name = "zone")
    private String zone;
	
	@Column(name = "n_chassis")
    private String nChassis;
	
	@Column(name = "immatriculation")
    private String immatriculation;
	
	@Column(name = "date_p_circulation")
    private LocalDate datePremCirculation;
	
	@Column(name = "marque")
    private String marque;
	
	@Column(name = "modele")
    private String modele;
	
	@Column(name = "designation_piece")
    private String designationPiece;
	
	@Column(name = "reference_piece")
    private String referencePiece;
	
	@Column(name = "nature_piece")
    private String naturePiece;
	
	@Column(name = "total_ht")
    private Double totalHt;

	public LocalDate getDateAccident() {
		return dateAccident;
	}

	public void setDateAccident(LocalDate dateAccident) {
		this.dateAccident = dateAccident;
	}

	public LocalDate getOperationDate() {
		return operationDate;
	}

	public void setOperationDate(LocalDate operationDate) {
		this.operationDate = operationDate;
	}

	public String getPointChoc() {
		return pointChoc;
	}

	public void setPointChoc(String pointChoc) {
		this.pointChoc = pointChoc;
	}

	public String getReparateur() {
		return reparateur;
	}

	public void setReparateur(String reparateur) {
		this.reparateur = reparateur;
	}

	public String getZone() {
		return zone;
	}

	public void setZone(String zone) {
		this.zone = zone;
	}

	public String getnChassis() {
		return nChassis;
	}

	public void setnChassis(String nChassis) {
		this.nChassis = nChassis;
	}

	public String getImmatriculation() {
		return immatriculation;
	}

	public void setImmatriculation(String immatriculation) {
		this.immatriculation = immatriculation;
	}

	public LocalDate getDatePremCirculation() {
		return datePremCirculation;
	}

	public void setDatePremCirculation(LocalDate datePremCirculation) {
		this.datePremCirculation = datePremCirculation;
	}

	public String getMarque() {
		return marque;
	}

	public void setMarque(String marque) {
		this.marque = marque;
	}

	public String getModele() {
		return modele;
	}

	public void setModele(String modele) {
		this.modele = modele;
	}

	public String getDesignationPiece() {
		return designationPiece;
	}

	public void setDesignationPiece(String designationPiece) {
		this.designationPiece = designationPiece;
	}

	public String getReferencePiece() {
		return referencePiece;
	}

	public void setReferencePiece(String referencePiece) {
		this.referencePiece = referencePiece;
	}

	public String getNaturePiece() {
		return naturePiece;
	}

	public void setNaturePiece(String naturePiece) {
		this.naturePiece = naturePiece;
	}

	public Double getTotalHt() {
		return totalHt;
	}

	public void setTotalHt(Double totalHt) {
		this.totalHt = totalHt;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	
	
	
	
	
	
	

}
