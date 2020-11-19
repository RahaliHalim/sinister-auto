package com.gaconnecte.auxilium.service.dto;

import java.time.LocalDate;


public class ViewExtractPieceDTO {
	
	 private Long id;

    private LocalDate dateAccident;
	
    private LocalDate operationDate;
	
    private String pointChoc;
	
    private String reparateur;
	
    private String zone;
	
    private String nChassis;
	
    private String immatriculation;
	
    private LocalDate datePremCirculation;
	
    private String marque;
	
    private String modele;
	
    private String designationPiece;
	
    private String referencePiece;
	
    private String naturePiece;
	
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
