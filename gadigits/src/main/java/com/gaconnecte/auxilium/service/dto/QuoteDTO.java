package com.gaconnecte.auxilium.service.dto;

import java.io.Serializable;

public class QuoteDTO implements Serializable {

    private String observationExpert;
    private Float nombreMOEstime;
    private String designation;
    private String typeIntervention;
    private Double prixUnit;
    private Float nombreHeures;
    //private Float totalHt;
    //private Float discount;
    //private Float amountDiscount;
    //private Float amountAfterDiscount;
    private Float tva;
    //private Float amountVat;
    private Float totalTtc;
    private Float vetuste;
    //private String TTCVetuste;
    private String observationRepairer;
    private String designationReference;
    public String naturePiece;
    private Float quantite;

    public String getObservationExpert() {
        return observationExpert;
    }

    public void setObservationExpert(String observationExpert) {
        this.observationExpert = observationExpert;
    }

    public Float getNombreMOEstime() {
        return nombreMOEstime;
    }

    public void setNombreMOEstime(Float nombreMOEstime) {
        this.nombreMOEstime = nombreMOEstime;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getTypeIntervention() {
        return typeIntervention;
    }

    public void setTypeIntervention(String typeIntervention) {
        this.typeIntervention = typeIntervention;
    }

    public Double getPrixUnit() {
        return prixUnit;
    }

    public void setPrixUnit(Double prixUnit) {
        this.prixUnit = prixUnit;
    }

    public Float getNombreHeures() {
        return nombreHeures;
    }

    public void setNombreHeures(Float nombreHeures) {
        this.nombreHeures = nombreHeures;
    }

    /*public Float getTotalHt() {
        return totalHt;
    }

    public void setTotalHt(Float totalHt) {
        this.totalHt = totalHt;
    }*/

    /*public Float getDiscount() {
        return discount;
    }

    public void setDiscount(Float discount) {
        this.discount = discount;
    }*/

    /*public Float getAmountDiscount() {
        return amountDiscount;
    }

    public void setAmountDiscount(Float amountDiscount) {
        this.amountDiscount = amountDiscount;
    }*/

    /*public Float getAmountAfterDiscount() {
        return amountAfterDiscount;
    }

    public void setAmountAfterDiscount(Float amountAfterDiscount) {
        this.amountAfterDiscount = amountAfterDiscount;
    }*/

    public Float getTva() {
        return tva;
    }

    public void setTva(Float tva) {
        this.tva = tva;
    }

    /*public Float getAmountVat() {
        return amountVat;
    }

    public void setAmountVat(Float amountVat) {
        this.amountVat = amountVat;
    }*/

    public Float getTotalTtc() {
        return totalTtc;
    }

    public void setTotalTtc(Float totalTtc) {
        this.totalTtc = totalTtc;
    }

    public Float getVetuste() {
        return vetuste;
    }

    public void setVetuste(Float vetuste) {
        this.vetuste = vetuste;
    }

    public String getObservationRepairer() {
        return observationRepairer;
    }

    public void setObservationRepairer(String observationRepairer) {
        this.observationRepairer = observationRepairer;
    }

    public String getDesignationReference() {
        return designationReference;
    }

    public void setDesignationReference(String designationReference) {
        this.designationReference = designationReference;
    }

    public String getNaturePiece() {
        return naturePiece;
    }

    public void setNaturePiece(String naturePiece) {
        this.naturePiece = naturePiece;
    }

    public Float getQuantite() {
        return quantite;
    }

    public void setQuantite(Float quantite) {
        this.quantite = quantite;
    }

}